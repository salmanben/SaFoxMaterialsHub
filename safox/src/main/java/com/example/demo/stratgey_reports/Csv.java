package com.example.demo.stratgey_reports;

import com.example.demo.model.Transaction;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.opencsv.CSVWriter;

@Component
public class Csv implements ReportGenerator {

    @Override
    public ResponseEntity<byte[]> generateReport(List<Transaction> transactions, String name) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(writer);

        String[] header = { "ID", "Transaction Id", "Client Id", "Montant", "Method", "Date" };
        csvWriter.writeNext(header);

        for (Transaction transaction : transactions) {
            String[] row = { String.valueOf(transaction.getId()), String.valueOf(transaction.getTransaction_id()),
                    String.valueOf(transaction.getClient().getId()),
                    String.valueOf(transaction.getMontant()), transaction.getMethod().toString(),
                    String.valueOf(transaction.getCreatedAt()) };
            csvWriter.writeNext(row);
        }

        csvWriter.flush();
        writer.flush();
        byte[] reportBytes = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", name + ".csv");
        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);
    }
}
