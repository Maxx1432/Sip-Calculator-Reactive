package com.sip_calculator.Sip_Calculator.Exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
class RateLimitingException(message:String ) : RuntimeException(message) {
}