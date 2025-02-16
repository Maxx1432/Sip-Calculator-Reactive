package com.sip_calculator.Sip_Calculator.Configuration

import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

class RestTemplateConfiguration {
    @Bean
    fun getRestTemplate() : RestTemplate{
        return RestTemplate()
    }
}