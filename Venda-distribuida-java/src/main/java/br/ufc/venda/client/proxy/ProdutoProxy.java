package br.ufc.venda.client.proxy;

import java.util.List;

import br.ufc.venda.client.dao.ProdutoDAO;
import br.ufc.venda.model.Produto;

public class ProdutoProxy {
	
	ProdutoDAO pdao;
	
	public ProdutoProxy(){
		pdao = new ProdutoDAO();
	}
	
	public List<Produto> listarProdutos(){
		return pdao.selectAll();
	}
	
	public List<Produto> encontrarProduto(int id){
		return pdao.select(id);
	}
	
}
