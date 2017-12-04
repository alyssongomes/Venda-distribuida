package br.ufc.venda.model;

public class Resposta {
	
	public static final int SUCCESS = 200;
	public static final int ERROR = 500;
	
	private int codigo;
	private String resposta;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	
}
