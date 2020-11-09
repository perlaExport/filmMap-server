package pl.perlaexport.filmmap.user.repository;

import org.springframework.data.repository.CrudRepository;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
}
