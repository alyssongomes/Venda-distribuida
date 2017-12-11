#-*-coding:utf-8 -*-

import getpass
import os
import sys
import time

from proxy.ClientProxy import ClientProxy
from proxy.DatabaseProxy import DatabaseProxy
from proxy.ProdutoProxy import ProdutoProxy
from proxy.VendaProxy import VendaProxy

class TerminalVenda():

    def __init__(self):
        self.clienteSessao = None
        self.cproxy = ClientProxy()
        self.dbproxy = DatabaseProxy()
        self.pproxy = ProdutoProxy()
        self.vproxy = VendaProxy()

    def verificarBase(self):
        return self.dbproxy.verificarBaseReplica()

    def showMain(self):
        print("#######################")
        print("#  1 - Registrar-se   #")
        print("#  2 - Login          #")
        print("#  3 - Sair           #")
        print("#######################")
        option = int(input("Escolha: "))
        if option == 1:
            self.showRegistrar()
        elif option == 2:
            self.showLogin()
        elif option == 3:
            sys.exit()
        else:
            print("Opçao invalida")

    def showRegistrar(self):
        try:
            nome = input("Diga seu nome: ")
            cpf = int(input("Seu CPF (apenas números): "))
            rg = int(input("Seu RG(apenas números): "))
            endereco = input("Onde você mora: ")

            if self.cproxy.registrarCliente(nome,cpf,rg,endereco):
                print("Cliente cadastrado com sucesso")
                self.clienteSessao = {
                    "nome":nome,
                    "cpf":cpf,
                    "rg":rg,
                    "endereco":endereco
                }
                self.showMenu()
            else:
                print("Não foi possível cadastrar o cliente")
                self.showMain()
        except Exception as e:
            print("Informe dados válidos"+str(e))

    def showLogin(self):
        nome = input("Usuario: ")
        cpf = getpass.getpass('CPF (apenas números):')
        self.clienteSessao = self.cproxy.autenticar(nome, cpf)
        if self.clienteSessao is not None:
            os.system('clear')
            print("Bem-vindo! "+self.clienteSessao['nome'])
            while True:
                self.showMenu()
        else:
            self.showMain()

    def showProdutos(self):
        produtos = self.pproxy.listarProdutos()
        for p in produtos:
            print(str(p))

    def realizarCompra(self):
        dataVenda = time.strftime("%Y-%m-%d %H:%M:%S")
        try:
            self.showProdutos()
            produtoId = int(input("Informe o Id do produto que deseja comprar"))
            qtd = int(input("Informe a quantidade de produtos"))
            modoPagamento = int(input("1-Débito\n2-Crédito\nInforme o modo de Pagamento"))
            numCartao = int(input("Informe o número do cartão (apenas número)"))

            prod = None
            for p in self.pproxy.listarProdutos():
                if int(p['id']) == produtoId:
                    prod = p

            if self.vproxy.realizarVenda(self.clienteSessao,prod, qtd, dataVenda,modoPagamento,numCartao):
                print("Compra realizada com sucesso!")
                self.showMenu()
            else:
                print("Não foi possível efetuar a compra!")
                self.showMenu()

        except Exception as e:
            print("Houve um erro ao realizar a compra:"+str(e))

    def showMenu(self):
        os.system('clear')
        print("##########################")
        print("# 1 - Listar Produtos    #")
        print("# 2 - Realizar Compra    #")
        print("# 3 - Sair               #")
        print("##########################")
        option = int(input("Escolha: "))
        if option == 1:
            self.showProdutos()
        elif option == 2:
            self.realizarCompra()
        elif option == 3:
            self.showMain()
        else:
            print("Opção Inválida")


terminal = TerminalVenda()
if(terminal.verificarBase()):
    while True:
        terminal.showMain()
else:
    print("Nao foi possivel fazer a verificaçao da base no mestre!")
