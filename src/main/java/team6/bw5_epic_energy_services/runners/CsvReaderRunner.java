package team6.bw5_epic_energy_services.runners;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CsvReaderRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        // this.importMunicipalities();

        // this.importProvince();
    }

    public void importMunicipalities() {
        String filePath = "/comuni-italiani.csv";

        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr)
                        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                        .build()
        ) {
            String[] record;
            int rowNumber = 0;
            while ((record = csvReader.readNext()) != null) {
                rowNumber++;

                String provinceCode = record[0];
                String progressiveMunicipality = record[1];
                String municipalityName = record[2];
                String provinceName = record[2];

                System.out.printf("Municipality (Row %d): Province Code=%s, Progressive Municipality Code=%s, Municipality Name=%s%n, Province Name=%s%n",
                        rowNumber, provinceCode, progressiveMunicipality, municipalityName, provinceName);
            }
        } catch (IOException | CsvException e) {
            System.out.println("Error while reading the file " + filePath);
            e.printStackTrace();
        }
        System.out.println("--- End file: " + filePath + " ---");
    }


    public void importProvince() {
        String filePath = "/province-italiane.csv";

        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr)
                        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                        .build()
        ) {
            String[] record;
            int rowNumber = 0;
            while ((record = csvReader.readNext()) != null) {
                rowNumber++;

                String code = record[0];
                String provinceName = record[1];
                String region = record[2];

                System.out.printf("Province (Row %d): Sigla=%s, Name=%s, Region=%s%n",
                        rowNumber, code, provinceName, region);
            }
        } catch (IOException | CsvException e) {
            System.out.println("Error while reading the file " + filePath);
            e.printStackTrace();
        }
        System.out.println("--- End file: " + filePath + " ---");
    }
}




