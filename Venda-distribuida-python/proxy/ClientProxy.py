#-*-coding:utf-8 -*-

from dao.ClienteDAO import ClienteDAO
from client.ClientUDP import ClientUDP

class ClientProxy():

    def __init__(self):
        self.cdao = ClienteDAO()
        self.udpc = ClientUDP()

    def registrarCliente(self, nome, cpf, rg, endereco):
        pass

    def autenticar(self, nome, cpf):
        return self.cdao.autenticar(nome, cpf)