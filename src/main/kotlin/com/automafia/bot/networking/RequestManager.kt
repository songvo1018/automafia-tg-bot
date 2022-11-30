package com.automafia.bot.networking

import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse as HttpResponse


class RequestJson {
    fun String.utf8(): String = URLEncoder.encode(this, "UTF-8")

    fun get(params: Map<String, String>, urlString: String) : HttpResponse<String> {
        val urlParams = params.map {(k, v) -> "${(k.utf8())}=${v.utf8()}"}
            .joinToString("&")

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$urlString?${urlParams}"))
            .build();

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())
        return  response
    }

    fun post (values: Map<Any, Any>, urlString: String) : HttpResponse<String> {
        val objectMapper = ObjectMapper()
        val requestBody: String = objectMapper
            .writeValueAsString(values)

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create(urlString))
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())
        return  response
    }
}