package pl.perlaexport.filmmap.user.password.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.perlaexport.filmmap.user.password.dto.ResetPasswordDto;
import pl.perlaexport.filmmap.user.password.service.PasswordService;

@RestController
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/password/request")
    public void changePasswordRequest(@RequestParam String email){
        passwordService.changePasswordRequest(email);
    }

    @PutMapping("/password/change")
    public void changePassword(@RequestBody ResetPasswordDto dto,
                                     @RequestParam Long id, @RequestParam String token){
        passwordService.changePassword(id,token,dto);
    }
    @PutMapping("/password/resend_token")
    public void resendVerificationToken(@RequestParam String email){
        passwordService.resendChangePasswordToken(email);
    }
}
