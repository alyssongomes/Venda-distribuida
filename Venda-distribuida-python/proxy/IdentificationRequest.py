#-*-coding:utf-8 -*-

class IdentificationRequest():

    _requestId = 0

    @staticmethod
    def increment():
        IdentificationRequest._requestId += 1

    @staticmethod
    def getRequestId():
        return IdentificationRequest._requestId
