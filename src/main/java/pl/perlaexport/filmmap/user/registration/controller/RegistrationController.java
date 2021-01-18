package pl.perlaexport.filmmap.user.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;
import pl.perlaexport.filmmap.user.registration.service.RegistrationService;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public void registration(@Valid @RequestBody RegistrationDto registrationDto){
        registrationService.register(registrationDto);
    }

    @PutMapping("/register/confirm")
    public UserEntity confirmRegistration(@RequestParam Long id, @RequestParam String token){
        return registrationService.confirmRegistration(id,token);
    }
    @GetMapping("/register/resend_token")
    public void resendVerificationToken(@RequestParam String email){
        registrationService.resendVerificationToken(email);
    }
}
