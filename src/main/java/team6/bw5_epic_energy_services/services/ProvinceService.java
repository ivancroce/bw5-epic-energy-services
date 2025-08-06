package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Province;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.repositories.ProvinceRepository;

@Service
@Slf4j
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;


    public Province saveProvinceFromCsv(Province province) {

        if (provinceRepository.existsByCode(province.getCode())) {
            throw new BadRequestException("Province with code '" + province.getCode() + "' already exists.");
        }
        return provinceRepository.save(province);
    }
}
