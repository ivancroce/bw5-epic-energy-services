package team6.bw5_epic_energy_services.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Municipality;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.NewMunicipalityDTO;
import team6.bw5_epic_energy_services.repositories.MunicipalityRepository;

import java.util.UUID;

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

    public Municipality findMunicipalityById(UUID municipalityId) {
        return municipalityRepository.findById(municipalityId)
                .orElseThrow(() -> new NotFoundException("MUnicipality not found"));
    }

    //------------------------------SAVE-----------------------------------------------
    public Municipality save(NewMunicipalityDTO payload) {
        this.municipalityRepository.findByName(payload.name()).ifPresent(municipality -> {
            throw new BadRequestException("Municipality " + municipality.getName() + " already exists in our system");
        });
        Municipality newMunicipality = new Municipality(payload.name(), payload.provinceCode(), payload.progressiveMunicipalityCode(), payload.provinceName(), payload.province());
        Municipality savedMunicipality = this.municipalityRepository.save(newMunicipality);

        log.info("MUnicipality " + savedMunicipality.getName() + " has been successfully saved");

        return savedMunicipality;
    }
}
