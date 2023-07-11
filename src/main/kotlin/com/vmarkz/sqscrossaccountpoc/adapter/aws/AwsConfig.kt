package com.vmarkz.sqscrossaccountpoc.adapter.aws

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sts.StsClient
import software.amazon.awssdk.services.sts.auth.StsAssumeRoleCredentialsProvider
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse


@Configuration
class AwsConfig (
    @Value("\${environment.aws.roleArn}")
    private val roleArn: String,
    @Value("\${environment.aws.roleSessionName}")
    private val roleSessionName: String,
    @Value("\${environment.aws.region}")
    private val region: String
) {

    private final val stsClient: StsClient = StsClient.builder()
        .region(Region.of(region))
//        .credentialsProvider(ProfileCredentialsProvider.create())
        .build()

    private final val assumeRoleRequest: AssumeRoleRequest = AssumeRoleRequest
        .builder()
        .roleArn(roleArn)
        .roleSessionName(roleSessionName)
        .build()

    private final val assumeRoleResponse: AssumeRoleResponse = stsClient.assumeRole(assumeRoleRequest)

//    @Bean
//    fun awsCredentials(): AwsSessionCredentials =
//        assumeRoleResponse
//            .credentials()
//            .let { sessionCredentials ->
//                AwsSessionCredentials.create(
//                    sessionCredentials.accessKeyId(),
//                    sessionCredentials.secretAccessKey(),
//                    sessionCredentials.sessionToken()
//                )
//            }


    @Bean(name = ["credentialsProvider"])
    fun credentialsProvider() = StsAssumeRoleCredentialsProvider.builder()
        .refreshRequest(assumeRoleRequest)
        .stsClient(stsClient)
        .build()

}