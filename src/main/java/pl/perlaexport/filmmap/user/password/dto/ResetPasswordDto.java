package pl.perlaexport.filmmap.user.password.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String password;
    private String matchingPassword;
}
