package br.ufc.venda.client.esqueleto;

import com.google.gson.Gson;

import br.ufc.venda.client.dao.VendaDAO;
import br.ufc.venda.model.Resposta;
import br.ufc.venda.model.Venda;

public class EsqueletoBroadcastVenda {
	
	private VendaDAO vdao;
	private Gson gson;
	
	public EsqueletoBroadcastVenda(){
		vdao = new VendaDAO();
		gson = new Gson();
	}
	
	public String refresh(String args){
		Venda venda = gson.fromJson(args, Venda.class);
		Resposta resposta = new Resposta();
		if(vdao.salvar(venda)){
			resposta.setCodigo(Resposta.SUCCESS);
			resposta.setResposta("true");
		}else{
			resposta.setCodigo(Resposta.ERROR);
			resposta.setResposta("false");
		}
		return gson.toJson(resposta);
	}
	
	public String rollback(String args){
		Venda venda = gson.fromJson(args, Venda.class);
		Resposta resposta = new Resposta();
		if(vdao.excluir(venda)){
			resposta.setCodigo(Resposta.SUCCESS);
			resposta.setResposta("true");
		}else{
			resposta.setCodigo(Resposta.ERROR);
			resposta.setResposta("false");
		}
		return gson.toJson(resposta);
	}
	
	
}
