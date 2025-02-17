package com.sip_calculator.Sip_Calculator.Services

import com.sip_calculator.Sip_Calculator.Dto.InvestmentDetailsDto
import com.sip_calculator.Sip_Calculator.Dto.LumpSumData
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment
import com.sip_calculator.Sip_Calculator.Util.CustomUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.math.BigDecimal

@Service
class SipCalculationService(@Value("\${enable.lumpsum}") private var enableLumpSum : Boolean) {
    fun calculation(
        investmentDetails : InvestmentDetailsDto,
        currentYearInvestment : YearlyInvestment,
        lastYearInvestment : YearlyInvestment?,
        lumpSumDataList : List<LumpSumData>
    ) : Mono<Unit>{
        val isLumpSumEnabled = enableLumpSum && lumpSumDataList.isNotEmpty()
        return if (lastYearInvestment == null){
            calculationForStartingYear(investmentDetails, currentYearInvestment, lumpSumDataList, isLumpSumEnabled)
        } else{
            calculationAsPerLastYear(investmentDetails, currentYearInvestment, lastYearInvestment, lumpSumDataList, isLumpSumEnabled)
        }
    }

    private fun calculationForStartingYear(
        investmentDetails: InvestmentDetailsDto,
        currentYearInvestment: YearlyInvestment,
        lumpSumDataList: List<LumpSumData>,
        isLumpSumEnabled: Boolean
    ): Mono<Unit> {
        return Mono.defer {
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

            val result = CustomUtils.performCalculationForLumpSumInvestments(isLumpSumEnabled, roiMonthly, currentYearInvestment, lumpSumDataList, netWorth, totalSipInvestmentThisYear)
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

            Mono.just(Unit)
        }
    }

    private fun calculationAsPerLastYear(
        investmentDetails: InvestmentDetailsDto,
        currentYearInvestment: YearlyInvestment,
        lastYearInvestment: YearlyInvestment,
        lumpSumDataList: List<LumpSumData>,
        isLumpSumEnabled : Boolean
    ): Mono<Unit> {
        return Mono.defer {
            val stepSipPercentage = investmentDetails.stepSipPercentage
            val roi = investmentDetails.roiPercentage / 100.0
            val roiMonthly = roi / 12.0

            val steppedUpSipValue = CustomUtils.calculatedSteppedUpSipValue(lastYearInvestment.steppedUpSipValue, stepSipPercentage)
            val lastYearLumpSumInvestment = lastYearInvestment.netWorth
            val lastYearLumpSumAmount = CustomUtils.compoundedLumpSumAmountForLastYear(lastYearLumpSumInvestment, roiMonthly, 12)
            val sipAmountThisYear = CustomUtils.compoundSipAmount(steppedUpSipValue, roiMonthly, 12)
            val totalSipInvestmentThisYear = steppedUpSipValue.multiply(BigDecimal.valueOf(12))
            var netWorth = lastYearLumpSumAmount.add(sipAmountThisYear)
            var overAllInvested = lastYearInvestment.overAllInvested.add(totalSipInvestmentThisYear)

            val result = CustomUtils.performCalculationForLumpSumInvestments(isLumpSumEnabled, roiMonthly, currentYearInvestment, lumpSumDataList, netWorth, overAllInvested)
            result?.let {
                overAllInvested = it[0]
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

            Mono.just(Unit)
        }
    }

    fun calculationForJustLumpSum(
        investmentDetails: InvestmentDetailsDto,
        currentYearInvestment: YearlyInvestment,
        lastYearInvestment: YearlyInvestment?,
        returnList: MutableList<YearlyInvestment>
    ): Mono<Unit> {
        return Mono.defer {
            val roi = investmentDetails.roiPercentage / 100.0
            val roiMonthly = roi / 12.0

            val (lumpSumInvestment, totalInvestedAmount) = if (returnList.isEmpty()) {
                val initialInvestment = BigDecimal.valueOf(investmentDetails.monthlySipAmt.toDouble())
                initialInvestment to CustomUtils.compoundedLumpSumAmountForLastYear(initialInvestment, roiMonthly, 12)
            } else {
                val previousInvestment = lastYearInvestment?.directLumpSumAmount ?: BigDecimal.ZERO
                previousInvestment to CustomUtils.compoundedLumpSumAmountForLastYear(previousInvestment, roiMonthly, 12)
            }

            currentYearInvestment.apply {
                this.directLumpSumAmount = totalInvestedAmount
                this.roiMonthly = roiMonthly
                this.roi = roi
                this.netWorth = totalInvestedAmount
                this.interestReceived = totalInvestedAmount.subtract(BigDecimal.valueOf(investmentDetails.monthlySipAmt.toDouble()))
            }

            CustomUtils.setDecimalToTwoPlaces(currentYearInvestment)
            returnList.add(currentYearInvestment)

            Mono.just(Unit)
        }
    }
}