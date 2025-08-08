package team6.bw5_epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team6.bw5_epic_energy_services.entities.User;
import team6.bw5_epic_energy_services.exceptions.UnauthorizedException;
import team6.bw5_epic_energy_services.payloads.UserLoginDTO;
import team6.bw5_epic_energy_services.tools.JWTTools;

@Service
public class AuthorizationService {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;

    public String checkEmailBeforeLogin(UserLoginDTO payload) {
        User found = userService.findByEmail(payload.email());
        if (bCrypt.matches(payload.password(), found.getPassword())) {
            String extractedToken = jwtTools.createToken(found);
            return extractedToken;
        } else {
            throw new UnauthorizedException("Unauthorized - try again");
        }
    }
}
