package com.example.service1
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class GraphController {

    @QueryMapping
    fun user(
        @Argument id: String
    ): User {
        return User(id, "User $id")
    }
}

data class User(val id: String, val name: String)

