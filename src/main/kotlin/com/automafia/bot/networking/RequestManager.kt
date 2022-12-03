package com.automafia.bot.networking

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse as HttpResponse

enum class RequestMethods {
    POST,
    PUT,
    GET
}

@Service
class RequestManager {
    private val host: String = "http://localhost:9292"

    public fun get(params: Map<String, String>, command: Endpoints): HttpResponse<String> {
        val urlParams = toUrlParams(params)

        val urlString = getApiEndpointBy(command)
        val request = createRequest(RequestMethods.GET, createUrl(urlString, urlParams), null)

        val response = createResponse(request)
        println("get request" + response.statusCode())
        return response
    }

    fun post(values: Map<Any, Any>, command: Endpoints, params: Map<String, String>): HttpResponse<String> {
        val urlParams = toUrlParams(params)
        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper.writeValueAsString(values)

        val urlString = getApiEndpointBy(command)
        val request = createRequest(RequestMethods.POST, createUrl(urlString, urlParams), requestBody)

        val response = createResponse(request)
        println("post request" + response.statusCode())
        return response
    }


    private fun getApiEndpointBy(command: Endpoints): String {
        val urlEndpoint = when (command) {
            Endpoints.HEALTH -> "/api/health"
            Endpoints.GET_GAMES -> "/api/all"
            Endpoints.GET_GAME -> "/api/find/"
            Endpoints.CREATE_GAME -> "/api/create"
            Endpoints.CONNECT_GAME -> "/api/connect/"
            Endpoints.SET_TARGET -> "/api/target/"
            Endpoints.NEXT_GO -> "/api/next-go/" //only for game owner
            Endpoints.END_GAME -> "/api/end-game/" //only for game owner
            Endpoints.NEXT_ROUND -> "/api/next-round/" //only for game owner
            Endpoints.INITIALIZE_CONFIGS -> "/api/default-configs"
            Endpoints.CONFIG_LIST -> "/api/game-configs"
            Endpoints.CONFIG_SELECTED -> "/api/create-on-config"
        }
        return urlEndpoint
    }

    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

    private fun createUrl(endpoint: String, params: String): String = "$host$endpoint?$params"

    private fun toUrlParams(params: Map<String, String>): String = params.map { (k, v) -> "${(k.utf8())}=${v.utf8()}" }
        .joinToString("&")

    private fun createRequest(method: RequestMethods, url: String, requestBody: String?): HttpRequest {
        return when (method) {
            RequestMethods.GET -> HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build()

            RequestMethods.POST -> HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build()

            RequestMethods.PUT -> HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build()
        }
    }

    private fun createResponse(request: HttpRequest): HttpResponse<String> {
        val client = HttpClient.newBuilder().build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}