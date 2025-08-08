package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team6.bw5_epic_energy_services.entities.Role;
import team6.bw5_epic_energy_services.payloads.RoleDTO;
import team6.bw5_epic_energy_services.payloads.RoleRespDTO;
import team6.bw5_epic_energy_services.services.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("")
    public RoleRespDTO createRole(@RequestBody RoleDTO body) {
        Role newRole = this.roleService.save(body);
        return new RoleRespDTO(newRole.getId());
    }


    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Role> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return roleService.findAll(page, size, sortBy);
    }

}
