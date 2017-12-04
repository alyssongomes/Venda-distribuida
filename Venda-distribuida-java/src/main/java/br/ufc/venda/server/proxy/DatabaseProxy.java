package br.ufc.venda.server.proxy;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.ufc.venda.model.IdentificationRequest;
import br.ufc.venda.model.Mensagem;
import br.ufc.venda.server.client.UDPClient;
import br.ufc.venda.server.dao.DatabaseDAO;

public class DatabaseProxy {

	private int REQUEST = 0;
	
	private DatabaseDAO dbdao;
	private UDPClient udpc;
	private Gson gson;
	
	public DatabaseProxy(){
		dbdao = new DatabaseDAO();
		udpc = new UDPClient("127.0.0.1", 6000);
		gson = new Gson();
	}
	
	//TODO: implementar o proxy da atualização dos bancos 
	public boolean atualizarDatabases(){
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
