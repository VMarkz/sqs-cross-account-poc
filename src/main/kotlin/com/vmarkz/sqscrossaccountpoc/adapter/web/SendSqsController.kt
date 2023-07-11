package com.vmarkz.sqscrossaccountpoc.adapter.web

import com.vmarkz.sqscrossaccountpoc.domain.port.SendSqsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/v1/sqs")
class SendSqsController @Autowired constructor(
    private val sendSqsService: SendSqsService
) {

    private val logger: Logger = LoggerFactory.getLogger(SendSqsController::class.java)

    @PostMapping("/send")
    fun send(@RequestBody requestBody: String) {
        logger.info("Received request: $requestBody")
        sendSqsService.send(requestBody)
    }
}