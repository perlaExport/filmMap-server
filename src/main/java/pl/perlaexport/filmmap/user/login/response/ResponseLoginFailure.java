package pl.perlaexport.filmmap.user.login.response;

import lombok.Data;

@Data
public class ResponseLoginFailure extends ResponseLogin{
    public String email = "bad login";
    public String password = "bad login";
}
