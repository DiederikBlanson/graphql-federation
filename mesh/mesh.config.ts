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