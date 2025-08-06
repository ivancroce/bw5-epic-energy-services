package team6.bw5_epic_energy_services.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Province;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewProvinceDTO;
import team6.bw5_epic_energy_services.repositories.ProvinceRepository;

import java.util.UUID;

@Service
@Slf4j
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    //----------------------------FIND ALL-----------------------------------------------------------
    public Page<Province> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.provinceRepository.findAll(pageable);
    }

    public Province findProvinceById(UUID provinceId) {
        return provinceRepository.findById(provinceId)
                .orElseThrow(() -> new NotFoundException("Province not found"));
    }

    //------------------------------SAVE-----------------------------------------------
    public Province save(NewProvinceDTO payload) {
        this.provinceRepository.findByName(payload.name()).ifPresent(province -> {
            throw new BadRequestException("Province " + province.getName() + " already exists in our system");
        });
        Province newProvince = new Province(payload.name(), payload.code(), payload.region());
        Province savedProvince = this.provinceRepository.save(newProvince);

        log.info("Province " + savedProvince.getName() + " has been successfully saved");

        return savedProvince;
    }

}
