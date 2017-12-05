package br.ufc.venda.server.esqueleto;

import com.google.gson.Gson;

import br.ufc.venda.model.Database;
import br.ufc.venda.model.Resposta;
import br.ufc.venda.server.dao.DatabaseDAO;

public class EsqueletoDatabase {

	private DatabaseDAO dbdao;
	private Gson gson;
	
	public EsqueletoDatabase(){
		dbdao = new DatabaseDAO();
		gson = new Gson();
	}
	
	public String verificarBaseReplica(String args){
		Database database = gson.fromJson(args, Database.class); 
		Resposta resposta = new Resposta();
		
		if(dbdao.select(database) == null){
			if(dbdao.salvar(database)){
				resposta.setCodigo(Resposta.SUCCESS);
				resposta.setResposta("true");
			}else{
				resposta.setCodigo(Resposta.ERROR);
				resposta.setResposta("false");
			}
		}else{
			resposta.setCodigo(Resposta.SUCCESS);
			resposta.setResposta("true");
		}
		return gson.toJson(resposta);
	}
	
}
