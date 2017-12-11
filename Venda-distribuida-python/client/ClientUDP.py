#!/usr/bin/env python
# -*-coding: utf-8 -*-

from socket import *

class ClientUDP():

    def __init__(self, serverName = '127.0.0.1', serverPort = 12000):
        self.serverName = serverName
        self.serverPort = serverPort
        self.clientSocket = socket(AF_INET, SOCK_DGRAM)
        self.message = None

    def sendRequest(self, message):
        try:
            self.message = message
            self.clientSocket.sendto(self.message.encode(), (self.serverName, self.serverPort))
            self.clientSocket.settimeout(2/1000)
        except error as e:
            print(str(e))
        except IOError as io:
            print(str(io))

    def getReplay(self):
        while True:
            try:
                messageReply, serverAddress = self.clientSocket.recvfrom(2048)
                return messageReply

            except timeout:
                print('reenviando...')
                try:
                    self.clientSocket.sendto(self.message.encode(), (self.serverName, self.serverPort))
                    self.clientSocket.settimeout(2/1000)
                except error as e:
                    print(str(e))
                except IOError as io:
                    print(str(io))
            except IOError as io:
                print(str(io))

    def finaliza(self):
        try:
            self.clientSocket.close()
        except error as e:
            print(str(e))


