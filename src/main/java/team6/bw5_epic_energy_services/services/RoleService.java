package team6.bw5_epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import team6.bw5_epic_energy_services.entities.Role;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.RoleDTO;
import team6.bw5_epic_energy_services.repositories.RoleRepository;

import java.util.UUID;

public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role save(RoleDTO body) {
        roleRepository.findByName(body.name()).ifPresent(role -> {
            throw new BadRequestException("The role" + role.getName() + " exists!");
        });
        Role newRole = new Role(body.name());
        return roleRepository.save(newRole);
    }

    public Page<Role> findAll(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return roleRepository.findAll(pageable);
    }

    public Role findById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role with id " + id + " not found!"));
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Role with name " + name + " not found!"));
    }

    public Role findByIdAndUpdate(UUID id, RoleDTO body) {
        Role found = this.findById(id);

        if (!found.getName().equals(body.name())) {
            roleRepository.findByName(body.name()).ifPresent(role -> {
                throw new BadRequestException("The role " + role.getName() + " exists");
            });
        }

        found.setName(body.name());
        return roleRepository.save(found);
    }

    public void findByIdAndDelete(UUID id) {
        Role found = this.findById(id);
        roleRepository.delete(found);
    }

}
