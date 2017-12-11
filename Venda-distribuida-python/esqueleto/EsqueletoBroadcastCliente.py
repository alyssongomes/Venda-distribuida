#-*-coding:utf-8 -*-

import json

from dao.ClienteDAO import ClienteDAO

class EsqueletoBroadcastCliente():

    __SUCCESS = 200
    __ERROR = 500

    def __init__(self):
        self.cdao = ClienteDAO()

    def refresh(self, args):
        cliente = json.loads(args)
        resposta = None
        if self.cdao.salvar(cliente):
            resposta = {"codigo":self.__SUCCESS, "reposta":"true"}
        else:
            resposta = {"codigo": self.__ERROR, "reposta": "false"}
        return json.dump(resposta)

    def rollback(self,args):
        cliente = json.loads(args)
        resposta = None
        if self.cdao.excluir(cliente):
            resposta = {"codigo": self.__SUCCESS, "reposta": "true"}
        else:
            resposta = {"codigo": self.__ERROR, "reposta": "false"}
        return json.dump(resposta)