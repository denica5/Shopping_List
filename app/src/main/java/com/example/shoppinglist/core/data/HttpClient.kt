package com.example.shoppinglist.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
//        install(Auth) {
//            bearer {
//                loadTokens {
//                    BearerTokens(
//                        accessToken = tokenStorage.getAccessToken(),
//                        refreshToken = tokenStorage.getRefreshToken()
//                    )
//                }
//
//                refreshTokens {
//                    if (response.status.value == 409) {
//
//                        val newTokens = refreshToken()
//
//                        tokenStorage.save(newTokens)
//
//                        BearerTokens(
//                            newTokens.accessToken,
//                            newTokens.refreshToken
//                        )
//                    } else {
//                        null
//                    }
//                }
//            }
//        }
    }
}