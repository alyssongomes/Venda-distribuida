#-*-coding:utf-8 -*-

import json

from client.ClientUDP import ClientUDP
from proxy.IdentificationRequest import IdentificationRequest

class VendaProxy():

    __REQUEST = 0

    def __init__(self):
        self.udpc = ClientUDP()

    def realizarVenda(self, cliente, produto, qtd, dataVenda, modoPagamento, numCartao):
        venda = {
            "cliente":cliente,
            "produto":produto,
            "quantidade":qtd,
            "total":qtd*float(produto['valor']),
            "dataVenda":dataVenda
        }
        pagamento = {"numCartao":numCartao,"modoPagamento":modoPagamento}
        venda['pagamento'] = pagamento

        resposta = json.loads(self.__doOperation("Venda","realizarVenda",json.dump(venda)).decode())
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