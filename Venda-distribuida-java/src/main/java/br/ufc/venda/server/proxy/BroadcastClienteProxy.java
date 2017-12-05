package br.ufc.venda.server.proxy;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.ufc.venda.model.Cliente;
import br.ufc.venda.model.Database;
import br.ufc.venda.model.IdentificationRequest;
import br.ufc.venda.model.Mensagem;
import br.ufc.venda.model.Resposta;
import br.ufc.venda.server.client.UDPClient;
import br.ufc.venda.server.dao.DatabaseDAO;

public class BroadcastClienteProxy {
	
	private int REQUEST = 0;
	
	private DatabaseDAO dbdao;
	private UDPClient udpc;
	private Gson gson;
	
	public BroadcastClienteProxy(){
		dbdao = new DatabaseDAO();
		gson = new Gson();
	}
	
	public boolean refresh(Cliente cliente){
		Resposta resposta = null;
		try{
			for(Database db : dbdao.selectAll()){
				System.out.println(db);
				udpc = new UDPClient(db.getIp(), db.getPorta());
				resposta = (Resposta) desempacota(new String(doOperation("BroadcastCliente", "refresh", empacota(cliente))), Resposta.class);
				if(resposta.getCodigo() == 500)
					throw new Exception("[refresh Cliente]: Não foi possível atualizar todas as bases");
			}
			System.out.println("Bases atualizadas com o novo cliente");
			return true;
		}catch (Exception e) {
			System.out.println("[ERROR]:"+e.getMessage());
		}
		return false;
	}
	
	public boolean rollback(Cliente cliente){
		Resposta resposta = null;
		try{
			for(Database db : dbdao.selectAll()){
				udpc = new UDPClient(db.getIp(), db.getPorta());
				resposta = (Resposta) desempacota(new String(doOperation("BroadcastCliente", "rollback", empacota(cliente))), Resposta.class);
				if(resposta.getCodigo() == 500)
					throw new Exception("[rollback Cliente]: Não foi possível atualizar todas as bases");
			}
			return true;
		}catch (Exception e) {
			System.out.println("[ERROR]:"+e.getMessage());
		}
		return false;
	}
	
	private byte[] doOperation(String remoteObjectRef, String methodId, String arguments){
		Mensagem mensagem = new Mensagem();
		mensagem.setArguments(arguments);
		mensagem.setMethod(methodId);
		mensagem.setObjectReference(remoteObjectRef);
		mensagem.setRequestId(IdentificationRequest.getRequestId());
		mensagem.setMessageType(REQUEST);
		
		byte[] data = empacota(mensagem).getBytes();
		udpc.sendRequest(data);
		
		Mensagem resposta = (Mensagem) desempacota(new String(udpc.getReplay()), Mensagem.class);
		IdentificationRequest.increment();
		return resposta.getArguments().getBytes();
	}
	
	public void finaliza(){
		udpc.finaliza();
	}
	
	//Object to JSON
	private String empacota(Object object){
		return gson.toJson(object);
	}
	
	//JSON to Object
	private Object desempacota(String json, Class classOfT){
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return gson.fromJson(reader, classOfT);
	}
	
}
