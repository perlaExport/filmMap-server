package pl.perlaexport.filmmap.movie.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatedMovieResponse {
    MovieEntity movie;
    Integer userRate;
}
