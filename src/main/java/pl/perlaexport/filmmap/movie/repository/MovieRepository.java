package pl.perlaexport.filmmap.movie.repository;

import org.springframework.data.repository.CrudRepository;
import pl.perlaexport.filmmap.movie.model.MovieEntity;

public interface MovieRepository extends CrudRepository<MovieEntity,String> {
}
