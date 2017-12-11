#-*-coding:utf-8 -*-

from dao.ProdutoDAO import ProdutoDAO

class ProdutoProxy():

    def __init__(self):
        self.pdao = ProdutoDAO()

    def listarProdutos(self):
        return self.pdao.selectAll()

    def encontrarProduto(self, id):
        return self.pdao.select(id)

