package pl.perlaexport.filmmap.rating.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewListResponse {
    List<ReviewResponse> reviews;
    Integer currentPage;
    Integer amountOfPages;
}
