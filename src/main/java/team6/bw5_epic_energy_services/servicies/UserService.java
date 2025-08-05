package team6.bw5_epic_energy_services.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.repositories.RoleRepository;
import team6.bw5_epic_energy_services.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

}
