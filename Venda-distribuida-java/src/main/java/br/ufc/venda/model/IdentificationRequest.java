package br.ufc.venda.model;

public class IdentificationRequest {
	
	private static int requestId = 0;
	
	public static void increment(){
		requestId++;
	}
	
	public static int getRequestId(){
		return requestId;
	}
	
}
