package br.ufc.venda.model;

public enum ModoPagamento {
	
	DEBITO(1), CREDITO(2);
	
	private ModoPagamento(int pagamento){
		this.pagamento = pagamento;
	}
	
	private int pagamento;
	
	public int pagamento(){
		return this.pagamento;
	}
}
