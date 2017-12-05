package br.ufc.venda.client.client;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufc.venda.client.proxy.ClienteProxy;
import br.ufc.venda.client.proxy.DatabaseProxy;
import br.ufc.venda.client.proxy.ProdutoProxy;
import br.ufc.venda.client.proxy.VendaProxy;
import br.ufc.venda.model.Cliente;
import br.ufc.venda.model.ModoPagamento;
import br.ufc.venda.model.Produto;

public class TerminalVenda {

	Cliente clienteSessao;
	ClienteProxy cproxy;
	VendaProxy vproxy;
	ProdutoProxy pproxy;
	DatabaseProxy dbproxy;
	
	public TerminalVenda(){
		cproxy = new ClienteProxy();
		vproxy = new VendaProxy();
		pproxy = new ProdutoProxy();
		dbproxy = new DatabaseProxy();
	}
	
	public boolean verificarBase(){
		return dbproxy.verificarBaseReplica();
	}
	
	private void showMain(){
		int option = 0;
		try{
			option = Integer.parseInt(JOptionPane.showInputDialog("1 - Registrar-se \n2 - Login\n3 - Sair"));
		}catch (Exception e) { 
			JOptionPane.showMessageDialog(null,"Opção inválida!"); 
		}
		if(option == 1)
			showRegister();
		else if(option == 2)
			showLogin();
		else if(option == 3)
			System.exit(0);
		else
			JOptionPane.showMessageDialog(null, "Opção inválida");
	}
	
	private void showRegister(){
		try{
			String nome = JOptionPane.showInputDialog("Diga seu nome:");
			Long cpf = Long.parseLong(JOptionPane.showInputDialog("Diga seu CPF (apenas números):"));
			Long rg = Long.parseLong(JOptionPane.showInputDialog("Diga seu RG (apenas números):"));
			String endereco = JOptionPane.showInputDialog("Diga seu Endereço:");
			Boolean resultado = cproxy.registrarCliente(nome, cpf, rg, endereco);
			System.out.println("TerminalVenda: "+resultado);
			if(resultado){
				JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "VENDA-DISTRIBUIDA", JOptionPane.INFORMATION_MESSAGE);
				clienteSessao = new Cliente();
				clienteSessao.setCpf(cpf);
				clienteSessao.setRg(rg);
				clienteSessao.setNome(nome);
				clienteSessao.setEndereco(endereco);
				showMenu();
			}else{
				JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o cliente!", "VENDA-DISTRIBUIDA", JOptionPane.ERROR_MESSAGE);
				showMain();
			}
		}catch (Exception e) { 
			JOptionPane.showMessageDialog(null, "Informe dados válidos: "+e.getMessage()); 
		}
		
	}

	private void showLogin(){
		String usuario = JOptionPane.showInputDialog("Nome:");
		Long senha = Long.parseLong(JOptionPane.showInputDialog("CPF (apenas números):"));
		
		clienteSessao = cproxy.autenticar(usuario, senha);
		if(clienteSessao != null){
			JOptionPane.showMessageDialog(null, "Bem-vindo! "+clienteSessao.getNome(), "VENDA-DISTRIBUÍDA",JOptionPane.INFORMATION_MESSAGE);
			while(true)
				showMenu();
		}else{
			showMain();
		}
	}
	
	private void showProdutos(){
		List<Produto> produtos = pproxy.listarProdutos();
		String lista = "";
		for (Produto produto : produtos) {
			lista += produto+"\n";
		}
		JOptionPane.showMessageDialog(null, lista);
	}
	
	private void realizarCompra(){
		String lista = "";
		Date dataVenda = new Date(Calendar.getInstance().getTimeInMillis());
		try{
			List<Produto> produtos = pproxy.listarProdutos();
			for (Produto produto : produtos) lista += produto+"\n";
			int produtoId = Integer.parseInt(JOptionPane.showInputDialog(null, lista+"Informe o Id do produto que deseja comprar"));
			int qtd = Integer.parseInt(JOptionPane.showInputDialog(null, "Informe a quantidade de produtos"));
			int modoPagamento = Integer.parseInt(JOptionPane.showInputDialog(null, "1-Débito\n2-Crédito\nInforme o modo de Pagamento"));
			Long numCartao = Long.parseLong(JOptionPane.showInputDialog(null, "Informe o número do cartão (apenas número)"));
			
			Produto prod = null;
			for (Produto produto : produtos) if(produto.getId() == produtoId) prod = produto;
			if(modoPagamento != ModoPagamento.CREDITO.pagamento() && modoPagamento != ModoPagamento.DEBITO.pagamento())
				return;
			
			Boolean resultado = vproxy.realizarVenda(clienteSessao, prod, qtd, dataVenda, modoPagamento, numCartao);
			if(resultado){
				JOptionPane.showMessageDialog(null, "Compra realizada com sucesso!", "VENDA-DISTRIBUIDA", JOptionPane.INFORMATION_MESSAGE);
				showMenu();
			}else{
				JOptionPane.showMessageDialog(null, "Não foi possível efetuar a compra!", "VENDA-DISTRIBUIDA", JOptionPane.ERROR_MESSAGE);
				showMenu();
			}
				
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Houve um erro ao realizar a compra: "+e.getMessage());
		}
	}
	
	private void showMenu(){
		int option = 0;
		try{
			option = Integer.parseInt(JOptionPane.showInputDialog("1 - Listar Produtos \n2 - Realizar Compra\n3 - Sair"));
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Opção inválida");
		}
		if(option == 1)
			showProdutos();
		else if(option == 2)
			realizarCompra();
		else if(option == 3)
			showMain();
		else
			JOptionPane.showMessageDialog(null, "Opção inválida");
	}
	
	public static void main(String[] args) {
		TerminalVenda tv = new TerminalVenda();
		if(tv.verificarBase())
			while(true)
				tv.showMain();
		else
			JOptionPane.showMessageDialog(null, "A base deste cliente não está no mestre!", "VENDA-DISTRIBUÍDA", JOptionPane.ERROR_MESSAGE);
	}

}
