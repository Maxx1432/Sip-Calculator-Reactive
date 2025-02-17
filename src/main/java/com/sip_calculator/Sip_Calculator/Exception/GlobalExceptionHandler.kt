package com.sip_calculator.Sip_Calculator.Exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.sip_calculator.Sip_Calculator.Dto.ErrorMessage
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandler : ErrorWebExceptionHandler  {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val response = exchange.response
        response.statusCode = when(ex){
            is RateLimitingException -> HttpStatus.TOO_MANY_REQUESTS
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }
        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorMessage = ErrorMessage(message = ex.message ?: "Unexpected error occurred")

        val bufferFactory = response.bufferFactory()
        val dataBuffer = bufferFactory.wrap(ObjectMapper().writeValueAsBytes(errorMessage))

        return response.writeWith(Mono.just(dataBuffer))
    }
}