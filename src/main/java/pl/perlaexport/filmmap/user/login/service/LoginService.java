package pl.perlaexport.filmmap.user.login.service;

import pl.perlaexport.filmmap.user.login.dto.LoginDto;
import pl.perlaexport.filmmap.user.login.response.ResponseLogin;

public interface LoginService {
    ResponseLogin login(LoginDto loginDto);
}
