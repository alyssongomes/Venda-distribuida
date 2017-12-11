#-*- coding:utf-8 -*-

import psycopg2

class ConnectionFactory():

    @staticmethod
    def getConnection():
        try:
            return psycopg2.connect(host='localhost', user='postgres',password='postgres',dbname='sd_venda_replica')
        except Exception as e:
            print('Não foi possível realizar a conexão:'+str(e))
