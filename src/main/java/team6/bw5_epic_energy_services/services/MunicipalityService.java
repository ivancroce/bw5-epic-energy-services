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


    public void saveMunicipalityFromCsv(String name, String progressiveMunicipalityCode, Province province) {

        if (!municipalityRepository.existsByNameAndProvince(name, province)) {
            Municipality newMunicipality = new Municipality(name, progressiveMunicipalityCode, province);
            municipalityRepository.save(newMunicipality);
        }
    }
}
