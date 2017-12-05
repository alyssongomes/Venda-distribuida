package br.ufc.venda.client.proxy;

import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.ufc.venda.client.client.UDPClient;
import br.ufc.venda.client.server.UDPServer;
import br.ufc.venda.model.Database;
import br.ufc.venda.model.IdentificationRequest;
import br.ufc.venda.model.Mensagem;
import br.ufc.venda.model.Resposta;

public class DatabaseProxy {
	
	private int REQUEST = 0;
	
	private UDPClient udpc;
	private Gson gson;
	
	public DatabaseProxy(){
		udpc = new UDPClient("127.0.0.1", 5000);
		gson = new Gson();
	}
	
	public boolean verificarBaseReplica(){
		Database database = new Database();
		database.setIp(UDPServer.IP);
		database.setPorta(UDPServer.PORTA);
		
		Resposta resposta = (Resposta) desempacota(new String(doOperation("Database", "verificarBaseReplica", empacota(database))), Resposta.class);
		if(resposta.getCodigo() == 200)
			return true;
		else
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
		if(!json.endsWith("\"}\"}"))
			json = json+"}";
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return gson.fromJson(reader, classOfT);
	}
	
	
	
}
