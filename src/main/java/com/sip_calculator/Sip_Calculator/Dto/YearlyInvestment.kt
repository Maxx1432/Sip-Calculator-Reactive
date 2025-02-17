package com.sip_calculator.Sip_Calculator.Dto

import java.math.BigDecimal

data class YearlyInvestment(
    var currentYear: Int = 0,
    var steppedUpSipValue: BigDecimal = BigDecimal.ZERO,
    var overAllInvested: BigDecimal = BigDecimal.ZERO,
    var stepSipPercentage: Int = 0,
    var totalInvestedYears: Int = 0,
    var roi: Double = 0.0,
    var compoundingFreq: Int = 12,
    var investedTime: Int = 1,
    var roiMonthly: Double = 0.0,
    var totalCompoundingTime: Int = 12,
    var totalSipInvestmentThisYear: BigDecimal = BigDecimal.ZERO,
    var sipAmountThisYear: BigDecimal = BigDecimal.ZERO,
    var directLumpSumInvestment: Int = 0,
    var monthOfLumpSumInvestment: List<Int> = emptyList(),
    var directLumpSumAmount: BigDecimal = BigDecimal.ZERO,
    var lastYearLumpSumInvestment: BigDecimal = BigDecimal.ZERO,
    var totalCompoundingTimeForLastYearLumpSumInvestment: Int = 12,
    var lastYearLumpSumAmount: BigDecimal = BigDecimal.ZERO,
    var netWorth: BigDecimal = BigDecimal.ZERO,
    var interestReceived: BigDecimal = BigDecimal.ZERO
)
