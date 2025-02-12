package com.sip_calculator.Sip_Calculator.Mappers

import com.sip_calculator.Sip_Calculator.CsvEntities.YearlyInvestmentCsv
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
interface YearlyInvestmentMapper {
    @Mapping(target = "directLumpSumInvestment", source = "source.directLumpSumInvestment", defaultValue = "0")
    @Mapping(target = "directLumpSumAmount", source = "source.directLumpSumAmount", defaultValue = "0.00")
    @Mapping(target = "steppedUpSipValue", source = "source.steppedUpSipValue", defaultValue = "0.00")
    @Mapping(target = "stepSipPercentage", source = "source.stepSipPercentage", defaultValue = "0")
    @Mapping(target = "totalSipInvestmentThisYear", source = "source.totalSipInvestmentThisYear", defaultValue = "0.00")
    @Mapping(target = "sipAmountThisYear", source = "source.sipAmountThisYear", defaultValue = "0.00")
    @Mapping(target = "lastYearLumpSumInvestment", source = "source.lastYearLumpSumInvestment", defaultValue = "0.00")
    @Mapping(target = "overAllInvested", source = "source.overAllInvested", defaultValue = "0.00")
    @Mapping(target = "netWorth", source = "source.netWorth", defaultValue = "0.00")
    @Mapping(target = "interestReceived", source = "source.interestReceived", defaultValue = "0.00")
    fun sourceToDestination(source: YearlyInvestment): YearlyInvestmentCsv

    fun sourceToDestination(source: List<YearlyInvestment>): List<YearlyInvestmentCsv>
}