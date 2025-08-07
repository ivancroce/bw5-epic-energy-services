package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team6.bw5_epic_energy_services.payloads.UserLoginDTO;
import team6.bw5_epic_energy_services.payloads.UserLoginRespDTO;
import team6.bw5_epic_energy_services.services.AuthorizationService;
import team6.bw5_epic_energy_services.services.UserService;


@RestController
@RequestMapping("/public")
public class AuthorizationController {
    @Autowired
    public UserService userService;

    @Autowired
    public AuthorizationService authorizationsService;

    //LOGIN
    @PostMapping("/login")
    public UserLoginRespDTO login(@RequestBody UserLoginDTO payload) {
        String extractedToken = authorizationsService.checkEmailBeforeLogin(payload);
        return new UserLoginRespDTO(extractedToken);
    }
}
