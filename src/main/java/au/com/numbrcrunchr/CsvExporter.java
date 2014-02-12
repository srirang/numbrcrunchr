package au.com.numbrcrunchr;

import java.util.List;
import java.util.logging.Logger;

import org.primefaces.component.export.CSVExporter;

import au.com.numbrcrunchr.domain.FeasibilityAnalysisResult;

public class CsvExporter {
    private static final Logger LOGGER = Logger.getLogger(CSVExporter.class
            .getName());

    public static String exportToCsvString(
            List<FeasibilityAnalysisResult> feasibilityAnalysisResults) {
        String eol = System.getProperty("line.separator");
        StringBuffer buffer = new StringBuffer(
                "Year, Tenant Pays, You Pay, ATO Pays, Total Income, Interest, Ongoing Expenses, Total Expense, Cashflow Before Tax, Cashflow After Tax, Gross Yield (%), Nett Yield (%)")
                .append(eol);
        for (FeasibilityAnalysisResult feasibilityAnalysisResult : feasibilityAnalysisResults) {
            buffer.append(feasibilityAnalysisResult.getYear());
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getRentalIncome()));
            buffer.append(",");
            buffer.append(String.valueOf(-feasibilityAnalysisResult.getYouPay()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getTaxSavings()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getTotalIncome()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getInterest()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getOngoingExpenses()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getTotalExpense()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getGrossCashflow()));
            buffer.append(",");
            buffer.append(String.valueOf(feasibilityAnalysisResult
                    .getNettCashflow()));
            buffer.append(",");
            buffer.append(PercentageConverter.PERCENTAGE_FORAMT
                    .format(feasibilityAnalysisResult.getGrossYield()));
            buffer.append(",");
            buffer.append(PercentageConverter.PERCENTAGE_FORAMT
                    .format(feasibilityAnalysisResult.getNettYield()));

            buffer.append(eol);
        }
        LOGGER.info(buffer.toString());
        return buffer.toString();
    }
}
