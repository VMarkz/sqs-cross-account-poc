package com.vmarkz.sqscrossaccountpoc.domain.services

import com.vmarkz.sqscrossaccountpoc.domain.port.SendSqsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

@Service
class SendSqsServiceImpl @Autowired constructor(
    private val sqsClient: SqsClient,
    @Value("\${environment.sqs.queueUrl}")
    private val queueUrl: String
): SendSqsService {

    private val logger: Logger = LoggerFactory.getLogger(SendSqsServiceImpl::class.java)

    override fun send(requestBody: String) {
        try {
            sqsClient.sendMessage(
                SendMessageRequest.builder().messageBody(requestBody).queueUrl(queueUrl).build()
            ).messageId()
                .also {
                    logger.info("Message ID: $it")
                }
        } catch (e: Exception) {
            logger.error(
                mapOf(
                    "Message: " to "Failed to send message.",
                    "Error: " to e.message,
                    "Cause: " to e.cause
                ).toString()
            )
        }

    }
}