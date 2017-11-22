package br.ufc.venda.model;

public class Mensagem {
	
	private int messageType;
	private int requestId; 
	private String objectReference;
	private String method;
	private String arguments;
	
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getObjectReference() {
		return objectReference;
	}
	public void setObjectReference(String objectReference) {
		this.objectReference = objectReference;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

}
