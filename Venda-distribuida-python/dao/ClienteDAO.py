#-*- coding:utf-8 -*-

from connection.ConnectionFactory import ConnectionFactory

class ClienteDAO():

    def autenticar(self,nome, cpf):
        cliente = None
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('SELECT * FROM cliente WHERE nome = '+nome+' AND cpf = '+cpf)
            cliente = tuple(con.fetchall())[0]
        except Exception as e:
            print('Não foi possível autenticar o cliente:'+str(e))
        return cliente

    def salvar(self,cliente):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('INSERT INTO cliente (nome, cpf, rg, endereco) VALUES (\''+cliente['nome']+'\','+cliente['cpf']+','+cliente['rg']+',\''+cliente['endereco']+'\')')
            return True
        except Exception as e:
            print('Não foi possível salvar o cliente:' + str(e))
        return False

    def excluir(self, cliente):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('DELETE FROM cliente WHERE id ='+cliente['id']+')')
            return True
        except Exception as e:
            print('Nao foi possivel excluir o cliente: '+str(e))
        return False