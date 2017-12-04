package br.ufc.venda.server.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.ufc.venda.model.Venda;
import br.ufc.venda.server.connection.ConnectionFactory;

public class VendaDAO {
	
	private Connection connection;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public boolean salvar(Venda venda){
		try{
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("INSERT INTO venda (cliente_id, produto_id, quantidade, total, data_venda, numero_cartao, pagamento) VALUES (?,?,?,?,?,?,?)");
			stmt.setLong(1, venda.getCliente().getCpf());
			stmt.setLong(2, venda.getProduto().getId());
			stmt.setInt(3, venda.getQuantidade());
			stmt.setDouble(4, venda.getTotal());
			
			Date sqlDate = new Date(venda.getDataVenda().getTime()); 
			stmt.setDate(5, sqlDate);
			stmt.setLong(6, venda.getPagamento().getNumCartao());
			stmt.setInt(7, venda.getPagamento().getModoPagamento().pagamento());
			
			stmt.execute();
			stmt.close();
			return true;
		}catch (Exception e) {
			System.out.println("Não foi possível salvar a venda: "+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
}
