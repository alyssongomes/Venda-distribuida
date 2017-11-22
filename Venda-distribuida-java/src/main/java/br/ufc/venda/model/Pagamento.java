package br.ufc.venda.model;

public enum Pagamento {
	
	DE("DEBITO"), CR("CREDITO");
	
	private Pagamento(String pagamento){
		this.pagamento = pagamento;
	}
	
	private String pagamento;
	
	public String pagamento(){
		return this.pagamento;
	}
}
