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
			rs.close();
		}catch (Exception e) {
			System.out.println("Não foi possível listar as databases: "+e.getMessage());
		}
		return databases;
	}
	
	public Database select(Database database){
		Database db = new Database();
		try{
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("SELECT * FROM database WHERE ip = ? AND porta = ?");
			stmt.setString(1, database.getIp());
			stmt.setInt(2, database.getPorta());
		
			rs = stmt.executeQuery();
			if(rs.next()){
				db.setIp(rs.getString("ip"));
				db.setPorta(rs.getInt("porta"));
				return db;
			}else{
				return null;
			}
			
		}catch (Exception e) {
			System.out.println("Não foi possível obter a database: "+e.getMessage());
		}
		return null;
	}
	
	public boolean salvar(Database database){
		try{
			connection = ConnectionFactory.getConnection();
			stmt = connection.prepareStatement("INSERT INTO database (ip, porta) VALUES (?,?)");
			stmt.setString(1, database.getIp());
			stmt.setInt(2, database.getPorta());
		
			stmt.execute();
			
			return true;
		}catch (Exception e) {
			System.out.println("Não foi possível salvar a database: "+e.getMessage());
		}
		return false;
	}
	
}
