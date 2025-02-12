package com.sip_calculator.Sip_Calculator.CsvEntities

import com.opencsv.bean.CsvBindByPosition
import java.math.BigDecimal

data class YearlyInvestmentCsv(@CsvBindByPosition(position = 0)
                               var currentYear: Int? = null,

                               @CsvBindByPosition(position = 1)
                               var steppedUpSipValue: BigDecimal? = null,

                               @CsvBindByPosition(position = 2)
                               var stepSipPercentage: Int? = null,

                               @CsvBindByPosition(position = 3)
                               var totalSipInvestmentThisYear: BigDecimal? = null,

                               @CsvBindByPosition(position = 4)
                               var sipAmountThisYear: BigDecimal? = null,

                               @CsvBindByPosition(position = 5)
                               var lastYearLumpSumInvestment: BigDecimal? = null,

                               @CsvBindByPosition(position = 6)
                               var directLumpSumInvestment: Int? = null,

                               @CsvBindByPosition(position = 7)
                               var directLumpSumAmount: BigDecimal? = null,

                               @CsvBindByPosition(position = 8)
                               var overAllInvested: BigDecimal? = null,

                               @CsvBindByPosition(position = 9)
                               var netWorth: BigDecimal? = null,

                               @CsvBindByPosition(position = 10)
                               var interestReceived: BigDecimal? = null
)
