package team6.bw5_epic_energy_services;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
public class MyRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws CsvValidationException, IOException {


        try (InputStream is = getClass().getResourceAsStream("/comuni-italiani.csv");
             InputStreamReader isr = new InputStreamReader(is);
             CSVReader csvReader = new CSVReaderBuilder(isr)
                     .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                     .build()) {


            if (is == null) {
                throw new IOException("File CSV non trovato nelle risorse");
            }

            String[] lines;

            while ((lines = csvReader.readNext()) != null) {
                for (int i = 0; i < lines.length; i++) {
                    System.out.println("Column " + i + ": " + lines[i]);
                }
            }

        }
    }
}




