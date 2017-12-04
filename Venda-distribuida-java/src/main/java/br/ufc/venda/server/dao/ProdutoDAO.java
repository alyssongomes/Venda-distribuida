package br.ufc.venda.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ufc.venda.model.Produto;
import br.ufc.venda.server.connection.ConnectionFactory;

public class ProdutoDAO {
	
	private Connection con;
	private PreparedStatement stm;
	private ResultSet rs;
	
	public List<Produto> selectAll(){
		List<Produto> produtos = new ArrayList<Produto>();
		try{
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement("SELECT * FROM produto");
		
			rs = stm.executeQuery();
			while(rs.next()){
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setDescricao(rs.getString("descricao"));
				p.setValor(rs.getDouble("valor"));
				produtos.add(p);
			}
			stm.close();
			return produtos;
		}catch (Exception e) {
			System.err.println("Não foi possível carregar os produtos: "+e.getMessage());
		}
		return null;
	}
	
	public List<Produto> select(int id){
		List<Produto> produtos = new ArrayList<Produto>();
		try{
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement("SELECT * FROM produto WHERE id = ? ");
			stm.setInt(0, id);
			
			rs = stm.executeQuery();
			while(rs.next()){
				Produto p = new Produto();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setDescricao(rs.getString("descricao"));
				p.setValor(rs.getDouble("valor"));
				produtos.add(p);
			}
			stm.close();
			return produtos;
		}catch (Exception e) {
			System.err.println("Não foi possível carregar os produtos com o id ["+id+"]: "+e.getMessage());
		}
		return null;
	}
	
}
