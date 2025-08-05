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

    private String cleanAndNormalize(String name) {
        if (name == null) return "";
        return name.replace("-", " ").split("/")[0].trim();
    }

    private String translateProvinceName(String cleanedName) {
        return switch (cleanedName) {
            case "verbano cusio ossola" -> "verbania";
            case "valle d'aosta" -> "aosta";
            case "bolzano" -> "bolzano";
            case "reggio nell'emilia" -> "reggio emilia";
            case "sud sardegna" -> "sardegna";
            case "pesaro e urbino" -> "pesaro urbino";
            case "monza e della brianza" -> "monza brianza";
            default -> cleanedName;
        };
    }

    private void importProvinces(String filePath) {
        System.out.println("--- Import Provinces: " + filePath + " ---");
        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build()
        ) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String nameFromCsv = record[1].trim();

                String cleanedName = cleanAndNormalize(nameFromCsv);

                Province savedProvince = provinceService.saveProvinceFromCsv(cleanedName, record[0].trim(), record[2].trim());

                provinceMap.put(cleanedName, savedProvince);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private void importMunicipalities(String filePath) {
        System.out.println("--- Import Municipalities: " + filePath + " ---");
        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build()
        ) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String municipalityName = record[2].trim();
                String provinceName = record[3].trim();


                String cleanedProvinceName = cleanAndNormalize(provinceName);
                String finalKeyForMap = translateProvinceName(cleanedProvinceName);
                Province registeredProvince = provinceMap.get(finalKeyForMap);

                if (registeredProvince != null) {

                    String cleanedMunicipalityName = municipalityName.replace("-", " ").trim();

                    municipalityService.saveMunicipalityFromCsv(
                            cleanedMunicipalityName,
                            record[0].trim(),
                            record[1].trim(),
                            provinceName,
                            registeredProvince
                    );
                } else {
                    System.out.println("--- Province not found ---");
                    System.out.println("Municipality: " + municipalityName);
                    System.out.println("Final key for map: '" + finalKeyForMap + "'");
                    System.out.println("-----------------------------------------------------");
                }

            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
