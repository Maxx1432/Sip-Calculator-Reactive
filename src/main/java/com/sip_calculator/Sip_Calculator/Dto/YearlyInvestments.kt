package com.sip_calculator.Sip_Calculator.Dto


import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class YearlyInvestments @JsonCreator constructor(
    @JsonProperty("yearlyInvestments")
    val yearlyInvestments: List<YearlyInvestment>
) {
    // Secondary constructor for convenience
    constructor() : this(emptyList())
}