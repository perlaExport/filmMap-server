package pl.perlaexport.filmmap.rating.repository;

import org.springframework.data.repository.CrudRepository;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<RatingEntity, Long> {
    Optional<RatingEntity> findByMovieAndUser(MovieEntity movie, UserEntity user);
}
