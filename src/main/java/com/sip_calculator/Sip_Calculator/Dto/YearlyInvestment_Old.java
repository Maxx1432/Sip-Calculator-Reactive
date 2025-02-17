package com.sip_calculator.Sip_Calculator.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;
@Data
@Accessors(chain = true)
@Setter
@Getter
public class YearlyInvestment_Old {

    /*
    This value needs to be incremented for following year on the basis numberOfYears
     */
    Integer currentYear;

    /*
    default value if the stepSipPercentage is 0 -- for first year
    Other-wise use the calulation logic to calculate the stepped up sip
     */
    BigDecimal steppedUpSipValue;

    Integer stepSipPercentage;

    /*
    Same as numberOfYears
    */
    Integer totalInvestedYears;

    /*
    Yearly roi
     */
    Double roi;

    /*
    12 for monthly
     */
    Integer compoundingFreq;

    // always 1
    Integer investedTime;

    /*
    roi / compoundingFreq
     */
    Double roiMonthly;

    // compoundingFreq * investedTime = 12
    Integer totalCompoundingTime;

    // steppedUpSipValue * 12
    BigDecimal totalSipInvestmentThisYear;

    /*
    totalSipInvestmentThisYear + interest on this sipInvestment
     */
    BigDecimal sipAmountThisYear;

    /*
    directLumpSumInvestment from frontend
     */
    Integer directLumpSumInvestment;

    List<Integer> monthOfLumpSumInvestment;

    /*
    directLumpSumInvestment + interest on this lumpsum as per monthOfLumpsumInvestment
     */
    BigDecimal directLumpSumAmount;

    /*
    Last year's net worth, for first year this field would be zero
     */
    BigDecimal lastYearLumpSumInvestment;

    /*
    months -- 12
     */
    Integer totalCompoundingTimeForLastYearLumpSumInvestment;

    /*
    Compounded value of lastYearLumpSumInvestment for totalCompoundingTimeForLastYearLumpSumInvestment
     */
    BigDecimal lastYearLumpSumAmount;

    /*
        totalSipInvestmentThisYear + totalSipInvestmentThisYear(for last years) + directLumpSumInvestment for this year + directLumpSumInvestment(for last years)
     */
    BigDecimal overAllInvested;

    /*
    sipAmountThisYear + directLumpSumAmount + lastYearLumpSumAmount
     */
    BigDecimal netWorth;

    public YearlyInvestment_Old setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
        return this;
    }

    public YearlyInvestment_Old setSteppedUpSipValue(BigDecimal steppedUpSipValue) {
        this.steppedUpSipValue = steppedUpSipValue;
        return this;
    }

    public YearlyInvestment_Old setStepSipPercentage(Integer stepSipPercentage) {
        this.stepSipPercentage = stepSipPercentage;
        return this;
    }

    public YearlyInvestment_Old setTotalInvestedYears(Integer totalInvestedYears) {
        this.totalInvestedYears = totalInvestedYears;
        return this;
    }

    public YearlyInvestment_Old setRoi(Double roi) {
        this.roi = roi;
        return this;
    }

    public YearlyInvestment_Old setCompoundingFreq(Integer compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
        return this;
    }

    public YearlyInvestment_Old setInvestedTime(Integer investedTime) {
        this.investedTime = investedTime;
        return this;
    }

    public YearlyInvestment_Old setRoiMonthly(Double roiMonthly) {
        this.roiMonthly = roiMonthly;
        return this;
    }

    public YearlyInvestment_Old setTotalCompoundingTime(Integer totalCompoundingTime) {
        this.totalCompoundingTime = totalCompoundingTime;
        return this;
    }

    public YearlyInvestment_Old setTotalSipInvestmentThisYear(BigDecimal totalSipInvestmentThisYear) {
        this.totalSipInvestmentThisYear = totalSipInvestmentThisYear;
        return this;
    }

    public YearlyInvestment_Old setSipAmountThisYear(BigDecimal sipAmountThisYear) {
        this.sipAmountThisYear = sipAmountThisYear;
        return this;
    }

    public YearlyInvestment_Old setDirectLumpSumInvestment(Integer directLumpSumInvestment) {
        this.directLumpSumInvestment = directLumpSumInvestment;
        return this;
    }

    public YearlyInvestment_Old setMonthOfLumpSumInvestment(List<Integer> monthOfLumpSumInvestment) {
        this.monthOfLumpSumInvestment = monthOfLumpSumInvestment;
        return this;
    }

    public YearlyInvestment_Old setDirectLumpSumAmount(BigDecimal directLumpSumAmount) {
        this.directLumpSumAmount = directLumpSumAmount;
        return this;
    }

    public YearlyInvestment_Old setLastYearLumpSumInvestment(BigDecimal lastYearLumpSumInvestment) {
        this.lastYearLumpSumInvestment = lastYearLumpSumInvestment;
        return this;
    }

    public YearlyInvestment_Old setTotalCompoundingTimeForLastYearLumpSumInvestment(Integer totalCompoundingTimeForLastYearLumpSumInvestment) {
        this.totalCompoundingTimeForLastYearLumpSumInvestment = totalCompoundingTimeForLastYearLumpSumInvestment;
        return this;
    }

    public YearlyInvestment_Old setLastYearLumpSumAmount(BigDecimal lastYearLumpSumAmount) {
        this.lastYearLumpSumAmount = lastYearLumpSumAmount;
        return this;
    }

    public YearlyInvestment_Old setOverAllInvested(BigDecimal overAllInvested) {
        this.overAllInvested = overAllInvested;
        return this;
    }

    public YearlyInvestment_Old setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
        return this;
    }

    public YearlyInvestment_Old setInterestReceived(BigDecimal interestReceived) {
        this.interestReceived = interestReceived;
        return this;
    }

    /*
        netWorth - overAllInvested
         */
    BigDecimal interestReceived;

}
