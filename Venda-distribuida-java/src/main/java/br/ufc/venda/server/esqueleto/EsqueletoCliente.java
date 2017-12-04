package br.ufc.venda.server.esqueleto;

import com.google.gson.Gson;

import br.ufc.venda.model.Cliente;
import br.ufc.venda.model.Resposta;
import br.ufc.venda.server.dao.ClienteDAO;

public class EsqueletoCliente {

	private ClienteDAO cdao;
	private Gson gson;
	
	public EsqueletoCliente(){
		cdao = new ClienteDAO();
		gson = new Gson();
	}
	
	public String registrarCliente(String args){
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
	
}
