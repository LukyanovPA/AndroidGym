package di

import helper.Endpoints
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val CONNECT_TIMEOUT = 15000L
private const val REQUEST_TIMEOUT = 30000L

internal val ktorModule = module {
    single {
        HttpClient {

            val baseUrl = Endpoints.BASE_URL_DEBUG

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(HttpTimeout) {
                connectTimeoutMillis = CONNECT_TIMEOUT
                requestTimeoutMillis = REQUEST_TIMEOUT
            }

            defaultRequest {
                url(baseUrl)
            }
        }
    }
}