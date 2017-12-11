#-*- coding:utf-8 -*-

from connection.ConnectionFactory import ConnectionFactory

class VendaDAO():

    def salvar(self, venda):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('INSERT INTO venda (cliente_id, produto_id, quantidade, total, data_venda, numero_cartao, pagamento) VALUES ('+venda['cliente']['id']+','+venda['produto']['id']+','+venda['quantidade']+','+venda['total']+','+venda['data_venda']+','+venda['numero_cartao']+''+venda['pagamento']+')')
            return True
        except Exception as e:
            print('Não foi possível salvar a venda:' + str(e))
        return False