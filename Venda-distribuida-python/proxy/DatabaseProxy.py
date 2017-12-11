#-*-coding:utf-8 -*-

import json

from client.ClientUDP import ClientUDP
from server.UDPServer import ServerUDP
from proxy.IdentificationRequest import IdentificationRequest

class DatabaseProxy():

    __REQUEST = 0

    def __init__(self):
        self.udpc = ClientUDP()

    def verificarBaseReplica(self):
        database = {"ip":ServerUDP.IP,"porta":ServerUDP.PORTA}
        resposta = json.loads(self.__doOperation("Database", "verificarBaseReplica", json.dump(database)).decode())
        print(str(resposta))
        if int(resposta['codigo']) == 200:
            return True
        else:
            return False

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