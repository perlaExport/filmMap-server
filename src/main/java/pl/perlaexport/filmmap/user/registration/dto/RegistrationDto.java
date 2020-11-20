package pl.perlaexport.filmmap.user.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @Size(min=5)
    private String password;
    @NotNull
    @NotBlank
    @Size(min=5)
    private String matchingPassword;
}
