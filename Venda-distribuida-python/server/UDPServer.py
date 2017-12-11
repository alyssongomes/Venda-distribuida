from socket import *

# Create a UDP socket

serverPort = 12000
serverSocket = socket(AF_INET, SOCK_DGRAM)

serverSocket.bind(('',	serverPort))


print('The server is ready to receive')

while 1:
    message, clientAddress = serverSocket.recvfrom(2048)
    modifiedMessage = message.upper()
    print(modifiedMessage)
    #serverSocket.sendto(modifiedMessage, clientAddress)


class ServerUDP():

    def __init__(self):
        pass

    def getRequest(self):
        pass

    def sendReply(self,resposta):
        pass

    def finaliza(self):
        pass

    def verificarPacotes(self, packets, newPacket):
        pass

    def sendAndStore(self, request, despachante, historico):
        pass