package com.vmarkz.sqscrossaccountpoc.domain.port

interface SendSqsService {
    fun send(requestBody: String)
}