package com.example.service1
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GraphController {

    @QueryMapping
    fun user(
        @Argument id: String
    ): User? {
        return users().find { it.id == id }
    }

    @QueryMapping
    fun users(): List<User> =
        listOf(
            User("1", "Diederik"),
            User("2", "John"),
            User("3", "Arnold")
        )
}

data class User(val id: String, val name: String)

