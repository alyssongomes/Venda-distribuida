#-*- coding:utf-8 -*-

from connection.ConnectionFactory import ConnectionFactory

class ProdutoDAO():

    def selectAll(self):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('SELECT * FROM produto')
            return tuple(con.fetchall())
        except Exception as e:
            print('Não foi possível carregar os produtos: '+str(e))
        return None

    def select(self,id):
        try:
            con = ConnectionFactory.getConnection().cursor()
            con.execute('SELECT * FROM produto WHERE id = '+id)
            return tuple(con.fetchall())
        except Exception as e:
            print('Não foi possível carregar os produtos com o id['+id+']: '+str(e))
        return None