package com.sip_calculator.Sip_Calculator.Util

import com.sip_calculator.Sip_Calculator.Dto.LumpSumData
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment
import java.math.BigDecimal
import java.math.RoundingMode

object CustomUtils {
    /**
     * Calculates the stepped-up SIP value based on the given percentage increase.
     */

    fun calculatedSteppedUpSipValue(
        previousValue: BigDecimal,
        stepPercentage: Int
    ) : BigDecimal{
        val increaseFactor = BigDecimal.valueOf(stepPercentage.toDouble() / 100.0)
        return previousValue.add(previousValue.multiply(increaseFactor)).setScale(2, RoundingMode.HALF_UP)
    }

    /**
     * Computes the future value of a SIP investment using monthly compounding.
     */

    fun compoundSipAmount(
        sipValue: BigDecimal,
        monthlyROI : Double,
        compoundingPeriods : Int
    ) : BigDecimal{
        var total = BigDecimal.ZERO
        for (i in 1..compoundingPeriods){
            val compoundedValue = sipValue.multiply(BigDecimal.valueOf(Math.pow(1 + monthlyROI, (compoundingPeriods - i + 1).toDouble())))
            total = total.add(compoundedValue)
        }
        return total.setScale(2,RoundingMode.HALF_UP)
    }

    /**
     * Computes the compounded value of a lump sum investment after a certain period.
     */
    fun compoundedLumpSumAmountForLastYear(
        initialInvestment: BigDecimal,
        monthlyROI: Double,
        months: Int
    ): BigDecimal {
        val factor = BigDecimal.valueOf(Math.pow(1 + monthlyROI, months.toDouble()))
        return initialInvestment.multiply(factor).setScale(2, RoundingMode.HALF_UP)
    }

    /**
     * Performs calculations for lump sum investments.
     */
    fun performCalculationForLumpSumInvestments(
        enableLumpSum: Boolean,
        roiMonthly: Double,
        currentYearInvestment: YearlyInvestment,
        lumpSumDataList: List<LumpSumData>?,
        netWorth: BigDecimal,
        overAllInvested: BigDecimal
    ): Array<BigDecimal>? {
        if (!enableLumpSum || lumpSumDataList.isNullOrEmpty()) {
            return null
        }

        var updatedNetWorth = netWorth
        var updatedInvested = overAllInvested

        for (lumpSum in lumpSumDataList) {
            val compoundedAmount = compoundedLumpSumAmountForLastYear(BigDecimal.valueOf(lumpSum.totalInvestment.toDouble()), roiMonthly, lumpSum.startingInvestmentMonth)
            updatedNetWorth = updatedNetWorth.add(compoundedAmount)
            updatedInvested = updatedInvested.add(BigDecimal.valueOf(lumpSum.totalInvestment.toDouble()))
        }

        return arrayOf(updatedInvested, updatedNetWorth)
    }

    /**
     * Rounds all BigDecimal values in YearlyInvestment to two decimal places.
     */
    fun setDecimalToTwoPlaces(investment: YearlyInvestment) {
        investment.apply {
            totalSipInvestmentThisYear = totalSipInvestmentThisYear.setScale(2, RoundingMode.HALF_UP)
            steppedUpSipValue = steppedUpSipValue.setScale(2, RoundingMode.HALF_UP)
            sipAmountThisYear = sipAmountThisYear.setScale(2, RoundingMode.HALF_UP)
            lastYearLumpSumInvestment = lastYearLumpSumInvestment.setScale(2, RoundingMode.HALF_UP)
            lastYearLumpSumAmount = lastYearLumpSumAmount.setScale(2, RoundingMode.HALF_UP)
            netWorth = netWorth.setScale(2, RoundingMode.HALF_UP)
            overAllInvested = overAllInvested.setScale(2, RoundingMode.HALF_UP)
            interestReceived = interestReceived.setScale(2, RoundingMode.HALF_UP)
        }
    }
}