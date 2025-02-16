package com.sip_calculator.Sip_Calculator.Controller

import com.sip_calculator.Sip_Calculator.SipCalculatorApplication
import com.sip_calculator.Sip_Calculator.Util.FileUtils
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RestController

@RestController("/sip")
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
class SipController(private val fileUtils : FileUtils,
    private val sipCalculator) {


}