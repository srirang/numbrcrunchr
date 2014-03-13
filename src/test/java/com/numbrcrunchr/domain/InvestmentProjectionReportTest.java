package com.numbrcrunchr.domain;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/domainApplicationContext.xml" })
public class InvestmentProjectionReportTest {

    @Autowired
    private FeasibilityAnalysisProjectionService projectionService;
    @Autowired
    private ProjectionParameters projectionParameters;

    @Test
    public void checkThatLoanBalanceReducesForPrincipalAndInterestProjection()
            throws JRException, IOException {
        long income = 120000;
        long ongoingCosts = 7000;
        long weeklyRent = 320;
        byte weeksRented = 50;
        long loanAmount = 427320;
        double interestRate = 8;
        double propertyManagementFee = 10;
        Property property = PropertyTest.createProperty(income, true,
                loanAmount, ongoingCosts, weeksRented, weeklyRent,
                interestRate, propertyManagementFee);
        property.setInterestOnlyPeriod(0);
        Projection projection = projectionService.runProjection(property, 20,
                projectionParameters);

        InvestmentProjectionReport investmentProjectionReport = new InvestmentProjectionReport(
                projection);
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("projection", investmentProjectionReport);
        JasperPrint print = JasperFillManager
                .fillReport(JasperCompileManager
                        .compileReport(new ClassPathResource(
                                "/investment-projection-report.jrxml")
                                .getInputStream()), parameters);
        JRExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                new FileOutputStream("investment-projection-report.pdf"));
        exporter.exportReport();
    }
}
