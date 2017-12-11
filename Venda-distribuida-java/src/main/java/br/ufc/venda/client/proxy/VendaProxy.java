package br.ufc.venda.client.proxy;

import java.io.StringReader;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import br.ufc.venda.client.client.UDPClient;
import br.ufc.venda.model.Cliente;
import br.ufc.venda.model.IdentificationRequest;
import br.ufc.venda.model.Mensagem;
import br.ufc.venda.model.ModoPagamento;
import br.ufc.venda.model.Pagamento;
import br.ufc.venda.model.Produto;
import br.ufc.venda.model.Resposta;
import br.ufc.venda.model.Venda;

public class VendaProxy {

	private int REQUEST = 0;
	
	private UDPClient udpc;
	private Gson gson;
	
	public VendaProxy(){
		udpc = new UDPClient("127.0.0.1",5000);
		gson = new Gson();
	}
	
	public boolean realizarVenda(Cliente cliente, Produto produto, int quantidade, Date dataVenda, int modoPagamento, Long cartao){
		Venda venda = new Venda();
		venda.setCliente(cliente);
		venda.setProduto(produto);
		venda.setQuantidade(quantidade);
		venda.setTotal(quantidade*produto.getValor());
		venda.setDataVenda(dataVenda);
		
		Pagamento pagamento = new Pagamento();
		pagamento.setNumCartao(cartao);
		ModoPagamento mp = null;
		for (ModoPagamento m: ModoPagamento.values()) if(m.pagamento() == modoPagamento) mp = m; 
		pagamento.setModoPagamento(mp);
		
		venda.setPagamento(pagamento);
		
		Resposta resposta = (Resposta) desempacota(new String(doOperation("Venda", "realizarVenda", empacota(venda))), Resposta.class);
		System.out.println(resposta);
		if(resposta.getCodigo() == 200)
			return true;
		else
			return false;
	}
	
	private byte[] doOperation(String remoteObjectRef, String methodId, String arguments){
		Mensagem mensagem = new Mensagem();
		mensagem.setMessageType(REQUEST);
		mensagem.setObjectReference(remoteObjectRef);
		mensagem.setMethod(methodId);
		mensagem.setRequestId(IdentificationRequest.getRequestId());
		mensagem.setArguments(arguments);
		
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
