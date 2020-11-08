package pl.perlaexport.filmmap.user.repository;

import org.springframework.data.repository.CrudRepository;
import pl.perlaexport.filmmap.user.model.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
}
