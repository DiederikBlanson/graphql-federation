package com.example.service2

import org.springframework.graphql.data.federation.EntityMapping
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.stereotype.Controller

@Controller
class GraphController {

    @EntityMapping
    fun user(
        @Argument id: String
    ) = User (
        id = id
    )

    @BatchMapping(typeName = "User", field = "email")
    fun emails(users: List<User>): Map<User, String> {
        return users.associateWith { "user-${it.id}@example.com" }
    }
}

data class User (
    val id: String
)