package pl.perlaexport.filmmap.user.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.perlaexport.filmmap.user.login.dto.LoginDto;
import pl.perlaexport.filmmap.user.login.response.ResponseLogin;
import pl.perlaexport.filmmap.user.login.service.LoginService;

import javax.validation.Valid;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseLogin login(@Valid @RequestBody LoginDto loginDto){
        return loginService.login(loginDto);
    }

}
