#-*-coding:utf-8 -*-

import json

from dao.ClienteDAO import ClienteDAO
from client.ClientUDP import ClientUDP
from proxy.IdentificationRequest import IdentificationRequest

class ClientProxy():

    __REQUEST = 0

    def __init__(self):
        self.cdao = ClienteDAO()
        self.udpc = ClientUDP()

    def registrarCliente(self, nome, cpf, rg, endereco):
        cliente = {"nome":nome,"cpf":cpf,"rg":rg,"endereco":endereco}
        resposta = json.loads(self.__doOperation("Cliente", "registrarCliente", json.dump(cliente)).decode())
        print(str(resposta))
        if int(resposta['codigo']) == 200:
            return True
        else:
            return False

    def autenticar(self, nome, cpf):
        return self.cdao.autenticar(nome, cpf)

    def __doOperation(self, remoteObjectRef, methodId, arguments):
        mensagem = {
            "arguments":arguments,
            "method":methodId,
            "objectReference":remoteObjectRef,
            "requestId":IdentificationRequest.getRequestId(),
            "messageType": self.__REQUEST
        }

        self.udpc.sendRequest(json.dump(mensagem).encode())

        resposta = json.loads(self.udpc.getReplay().decode())
        IdentificationRequest.increment()
        return resposta['arguments'].encode()

    def finalizar(self):
        self.udpc.finaliza()

