package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Province;
import team6.bw5_epic_energy_services.services.ProvinceService;

import java.util.UUID;

@RestController
@RequestMapping("/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @GetMapping
    public Page<Province> getAllProvinces(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy) {
        return provinceService.findAll(page, size, sortBy);
    }

    @GetMapping("/{provinceId}")
    public Province getProvinceById(@PathVariable UUID provinceId) {
        return provinceService.findProvinceById(provinceId);
    }
}
