package br.ufc.venda.model;

public class Pagamento {

	private ModoPagamento pagamento;
	private Long numCartao;
	
	public ModoPagamento getModoPagamento() {
		return pagamento;
	}
	public void setModoPagamento(ModoPagamento pagamento) {
		this.pagamento = pagamento;
	}
	public Long getNumCartao() {
		return numCartao;
	}
	public void setNumCartao(Long numCartao) {
		this.numCartao = numCartao;
	}
	
	
	
}
