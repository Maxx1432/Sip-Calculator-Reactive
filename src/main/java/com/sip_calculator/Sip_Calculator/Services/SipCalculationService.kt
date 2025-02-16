package com.sip_calculator.Sip_Calculator.Services

import com.sip_calculator.Sip_Calculator.Dto.InvestmentDetailsDto
import com.sip_calculator.Sip_Calculator.Dto.LumpSumData
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment
import com.sip_calculator.Sip_Calculator.Util.CustomUtils
import org.springframework.beans.factory.annotation.Value
import reactor.core.publisher.Mono
import java.math.BigDecimal

class SipCalculationService(@Value("\${enable.lumpsum}") private var enableLumpSum : Boolean) {
    fun calculation(
        investmentDetails : InvestmentDetailsDto,
        currentYearInvestment : YearlyInvestment,
        lastYearInvestment : YearlyInvestment?,
        lumpSumDataList : List<LumpSumData>
    ) : Mono<Unit>{
        enableLumpSum = lumpSumDataList.isNotEmpty()
        return if (lastYearInvestment == null){
            calculationForStartingYear(investmentDetails, currentYearInvestment, lumpSumDataList)
        } else{
            calcualtionAsPerLastYear(investmentDetails, currentYearInvestment, lastYearInvestment, lumpSumDataList)
        }
    }

    private fun calculationForStartingYear(
        investmentDetails: InvestmentDetailsDto,
        currentYearInvestment: YearlyInvestment,
        lumpSumDataList: List<LumpSumData>
    ): Mono<Unit> {
        return Mono.fromCallable {
            val stepSipPercentage = investmentDetails.stepSipPercentage
            val roi = investmentDetails.roiPercentage / 100.00
            val roiMonthly = roi / 12
            var steppedUpSipValue = CustomUtils.calculatedSteppedUpSipValue(BigDecimal.valueOf(investmentDetails.monthlySipAmt.toDouble()),stepSipPercentage)

            if (currentYearInvestment.currentYear == investmentDetails.startingYear) {
                steppedUpSipValue = CustomUtils.calculatedSteppedUpSipValue(BigDecimal.valueOf(investmentDetails.monthlySipAmt.toDouble()), 0)
            }

            val lastYearLumpSumInvestment = BigDecimal.ZERO
            val sipAmountThisYear = CustomUtils.compoundSipAmount(steppedUpSipValue, roiMonthly, 12)
            var totalSipInvestmentThisYear = steppedUpSipValue.multiply(BigDecimal.valueOf(12))
            val lastYearLumpSumAmount = BigDecimal.ZERO
            var netWorth = sipAmountThisYear.add(lastYearLumpSumAmount)

            val result = CustomUtils.performCalculationForLumpSumInvestments(enableLumpSum, roiMonthly, currentYearInvestment, lumpSumDataList, netWorth, totalSipInvestmentThisYear)
            result?.let {
                totalSipInvestmentThisYear = it[0]
                netWorth = it[1]
            }

            currentYearInvestment.apply {
                this.totalSipInvestmentThisYear = totalSipInvestmentThisYear
                this.steppedUpSipValue = steppedUpSipValue
                this.stepSipPercentage = stepSipPercentage
                this.roi = roi
                this.compoundingFreq = 12
                this.investedTime = 1
                this.roiMonthly = roiMonthly
                this.totalCompoundingTime = 12
                this.sipAmountThisYear = sipAmountThisYear
                this.lastYearLumpSumInvestment = lastYearLumpSumInvestment
                this.lastYearLumpSumAmount = lastYearLumpSumAmount
                this.totalCompoundingTimeForLastYearLumpSumInvestment = 12
                this.netWorth = netWorth
                this.overAllInvested = overAllInvested
                this.interestReceived = netWorth.subtract(overAllInvested)
                this.totalInvestedYears = investmentDetails.numberOfYears
            }

            Mono.empty<>()
        }
    }
}