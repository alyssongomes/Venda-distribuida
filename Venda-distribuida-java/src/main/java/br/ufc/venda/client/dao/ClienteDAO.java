package br.ufc.venda.client.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.ufc.venda.client.connection.ConnectionFactoryReplica;
import br.ufc.venda.model.Cliente;

public class ClienteDAO {
	
	private Connection con;
	private PreparedStatement stm;
	private ResultSet rs;
	
	public Cliente autenticar(String nome, Long cpf){
		Cliente c = new Cliente();
		
		try{
			con = ConnectionFactoryReplica.getConnection();
			stm = con.prepareStatement("SELECT * FROM cliente WHERE cpf = ? AND nome = ?");
			stm.setLong(1, cpf);
			stm.setString(2, nome);
		
			rs = stm.executeQuery();
			rs.next();
			
			c.setCpf(rs.getLong("cpf"));
			c.setNome(rs.getString("nome"));
			c.setRg(rs.getLong("rg"));
			c.setEndereco(rs.getString("endereco"));
			
			stm.close();
			return c;
		}catch (Exception e) {
			System.err.println("Não foi possível autenticar o cliente: "+e.getMessage());
		}
		return null;
	}
	
	public boolean salvar(Cliente cliente){
		try{
			con = ConnectionFactoryReplica.getConnection();
			stm = con.prepareStatement("INSERT INTO cliente (nome, cpf, rg, endereco) VALUES (?,?,?,?)");
			stm.setString(1, cliente.getNome());
			stm.setLong(2, cliente.getCpf());
			stm.setLong(3, cliente.getRg());
			stm.setString(4, cliente.getEndereco());
			
			stm.execute();
			stm.close();
			return true;
		}catch (Exception e) {
			System.err.println("Não foi possíve salvar o cliente: "+e.getMessage());
		}
		return false;
	}
	
}
