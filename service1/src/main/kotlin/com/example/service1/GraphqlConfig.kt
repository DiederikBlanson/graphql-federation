package com.example.service1

import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.data.federation.FederationSchemaFactory

@Configuration
class FederationConfig {

    @Bean
    fun graphQlSourceBuilderCustomizer(factory: FederationSchemaFactory): GraphQlSourceBuilderCustomizer =
        GraphQlSourceBuilderCustomizer {
            it.schemaFactory(factory::createGraphQLSchema)
        }

    @Bean
    fun schemaFactory(): FederationSchemaFactory = FederationSchemaFactory()
}