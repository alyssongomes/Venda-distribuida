package br.ufc.venda.server.esqueleto;

import com.google.gson.Gson;

import br.ufc.venda.model.Resposta;
import br.ufc.venda.model.Venda;
import br.ufc.venda.server.dao.VendaDAO;

public class EsqueletoVenda {
	
	private VendaDAO vdao;
	private Gson gson;
	
	public EsqueletoVenda(){
		vdao = new VendaDAO();
		gson = new Gson();
	}
	
	public String realizarVenda(String args){
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
}
