package com.vmarkz.sqscrossaccountpoc.adapter.message

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider

@Component
class SqsConfig @Autowired constructor(
    @Qualifier("credentialsProvider")
    private val stsAssumeRoleCredentialsProvider: StsAssumeRoleCredentialsProvider,
//    private val awsSessionCredentials: AwsSessionCredentials,
    @Value("\${environment.aws.region}")
    private val region: String
) {

    @Bean
    fun sqsClient() : SqsClient = SqsClient.builder()
        .region(Region.of(region))
        .credentialsProvider(stsAssumeRoleCredentialsProvider)
        .build()


}