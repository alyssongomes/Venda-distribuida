package br.ufc.venda.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static String url = "jdbc:postgresql://localhost/sd_venda";
	private static String user = "postgres";
	private static String password = "postgres";
	
	public static Connection getConnection(){
		try{
			return DriverManager.getConnection(url, user, password);
		}catch (Exception e) {
			System.err.println("Não foi possível realizar a conexão: "+e.getMessage());
		}
		return null;
	}
	
}
