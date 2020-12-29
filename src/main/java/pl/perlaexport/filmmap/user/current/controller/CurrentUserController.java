package pl.perlaexport.filmmap.user.current.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.perlaexport.filmmap.user.current.CurrentUser;
import pl.perlaexport.filmmap.user.model.UserEntity;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CurrentUserController {
    @GetMapping("/get_current_user")
    public UserEntity getCurrentUser(HttpServletRequest request){
        return CurrentUser.get(request);
    }
}
