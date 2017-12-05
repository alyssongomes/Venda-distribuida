package br.ufc.venda.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.ufc.venda.model.Database;
import br.ufc.venda.server.connection.ConnectionFactory;

public class DatabaseDAO {
	
	private Connection connection;
	private PreparedStatement stmt;
	private ResultSet rs;
	
	public List<Database> selectAll(){
		List<Database> databases = new ArrayList<Database>();
		try{
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("SELECT * FROM database");
			
			rs = stmt.executeQuery();
			while(rs.next()){
				Database db = new Database();
				db.setIp(rs.getString("ip"));
				db.setPorta(rs.getInt("porta"));
				databases.add(db);
			}
			stmt.close();
		}catch (Exception e) {
			System.out.println("Não foi possível listar os databases: "+e.getMessage());
		}
		return databases;
	}
}
