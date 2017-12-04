package br.ufc.venda.client.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryReplica {

	private static String url = "jdbc:postgresql://localhost/sd_venda_replica";
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
