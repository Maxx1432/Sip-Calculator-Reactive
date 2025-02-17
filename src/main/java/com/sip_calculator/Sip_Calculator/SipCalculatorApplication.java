package com.sip_calculator.Sip_Calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.sip_calculator.Sip_Calculator")
public class SipCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SipCalculatorApplication.class, args);
	}

}
