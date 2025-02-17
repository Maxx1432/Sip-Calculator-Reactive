package com.sip_calculator.Sip_Calculator.Mappers;

import com.sip_calculator.Sip_Calculator.CsvEntities.YearlyInvestmentCsv;
import com.sip_calculator.Sip_Calculator.Dto.YearlyInvestment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface YearlyInvestmentMapper {

    @Mapping(target = "directLumpSumInvestment", source = "directLumpSumInvestment", defaultValue = "0")
    @Mapping(target = "directLumpSumAmount", source = "directLumpSumAmount", defaultValue = "0.00")
    @Mapping(target = "steppedUpSipValue", source = "steppedUpSipValue", defaultValue = "0.00")
    @Mapping(target = "stepSipPercentage", source = "stepSipPercentage", defaultValue = "0")
    @Mapping(target = "totalSipInvestmentThisYear", source = "totalSipInvestmentThisYear", defaultValue = "0.00")
    @Mapping(target = "sipAmountThisYear", source = "sipAmountThisYear", defaultValue = "0.00")
    @Mapping(target = "lastYearLumpSumInvestment", source = "lastYearLumpSumInvestment", defaultValue = "0.00")
    @Mapping(target = "overAllInvested", source = "overAllInvested", defaultValue = "0.00")
    @Mapping(target = "netWorth", source = "netWorth", defaultValue = "0.00")
    @Mapping(target = "interestReceived", source = "interestReceived", defaultValue = "0.00")
    YearlyInvestmentCsv sourceToDestination(YearlyInvestment source);

    List<YearlyInvestmentCsv> sourceToDestination(List<YearlyInvestment> source);
}
