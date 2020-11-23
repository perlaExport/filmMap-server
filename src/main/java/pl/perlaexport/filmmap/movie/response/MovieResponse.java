package pl.perlaexport.filmmap.movie.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieResponse {
    String movieId;
    Double avgRate;
    Integer userRate;
}
