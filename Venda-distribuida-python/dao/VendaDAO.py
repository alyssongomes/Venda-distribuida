#-*- coding:utf-8 -*-

from connection.ConnectionFactory import ConnectionFactory

class VendaDAO():

    def salvar(self, venda):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('INSERT INTO venda (cliente_id, produto_id, quantidade, total, data_venda, numero_cartao, pagamento) VALUES ('+venda['cliente']['id']+','+venda['produto']['id']+','+venda['quantidade']+','+venda['total']+','+venda['dataVenda']+','+venda['numCartao']+''+venda['modoPagamento']+')')
            return True
        except Exception as e:
            print('Não foi possível salvar a venda:' + str(e))
        return False

    def excluir(self, venda):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('DELETE FROM venda WHERE cliente_id = '+venda['cliente']['id']+' AND produto_id = '+venda['produto']['id']+' AND quantidade = '+venda['quantidade']+' AND total = '+venda['total']+' AND data_venda = '+venda['dataVenda'])
            return True
        except Exception as e:
            print('Nao foi possivel excluir a venda: '+str(e))
        return False