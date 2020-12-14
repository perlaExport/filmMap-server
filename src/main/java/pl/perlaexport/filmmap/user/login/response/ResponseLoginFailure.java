package pl.perlaexport.filmmap.user.login.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class ResponseLoginFailure extends ResponseLogin{
    public String email;
    public String password;

}
