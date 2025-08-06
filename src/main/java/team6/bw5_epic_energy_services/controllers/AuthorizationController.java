package team6.bw5_epic_energy_services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team6.bw5_epic_energy_services.services.AuthorizationService;
import team6.bw5_epic_energy_services.services.UserService;


@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    public UserService userService;

    @Autowired
    public AuthorizationService authorizationsService;

    //LOGIN
//    @PostMapping("/login")
//    public UserLoginResponseDTO login(@RequestBody UserLoginDTO payload) {
//        String extractedToken = authorizationsService.checkEmailBeforeLogin(payload);
//        return new UserLoginResponseDTO(extractedToken);
//    }
}
