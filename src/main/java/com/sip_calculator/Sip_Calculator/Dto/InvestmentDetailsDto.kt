package com.sip_calculator.Sip_Calculator.Dto

data class InvestmentDetailsDto(
    val startingYear : Int,
    val monthlySipAmt : Int,
    val stepSipPercentage : Int,
    val roiPercentage : Int,
    val investedTime : Int, // always1
    val numberOfYears : Int,
    val lumpsumdata : List<LumpSumData>
)
