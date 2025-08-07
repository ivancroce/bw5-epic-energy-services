package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Municipality;
import team6.bw5_epic_energy_services.services.MunicipalityService;

import java.util.UUID;

@RestController
@RequestMapping("/municipalities")
public class MunicipalityController {

    @Autowired
    private MunicipalityService municipalityService;


    @GetMapping
    public Page<Municipality> getAllMUnicipalities(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy) {
        return municipalityService.findAll(page, size, sortBy);
    }

    @GetMapping("/{municipalityId}")
    public Municipality getMunicipalityById(@PathVariable UUID municipalityId) {
        return municipalityService.findMunicipalityById(municipalityId);
    }
}
