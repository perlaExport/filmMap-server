package pl.perlaexport.filmmap.user.password.service;

import pl.perlaexport.filmmap.user.password.dto.ResetPasswordDto;

public interface PasswordService {
    void changePasswordRequest(String email);
    void changePassword(Long id, String token, ResetPasswordDto resetPasswordDto);
    void resendChangePasswordToken(String email);
}
