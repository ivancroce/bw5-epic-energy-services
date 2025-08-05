package team6.bw5_epic_energy_services.runners;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team6.bw5_epic_energy_services.entities.Province;
import team6.bw5_epic_energy_services.repositories.ProvinceRepository;
import team6.bw5_epic_energy_services.services.MunicipalityService;
import team6.bw5_epic_energy_services.services.ProvinceService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private MunicipalityService municipalityService;

    private Map<String, Province> provinceMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        this.importProvinces("/province-italiane.csv");

        this.importMunicipalities("/comuni-italiani.csv");

    }

    private void importProvinces(String filePath) {
        System.out.println("--- Import Provinces: " + filePath + " ---");
        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr)
                        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                        .withSkipLines(1)
                        .build()
        ) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String code = record[0];
                String name = record[1];
                String region = record[2];

                Province savedProvince = provinceService.saveProvinceFromCsv(name, code, region);

                provinceMap.put(savedProvince.getName(), savedProvince);
            }
        } catch (IOException | CsvException e) {
            System.out.println("Error while reading province file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void importMunicipalities(String filePath) {
        System.out.println("--- Import Municipalities: " + filePath + " ---");

        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr)
                        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                        .withSkipLines(1)
                        .build()


        ) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {

                String provinceCode = record[0];
                String progressiveMunicipalityCode = record[1];
                String municipalityName = record[2];
                String provinceName = record[3];

                String provinceKeyForMap = provinceName.replace("-", " ").split("/")[0].trim();

                Province registeredProvince = provinceMap.get(provinceKeyForMap);

                municipalityService.saveMunicipalityFromCsv(municipalityName, provinceCode, progressiveMunicipalityCode, provinceName, registeredProvince);

            }
        } catch (IOException | CsvException e) {
            System.out.println("Error while reading municipality file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
