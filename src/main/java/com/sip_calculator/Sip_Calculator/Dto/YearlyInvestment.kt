package com.sip_calculator.Sip_Calculator.Dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class YearlyInvestment {

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

    public YearlyInvestment setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
        return this;
    }

    public YearlyInvestment setSteppedUpSipValue(BigDecimal steppedUpSipValue) {
        this.steppedUpSipValue = steppedUpSipValue;
        return this;
    }

    public YearlyInvestment setStepSipPercentage(Integer stepSipPercentage) {
        this.stepSipPercentage = stepSipPercentage;
        return this;
    }

    public YearlyInvestment setTotalInvestedYears(Integer totalInvestedYears) {
        this.totalInvestedYears = totalInvestedYears;
        return this;
    }

    public YearlyInvestment setRoi(Double roi) {
        this.roi = roi;
        return this;
    }

    public YearlyInvestment setCompoundingFreq(Integer compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
        return this;
    }

    public YearlyInvestment setInvestedTime(Integer investedTime) {
        this.investedTime = investedTime;
        return this;
    }

    public YearlyInvestment setRoiMonthly(Double roiMonthly) {
        this.roiMonthly = roiMonthly;
        return this;
    }

    public YearlyInvestment setTotalCompoundingTime(Integer totalCompoundingTime) {
        this.totalCompoundingTime = totalCompoundingTime;
        return this;
    }

    public YearlyInvestment setTotalSipInvestmentThisYear(BigDecimal totalSipInvestmentThisYear) {
        this.totalSipInvestmentThisYear = totalSipInvestmentThisYear;
        return this;
    }

    public YearlyInvestment setSipAmountThisYear(BigDecimal sipAmountThisYear) {
        this.sipAmountThisYear = sipAmountThisYear;
        return this;
    }

    public YearlyInvestment setDirectLumpSumInvestment(Integer directLumpSumInvestment) {
        this.directLumpSumInvestment = directLumpSumInvestment;
        return this;
    }

    public YearlyInvestment setMonthOfLumpSumInvestment(List<Integer> monthOfLumpSumInvestment) {
        this.monthOfLumpSumInvestment = monthOfLumpSumInvestment;
        return this;
    }

    public YearlyInvestment setDirectLumpSumAmount(BigDecimal directLumpSumAmount) {
        this.directLumpSumAmount = directLumpSumAmount;
        return this;
    }

    public YearlyInvestment setLastYearLumpSumInvestment(BigDecimal lastYearLumpSumInvestment) {
        this.lastYearLumpSumInvestment = lastYearLumpSumInvestment;
        return this;
    }

    public YearlyInvestment setTotalCompoundingTimeForLastYearLumpSumInvestment(Integer totalCompoundingTimeForLastYearLumpSumInvestment) {
        this.totalCompoundingTimeForLastYearLumpSumInvestment = totalCompoundingTimeForLastYearLumpSumInvestment;
        return this;
    }

    public YearlyInvestment setLastYearLumpSumAmount(BigDecimal lastYearLumpSumAmount) {
        this.lastYearLumpSumAmount = lastYearLumpSumAmount;
        return this;
    }

    public YearlyInvestment setOverAllInvested(BigDecimal overAllInvested) {
        this.overAllInvested = overAllInvested;
        return this;
    }

    public YearlyInvestment setNetWorth(BigDecimal netWorth) {
        this.netWorth = netWorth;
        return this;
    }

    public YearlyInvestment setInterestReceived(BigDecimal interestReceived) {
        this.interestReceived = interestReceived;
        return this;
    }

    /*
        netWorth - overAllInvested
         */
    BigDecimal interestReceived;

}
