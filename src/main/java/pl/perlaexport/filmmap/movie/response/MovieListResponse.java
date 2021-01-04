package pl.perlaexport.filmmap.movie.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.perlaexport.filmmap.movie.model.MovieEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieListResponse {
    List<MovieEntity> movies;
    Integer currentPage;
    Integer amountOfPages;
}
