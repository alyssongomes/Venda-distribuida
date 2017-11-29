package br.ufc.venda.model;

public class Database {
	
	private String operacao;
	private String dados;
	private String tabela;
	private int idBase;
	private String ip;
	private int porta;
	
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public String getDados() {
		return dados;
	}
	public void setDados(String dados) {
		this.dados = dados;
	}
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public int getIdBase() {
		return idBase;
	}
	public void setIdBase(int idBase) {
		this.idBase = idBase;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPorta() {
		return porta;
	}
	public void setPorta(int porta) {
		this.porta = porta;
	}
}
