package pl.perlaexport.filmmap.user.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;
import pl.perlaexport.filmmap.user.registration.service.RegistrationService;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public UserEntity registration(@Valid @RequestBody RegistrationDto registrationDto){
        return registrationService.register(registrationDto);
    }
}
