package br.ufc.venda.client.proxy;


import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.ufc.venda.client.client.UDPClient;
import br.ufc.venda.client.dao.ClienteDAO;
import br.ufc.venda.model.Cliente;
import br.ufc.venda.model.IdentificationRequest;
import br.ufc.venda.model.Mensagem;
import br.ufc.venda.model.Resposta;

public class ClienteProxy {
	
	private int REQUEST = 0;
	
	private ClienteDAO cdao;
	private UDPClient udpc;
	private Gson gson;
	
	public ClienteProxy(){
		cdao = new ClienteDAO();
		udpc = new UDPClient("127.0.0.1",5000);
		gson = new Gson();
	}
	
	public Cliente autenticar(String nome, Long cpf){
		return cdao.autenticar(nome, cpf);
	}
	
	public boolean registrarCliente(String nome, Long cpf, Long rg, String endereco){
		Cliente c = new Cliente();
		c.setNome(nome);
		c.setCpf(cpf);
		c.setRg(rg);
		c.setEndereco(endereco);
		
		Resposta resposta = (Resposta) desempacota(new String(doOperation("Cliente", "registrarCliente", empacota(c))), Resposta.class);
		System.out.println(resposta);
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
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return gson.fromJson(reader, classOfT);
	}
}
