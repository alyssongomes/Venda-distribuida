package br.ufc.venda.server.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class UDPClient {
	private DatagramSocket aSocket;
	private DatagramPacket request;
	private InetAddress aHost;
	private String ip;
	private int porta;
	
	
	public UDPClient(String ip, int porta){
		try{
			this.ip = ip;
			this.porta = porta;
			aSocket = new DatagramSocket();
			aHost = InetAddress.getByName(this.ip);
		}catch (Exception e) { 
			System.out.println("Não foi possível instanciar o objeto: "+e.getMessage());
		}
	}
	
	public void sendRequest(byte[] requisicao){
		try{
			request = new DatagramPacket(requisicao,  requisicao.length, aHost, this.porta);
			aSocket.send(request);
			aSocket.setSoTimeout(200);
		} catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e){
			System.out.println("IO: " + e.getMessage());
		}
	}
	
	public byte[] getReplay(){
		byte[] buffer = new byte[request.getData().length];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		while(true){
			try{
				aSocket.receive(reply);
				return reply.getData();
			}catch (SocketTimeoutException e) {
				System.out.println("Reenviando...");
				try{
					aSocket.send(this.request);
					aSocket.setSoTimeout(200);
				} 
				catch (SocketException se){ 
					System.out.println("Socket: " + se.getMessage());
				} 
				catch (IOException ioe){ 
					System.out.println("IO: " + ioe.getMessage());
				} 
			}catch (IOException e){
				System.out.println("IO: " + e.getMessage());
			}
		}
	}
	
	public void finaliza(){
		try{
			aSocket.close();
		}catch (Exception e) {
			System.out.println("Não foi possível fechar o socket: "+e.getMessage());
		}
	}
	
}
