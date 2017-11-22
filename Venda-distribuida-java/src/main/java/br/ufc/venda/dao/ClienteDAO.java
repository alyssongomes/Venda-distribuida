package br.ufc.venda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.ufc.venda.connection.ConnectionFactory;
import br.ufc.venda.model.Cliente;

public class ClienteDAO {
	
	private Connection con;
	private PreparedStatement stm;
	private ResultSet rs;
	
	public Cliente autenticar(Cliente cliente){
		Cliente c = new Cliente();
		try{
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement("SELECT * FROM clientes WHERE cpf = ? and nome = ?");
			stm.setString(0, cliente.getCpf());
			stm.setString(1, cliente.getNome());
		
			rs = stm.executeQuery();
			rs.beforeFirst();
			while(rs.next()){
				c.setCpf(rs.getString("cpf"));
				c.setNome(rs.getString("nome"));
				c.setRg(rs.getString("rg"));
				c.setEndereco(rs.getString("endereco"));
			}
			stm.close();
			return c;
		}catch (Exception e) {
			System.err.println("Não foi possível autenticar o cliente: "+e.getMessage());
		}
		return null;
	}
	
	public boolean salvar(Cliente cliente){
		try{
			con = ConnectionFactory.getConnection();
			stm = con.prepareStatement("INSERT INTO clientes (nome, cpf, rg, endereco) VALUES (?,?,?,?)");
			stm.setString(0, cliente.getNome());
			stm.setString(1, cliente.getCpf());
			stm.setString(2, cliente.getRg());
			stm.setString(3, cliente.getEndereco());
			
			stm.execute();
			stm.close();
			return true;
		}catch (Exception e) {
			System.err.println("Não foi possíve salvar o cliente: "+e.getMessage());
		}
		return false;
	}
	
}
