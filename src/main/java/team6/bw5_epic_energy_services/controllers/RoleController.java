package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
