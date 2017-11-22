#-*-coding:utf-8 -*-

import getpass
import os

def showMain():
    print("#######################")
    print("#  1 - Registrar-se   #")
    print("#  2 - Login          #")
    print("#######################")
    option = int(input("Escolha: "))
    if option == 1:
        showRegistrar()
    elif option == 2:
        showLogin()
    else:
        print("Opçao invalida")

def showRegistrar():
    nome = input("Diga seu nome: ")
    cpf = input("Seu CPF: ")
    rg = input("Seu RG: ")
    endereco = input("Onde você mora: ")
    cliente = {
        "nome": nome,
        "cpf":cpf,
        "rg":rg,
        "endereco":endereco
    }
    print(cliente)
    showMenu()

def showLogin():
    nome = input("Usuario: ")
    senha = getpass.getpass('Senha:')
    print(nome, senha)
    showMenu()

def showMenu():
    while True:
        os.system('clear')
        print("##########################")
        print("# 1 - Listar Produtos    #")
        print("# 2 - Realizar Compra    #")
        print("##########################")
        option = int(input("Escolha: "))
        if option == 1:
            pass
        elif option == 2:
            pass
        else:
            print("Opção Inválida")


showMain()
