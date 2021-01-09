package pl.perlaexport.filmmap.user.login.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.perlaexport.filmmap.user.model.UserEntity;

@AllArgsConstructor
@Data
public class ResponseLogin{
    private String token;
    private UserEntity user;
}
