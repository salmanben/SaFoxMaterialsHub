package com.example.demo.stratgey_reports;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.example.demo.model.Transaction;
import java.util.List;


@Component
public class ReportGeneratorContext {

   
    private final Map<String, ReportGenerator> reportGeneratorMap;

   public ReportGeneratorContext(Map<String, ReportGenerator> reportGeneratorMap) {
        this.reportGeneratorMap = reportGeneratorMap;
    }
    

    public ReportGenerator getReportGenerator(String reportType) {
        return reportGeneratorMap.get(reportType);
    }

    public ResponseEntity<byte[]> executerGenerator(String reportType, List<Transaction> transactions, String name) throws Exception{
        ReportGenerator reportGenerator = getReportGenerator(reportType);
        if (reportGenerator != null) {
            return reportGenerator.generateReport(transactions, name);
        } else {
            throw new IllegalArgumentException("Invalid report type: " + reportType);
        }

    }
}
