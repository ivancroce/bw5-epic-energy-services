package team6.bw5_epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.Role;
import team6.bw5_epic_energy_services.entities.User;
import team6.bw5_epic_energy_services.exceptions.BadRequestException;
import team6.bw5_epic_energy_services.exceptions.NotFoundException;
import team6.bw5_epic_energy_services.payloads.UserRegistrationDTO;
import team6.bw5_epic_energy_services.payloads.UserRespDTO;
import team6.bw5_epic_energy_services.repositories.RoleRepository;
import team6.bw5_epic_energy_services.repositories.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Page<User> findAllUsers(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return userRepository.findAll(pageable);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public UserRespDTO save(UserRegistrationDTO body) {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("The Email " + user.getEmail() + " it is already in use!");
        });
        userRepository.findByUsername(body.username()).ifPresent(user -> {
            throw new BadRequestException("The username " + user.getUsername() + " it is already in use!");
        });

        //CREAZIONE USER
        User newUser = new User(
                body.username(),
                body.email(),
                //body.password()),
                body.name(),
                body.surname(),
                //"https://avatars.com/" + body.name() + "+" + body.surname()
        );

        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("The role 'USER' was not found in the database."));
        newUser.setRoleList(Collections.singletonList(defaultRole));

        User savedUser = userRepository.save(newUser);
        return new UserRespDTO(savedUser.getId());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with email " + email + " not found!"));
    }

}
