# GraphQL Federation with Spring Boot
This project consists of multiple Kotlin-based Spring Boot applications with GraphQL support, built using Maven. It demonstrates how to set up a GraphQL API with Apollo Federation support.

## Architecture
TODO

## Getting Started
This guide provides instructions for setting up Service 1. Most configuration steps remain the same for Service 2, except for the following files, which need specific adjustments:
- `service2/src/main/resources/graphql/schema.graphqls`
- `service2/src/main/resources/application.properties`
- `service2/src/main/kotlin/com/example/service1/GraphqlController`

You can find the contents of these files in the Service 2 folder.

### Create a Kotlin/Maven Project
1. Visit Spring Initializr.
2. Select the following options:
![alt text](/assets/spring-init.png)

### Add GraphQL support 
1. Add the following dependencies to your pom.xml file:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-graphql</artifactId>
</dependency>
<dependency>
   <groupId>org.springframework.graphql</groupId>
   <artifactId>spring-graphql</artifactId>
   <version>${spring-graphql.version}</version>
</dependency>
<dependency>
   <groupId>com.graphql-java</groupId>
   <artifactId>graphql-java-extended-scalars</artifactId>
   <version>22.0</version>
</dependency>
<dependency>
   <groupId>com.apollographql.federation</groupId>
   <artifactId>federation-graphql-java-support</artifactId>
   <version>5.3.0</version>
</dependency>
```
2. Define the GraphQL schema in `graphql/schema.graphqls`
```
extend schema @link(url: "https://specs.apollo.dev/federation/v2.3", import: ["@key"])

type Query {
    user(id: ID!): User
}

type User @key(fields: "id") {
    id: ID!
    name: String
}
```
3. Create a GraphQL controller to handle user queries:
```
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
```
4. Configure application properties in `application.properties`:
```
spring.graphql.graphiql.enabled=true
server.port=8081
```
5. Set up schema generation for federation:
``` 
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
```
6. Run the application!
- Follow the same steps for Service 2, modifying the necessary files. 
- Start the services on ports 8081 and 8082, respectively.

### Setup GraphQL federation
To configure the GraphQL gateway, follow these steps based on the GraphQL Mesh documentation: https://the-guild.dev/graphql/mesh/v1/getting-started:
1. Created a new folder called `mesh`
2. Install the required dependencies:
```
npm i @graphql-mesh/compose-cli
npm i @graphql-hive/gateway
```
3. Define the Mesh configuration with subgraph endpoints in `mesh.config.ts`
```
import { defineConfig, loadGraphQLHTTPSubgraph } from '@graphql-mesh/compose-cli'

export const composeConfig = defineConfig({
  subgraphs: [
    {
      sourceHandler: loadGraphQLHTTPSubgraph('Service 1', {
        endpoint: 'http://localhost:8081/graphql'
      })
    },{
      sourceHandler: loadGraphQLHTTPSubgraph('Service 2', {
        endpoint: 'http://localhost:8082/graphql'
      })
    }
  ],
  fetch
})
```
4. Generate the supergraph:
```
npx mesh-compose -o supergraph.graphql
```
5. Run the supergraph gateway:
```
npx hive-gateway supergraph
```
6. Open `http://localhost:4000` in your browser. You should see:
![alt text](/assets/hive-gateway.png)
7. Navigate to `http://localhost:4000/graphql`. The supergraph is now accessible, and you can query fields from both Service 1 and Service 2:
![alt text](/assets/example-query.png)

## Other sources
- JWT Support https://the-guild.dev/graphql/hive/docs/gateway/authorization-authentication#in-upstream-graphql-subgraphs