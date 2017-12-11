#-*-coding:utf-8 -*-

from esqueleto.EsqueletoBroadcastCliente import EsqueletoBroadcastCliente
from esqueleto.EsqueletoBroadcastVenda import EsqueletoBroadcastVenda

class Despachante():

    def invoke(self, mensagem):
        if mensagem['objectReference'] == 'EsqueletoBroadcastCliente':
            if mensagem['method'] == 'refresh':
                return EsqueletoBroadcastCliente().refresh(mensagem['arguments'])
            elif mensagem['method'] == 'rollback':
                return EsqueletoBroadcastCliente().rollback(mensagem['arguments'])
        elif mensagem['objectReference'] == 'EsqueletoBroadcastVenda':
            if mensagem['method'] == 'refresh':
                return EsqueletoBroadcastVenda().refresh(mensagem['arguments'])
            elif mensagem['method'] == 'rollback':
                return EsqueletoBroadcastVenda().rollback(mensagem['arguments'])

