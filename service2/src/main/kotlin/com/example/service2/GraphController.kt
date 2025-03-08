package com.example.service2

import org.springframework.graphql.data.federation.EntityMapping
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class GraphController {

    @EntityMapping
    fun user(
        @Argument id: String
    ) = User (
        id = id
    )

    @SchemaMapping(typeName = "User", field = "email")
    fun email(
        user: User
    ): String = "my-custom-email@gmail.com"
}

data class User (
    val id: String
)