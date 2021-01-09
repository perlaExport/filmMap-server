package pl.perlaexport.filmmap.user.login.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseLoginFailure {
    public String email = "Bad email";
    public String password = "Bad password";

    public ResponseLoginFailure(String email){
        this.email = email;
    }
}
