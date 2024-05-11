package com.example.demo.stratgey_reports;

import com.example.demo.model.Transaction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class Xlsx implements ReportGenerator {

    @Override
    public ResponseEntity<byte[]> generateReport(List<Transaction> transactions, String name) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                Workbook workbook = createWorkbook(transactions)) {
            workbook.write(outputStream);

            byte[] reportBytes = outputStream.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("filename", name + ".xlsx");
            return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private Workbook createWorkbook(List<Transaction> transactions) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transactions");
        Row headerRow = sheet.createRow(0);
        String[] headers = { "ID", "Transaction ID", "Client Id", "Amount", "Method", "Date" };
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }
        int rowNum = 1;
        for (Transaction transaction : transactions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(transaction.getId());
            row.createCell(1).setCellValue(transaction.getTransaction_id());
            row.createCell(2).setCellValue(transaction.getClient().getId());
            row.createCell(3).setCellValue(transaction.getMontant());
            row.createCell(4).setCellValue(transaction.getMethod());
            row.createCell(5).setCellValue(transaction.getCreatedAt().toString());
        }
        return workbook;
    }
}
