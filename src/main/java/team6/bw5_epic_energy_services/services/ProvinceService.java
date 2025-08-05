package team6.bw5_epic_energy_services.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Province;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.payloads.NewProvinceDTO;
import team6.bw5_epic_energy_services.repositories.ProvinceRepository;

@Service
@Slf4j
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    public Province saveProvinceFromPayload(NewProvinceDTO payload) {
        if (provinceRepository.existsByName(payload.name())) {
            throw new BadRequestException("Province with name " + payload.name() + " already exists.");
        }
        Province province = new Province(payload.code(), payload.name(), payload.region());
        return provinceRepository.save(province);
    }

    public Province saveProvinceFromCsv(String rawName, String code, String region) {

        String cleanedName = cleanName(rawName);

        if (!provinceRepository.existsByName(cleanedName)) {
            Province newProvince = new Province(cleanedName, code, region);
            return provinceRepository.save(newProvince);
        } else {
            return provinceRepository.findByName(cleanedName).orElseThrow(() -> new BadRequestException("Error data."));
        }
    }

    private String cleanName(String rawName) {
        if (rawName == null) {
            return null;
        }
        return rawName
                .replace("-", " ")
                .split("/")[0]
                .trim();
    }
}
