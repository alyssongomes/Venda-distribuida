#-*-coding:utf-8 -*-

import json

from socket import *

from server.Despachante import Despachante

class ServerUDP():

    __REPLY = 1
    PORTA = 5432
    IP = "127.0.0.1"

    def __init__(self):
        try:
            self.serverSocket = socket(AF_INET, SOCK_DGRAM)
            self.serverSocket.bind(('',	self.PORTA))
        except Exception as e:
            print('Não foi possível criar uma intancia:' + str(e))

    def getRequest(self):
        try:
            #clientAddress = (IP, PORTA)
            message, clientAddress = self.serverSocket.recvfrom(2048)
            return message,clientAddress
        except Exception as e:
            print('Não foi possível receber a requisição: '+str(e))

    def sendReply(self,resposta, clientAddress):
        try:
            self.serverSocket.sendto(resposta, clientAddress)
        except error as e:
            print(str(e))
        except IOError as io:
            print(str(io))

    def finaliza(self):
        try:
            if self.serverSocket is not None:
                self.serverSocket.close()
        except Exception as e:
            print(str(e))

    def verificarPacotes(self, packets, message, clientAddress):
        for p in packets:
            # Se o IP e a Porta foram iguais
            if p['client'][0] == clientAddress[0] and p['client'][1] == clientAddress[1]:
                mensagePkOne = json.loads(message)
                mensagePkTwo = json.loads(p['response'])

                if int(mensagePkOne['requestId']) == int(mensagePkTwo['requestId']):
                    message = p['response']
                    return True
                elif int(mensagePkOne['requestId']) > int(mensagePkTwo['requestId']):
                    packets.remove(p)
                    print('Removeu requisiçoes antigas')
                    return False


    def sendAndStore(self, request, clientAddress, despachante, historico):
        #Descompactando
        mensagem = json.loads(request)
        print(str(mensagem))

        #Pegando o resultado
        reply = despachante.invoke(mensagem)
        mensagem['arguments'] = reply
        mensagem['messageType'] = self.__REPLY

        #Empacotando o resultado e envia
        self.sendReply(reply, clientAddress)

        #Guarda o pacote no histórico
        historico.append({'response':reply,'client':clientAddress})


