#-*-coding:utf-8 -*-

import json

from dao.VendaDAO import VendaDAO

class EsqueletoBroadcastVenda():

    __SUCCESS = 200
    __ERROR = 500

    def __init__(self):
        self.vdao = VendaDAO()

    def refresh(self, args):
        venda = json.loads(args)
        resposta = None
        if self.vdao.salvar(venda):
            resposta = {"codigo":self.__SUCCESS, "reposta":"true"}
        else:
            resposta = {"codigo": self.__ERROR, "reposta": "false"}
        return json.dump(resposta)

    def rollback(self,args):
        venda = json.loads(args)
        resposta = None
        if self.vdao.excluir(venda):
            resposta = {"codigo": self.__SUCCESS, "reposta": "true"}
        else:
            resposta = {"codigo": self.__ERROR, "reposta": "false"}
        return json.dump(resposta)
