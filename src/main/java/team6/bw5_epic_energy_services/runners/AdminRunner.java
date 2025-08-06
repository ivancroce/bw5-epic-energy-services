package team6.bw5_epic_energy_services.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import team6.bw5_epic_energy_services.entities.Role;
import team6.bw5_epic_energy_services.entities.User;
import team6.bw5_epic_energy_services.payloads.RoleDTO;
import team6.bw5_epic_energy_services.payloads.UserRegistrationDTO;
import team6.bw5_epic_energy_services.services.RoleService;
import team6.bw5_epic_energy_services.services.UserService;

import java.util.List;

@Component
public class AdminRunner implements ApplicationRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1. Controlla che esista il ruolo ADMIN, altrimenti crealo

        Role adminRole = roleService.findByName("ADMIN")
                .orElseGet(() -> roleService.save(new RoleDTO("ADMIN")));

        Role userRole = roleService.findByName("USER")
                .orElseGet(() -> roleService.save(new RoleDTO("USER")));


        // 2. Lista di utenti da creare (esempio username e email)
        List<UserRegistrationDTO> usersToCreate = List.of(
                new UserRegistrationDTO("admin1", "admin1@example.com", "PROVAPROVA", "Franco", "Conetto"),
                new UserRegistrationDTO("admin2", "admin2@example.com", "PROVAPROVA", "Pata", "Snello"),
                new UserRegistrationDTO("admin3", "admin3@example.com", "PROVAPROVA", "Alessio", "Provolone"),
                new UserRegistrationDTO("admin4", "admin4@example.com", "PROVAPROVA", "Merlo", "Merlot"));

        for (UserRegistrationDTO ud : usersToCreate) {
            User existingUser = userService.tryFindByEmail(ud.email());
            if (existingUser == null) {
                UserRegistrationDTO newUserDto = new UserRegistrationDTO(
                        ud.username(),
                        ud.email(),
                        ud.password(),
                        ud.name(),
                        ud.surname()
                );

                userService.save(newUserDto);
                System.out.println("Creato utente: " + ud.username());
            } else {
                System.out.println("Utente già esistente: " + ud.username());
            }
        }

    }


}


