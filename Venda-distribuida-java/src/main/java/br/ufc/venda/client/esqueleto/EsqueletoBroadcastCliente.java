package br.ufc.venda.client.esqueleto;

import com.google.gson.Gson;

import br.ufc.venda.client.dao.ClienteDAO;
import br.ufc.venda.model.Cliente;
import br.ufc.venda.model.Resposta;

public class EsqueletoBroadcastCliente {
	
	private ClienteDAO cdao;
	private Gson gson;
	
	public EsqueletoBroadcastCliente(){
		cdao = new ClienteDAO();
		gson = new Gson();
	}
	
	public String refresh(String args){
		Cliente cliente = gson.fromJson(args, Cliente.class); 
		Resposta resposta = new Resposta();
		if(cdao.salvar(cliente)){
			resposta.setCodigo(Resposta.SUCCESS);
			resposta.setResposta("true");
		}else{
			resposta.setCodigo(Resposta.ERROR);
			resposta.setResposta("false");
		}
		return gson.toJson(resposta);
	}
	
	public String rollback(String args){
		Cliente cliente = gson.fromJson(args, Cliente.class); 
		Resposta resposta = new Resposta();
		if(cdao.excluir(cliente)){
			resposta.setCodigo(Resposta.SUCCESS);
			resposta.setResposta("true");
		}else{
			resposta.setCodigo(Resposta.ERROR);
			resposta.setResposta("false");
		}
		return gson.toJson(resposta);
	}
	
}
