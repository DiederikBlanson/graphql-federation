import {
  createInlineSigningKeyProvider,
  defineConfig,
  extractFromHeader
} from '@graphql-hive/gateway'

const signingKey = 'my-secret-key'

export const gatewayConfig = defineConfig({
  jwt: {
    // Look and extract for the token in the 'authorization' header, with the 'Bearer' prefix.
    tokenLookupLocations: [extractFromHeader({ name: 'authorization', prefix: 'Bearer' })],
    // Decode and validate the token using the provided signing key.
    singingKeyProviders: [createInlineSigningKeyProvider(signingKey)],
    // Forward the verified token payload to the upstream GraphQL subgraphs.
    forward: {
      payload: true
    }
  }
})