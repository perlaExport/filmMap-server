package pl.perlaexport.filmmap.user.registration.service;

import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.registration.dto.RegistrationDto;

public interface RegistrationService {
    UserEntity register(RegistrationDto registrationDto);
}
