package com.sip_calculator.Sip_Calculator.Util;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class CustomMappingStrategyForYearlyInvestments<T> extends ColumnPositionMappingStrategy<T> {
    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
        String[] header = super.generateHeader(bean);
        return new String[]{"YEAR", "MONTHLY INVESTMENT", "STEP UP PERCENTAGE", "TOTAL SIP INVESTED IN THIS YEAR", "SIP YEAR END VALUE", "LAST YEAR'S LUMPSUM", "DIRECT LUMPSUM INVESTMENT", "DIRECT LUMP SUM AMOUNT", "OVERALL INVESTED", "NET WORTH", "INTEREST RECEIVED"};
    }
}
