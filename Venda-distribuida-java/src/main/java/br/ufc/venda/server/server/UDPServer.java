package br.ufc.venda.server.server;

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.ufc.venda.model.Mensagem;

public class UDPServer {
	
	private DatagramSocket aSocket;
	private int PORTA = 5000;
	public static int REPLY = 1;
	
	public UDPServer(){
		try{
			aSocket = new DatagramSocket(this.PORTA);
		}catch (Exception e) {
			System.out.println("Não foi possível criar a instância: "+e.getMessage());
		}
	}
	
	public DatagramPacket getRequest(){
		try{
			byte[] buffer = new byte[1000];
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(request);
			return request;
		}catch (Exception e) {
			System.out.println("Não foi possível receber a requisição: "+e.getMessage());
		}
		return null;
	}
	
	public void sendReply(byte[] resposta, InetAddress address, int port){
		try{
			DatagramPacket reply = new DatagramPacket(resposta,  resposta.length, address, port);
			aSocket.send(reply);
		} catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){
			System.out.println("IO: " + e.getMessage());
		}
	}
	
	public void finaliza(){
		try{
			if(aSocket != null) {
				aSocket.close();
			}
		}catch (Exception e) {
			System.out.println("Não foi possível fechar o socket: "+e.getMessage());
		}
	}
	
	public boolean verificarPacotes(List<DatagramPacket> packets, DatagramPacket packet, Gson gson){
		for(DatagramPacket dp: packets){
			if(dp.getPort() == packet.getPort() && dp.getAddress().equals(packet.getAddress())){
				JsonReader reader = new JsonReader(new StringReader(new String(packet.getData())));
				reader.setLenient(true);
				Mensagem packetOne = gson.fromJson(reader, Mensagem.class);
				
				reader = new JsonReader(new StringReader(new String(dp.getData())));
				reader.setLenient(true);
				Mensagem packetTwo = gson.fromJson(reader, Mensagem.class);
				if(packetOne.getRequestId() == packetTwo.getRequestId()){
					packet = dp;
					return true;					
				}else if(packetOne.getRequestId() > packetTwo.getRequestId()){ //Atualiza o histórico, verificando se as requisições anteriores foram atendidas
					packets.remove(dp);
					System.out.println("Removeu requisições antigas");
					return false;
				}
			}
		}
		return false;
	}
	
	public void sendAndStore(DatagramPacket request, Gson gson, Despachante despachante, List<DatagramPacket> historico){
		//Descompactando
		String requestJson = new String(request.getData());
		JsonReader reader = new JsonReader(new StringReader(requestJson));
		reader.setLenient(true);
		Mensagem mensagem = gson.fromJson(reader, Mensagem.class);
		
		//Pegando o resultado
		String reply  = despachante.invoke(mensagem);
		mensagem.setArguments(reply);
		mensagem.setMessageType(REPLY);

		//Empacotando o resultado e envia
		byte[] response = gson.toJson(mensagem).getBytes(); 
		this.sendReply(response, request.getAddress(), request.getPort());
		
		//Guarda o pacote no histórico
		DatagramPacket packet = new DatagramPacket(response, response.length, request.getAddress(), request.getPort());
		historico.add(packet);
	}
	
	public static void main(String[] args) {
		
		List<DatagramPacket> historicoRespostas = new ArrayList<DatagramPacket>();
		UDPServer udps = new UDPServer();
		Despachante despachante = new Despachante();
		Random random = new Random(100);
		Gson gson = new Gson();
		
		System.out.println("UDPServer rodando ...");
		try{
			while(true){
				//Pegando requisição
				DatagramPacket request = udps.getRequest();
				if(random.nextInt() % 3 == 0){
					
					if(udps.verificarPacotes(historicoRespostas, request, gson)){
						//Retransmite a reposta caso a requisição esteja duplicada
						udps.sendReply(request.getData(), request.getAddress(), request.getPort());
						System.out.println("Resposta retransmitida");
					}else{
						//Processa requisição, envia a resposta e armazena no histórico 
						udps.sendAndStore(request, gson, despachante, historicoRespostas);
						System.out.println("Enviou a resposta e adicionou no histórico");
					}
				}
			}
		}catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			udps.finaliza();
		}
	}

	
	
}
