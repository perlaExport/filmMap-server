package pl.perlaexport.filmmap.rating.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.perlaexport.filmmap.movie.model.MovieEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewListResponse {
    List<ReviewResponse> reviews;
    Integer currentPage;
    Integer amountOfPages;
}
