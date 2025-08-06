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

    private static final Map<String, String> nameTranslationMap = new HashMap<>();

    static {
        nameTranslationMap.put("Verbano Cusio Ossola", "Verbania");
        nameTranslationMap.put("Valle d'Aosta/Vallée d'Aoste", "Aosta");
        nameTranslationMap.put("Reggio nell'Emilia", "Reggio Emilia");
        nameTranslationMap.put("Sud Sardegna", "Cagliari");
        nameTranslationMap.put("Bolzano/Bozen", "Bolzano");
        nameTranslationMap.put("Pesaro e Urbino", "Pesaro Urbino");
        nameTranslationMap.put("Monza e della Brianza", "Monza Brianza");
        nameTranslationMap.put("Forlì Cesena", "Forli Cesena");

    }

    private final Map<String, Province> createdProvincesMap = new HashMap<>();
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private MunicipalityService municipalityService;
    private Map<String, String[]> provinceDetailsMap = new HashMap<>();

    @Override
    public void run(String... args) throws Exception {
        if (provinceRepository.count() > 0) {
            return;
        }
        this.prepareProvinceDetails("/province-italiane.csv");

        this.importDataFromMunicipalities("/comuni-italiani.csv");
        
    }

    private String cleanAndNormalize(String name) {
        if (name == null) return "";
        return name.replace("-", " ").trim();
    }

    private void prepareProvinceDetails(String filePath) {
        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build()
        ) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String code = record[0].trim();
                String rawName = record[1].trim();
                String region = record[2].trim();

                String cleanedName = cleanAndNormalize(rawName);

                provinceDetailsMap.put(cleanedName, new String[]{code, region});
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private void importDataFromMunicipalities(String filePath) {
        try (
                InputStream is = getClass().getResourceAsStream(filePath);
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                CSVReader csvReader = new CSVReaderBuilder(isr).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).withSkipLines(1).build()
        ) {
            String[] record;
            while ((record = csvReader.readNext()) != null) {
                String progressiveMunicipalityCode = record[1].trim();
                String name = record[2].trim();
                String officialProvinceName = record[3].trim();

                String officialKey = cleanAndNormalize(officialProvinceName);
                Province province = createdProvincesMap.get(officialKey);

                if (province == null) {
                    String defaultName = nameTranslationMap.getOrDefault(officialKey, officialKey);
                    String[] details = provinceDetailsMap.get(defaultName);

                    if (details == null) {
                        System.out.println("Error: can't find the province: " + defaultName + "(from: " + officialProvinceName + ")");
                        continue; // to go to the next municipality
                    }
                    String code = details[0];
                    String region = details[1];

                    province = new Province(officialProvinceName, code, region);
                    provinceRepository.save(province);

                    createdProvincesMap.put(officialKey, province);
                }

                municipalityService.saveMunicipalityFromCsv(name, progressiveMunicipalityCode, province);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
