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
            case "Valle d'Aosta" -> "Aosta";
            case "Bolzano" -> "Bolzano";
            case "Verbano Cusio Ossola" -> "Verbania";
            case "Reggio nell'Emilia" -> "Reggio Emilia";
            case "Pesaro e Urbino" -> "Pesaro Urbino";
            case "Monza e della Brianza" -> "Monza Brianza";
            case "Forlì Cesena" -> "Forli Cesena";
            // case "Sud Sardegna" -> "Sardegna";
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

                String code = record[0].trim();
                String name = record[1].trim();
                String region = record[2].trim();

                String cleanedName = cleanAndNormalize(name);

                Province savedProvince = provinceService.saveProvinceFromCsv(cleanedName, code, region);

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

                String municipalityName = record[2];
                String provinceName = record[3];

                String cleanedMunicipalityName = cleanAndNormalize(municipalityName);
                String cleanedProvinceName = cleanAndNormalize(provinceName);
                String translatedProvinceName = translateProvinceName(cleanedProvinceName);

                Province registeredProvince = provinceMap.get(translatedProvinceName);

                if (registeredProvince != null) {
                    municipalityService.saveMunicipalityFromCsv(
                            cleanedMunicipalityName,
                            record[0].trim(),
                            record[1].trim(),
                            translatedProvinceName,
                            registeredProvince
                    );
                } else {
                    System.out.println("--- Province not found ---");
                    System.out.println("Municipality: " + municipalityName);
                    System.out.println("Province: '" + translatedProvinceName + "'");
                    System.out.println("-----------------------------------------------------");
                }

            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
