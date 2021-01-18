package pl.perlaexport.filmmap.movie.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MovieListResponse {
    List<?> movies;
    Integer currentPage;
    Integer amountOfPages;
}
