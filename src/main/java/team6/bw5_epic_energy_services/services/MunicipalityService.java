package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Municipality;
import team6.bw5_epic_energy_services.entities.Province;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.payloads.NewMunicipalityDTO;
import team6.bw5_epic_energy_services.repositories.MunicipalityRepository;

@Service
@Slf4j
public class MunicipalityService {
    @Autowired
    private MunicipalityRepository municipalityRepository;

    //----------------------------FIND ALL-----------------------------------------------------------
    public Page<Municipality> findAll(int pageNumber, int pageSize, String sortBy) {
        if (pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        return this.municipalityRepository.findAll(pageable);
    }

    //------------------------------SAVE-----------------------------------------------
    public Municipality save(NewMunicipalityDTO payload) {
        this.municipalityRepository.findByName(payload.name()).ifPresent(municipality -> {
            throw new BadRequestException("Il nome " + municipality.getName() + "è già in uso!");
        });
        Municipality newMunicipality = new Municipality(payload.name(), payload.provinceCode(), payload.progressiveMunicipalityCode(), payload.provinceName(), payload.province());
        Municipality savedMunicipality = this.municipalityRepository.save(newMunicipality);

        log.info("Il comune con id:  " + savedMunicipality.getId() + "è stato salvato con successo!");

        return savedMunicipality;
    }

    public void saveMunicipalityFromCsv(String cleanedName, String provinceCode, String progressiveMunicipalityCode, String provinceName, Province province) {

        if (!municipalityRepository.existsByNameAndProvince(cleanedName, province)) {
            Municipality newMunicipality = new Municipality(cleanedName, provinceCode, progressiveMunicipalityCode, provinceName, province);
            municipalityRepository.save(newMunicipality);
        }
    }
}
