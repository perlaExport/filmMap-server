package pl.perlaexport.filmmap.rating.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.perlaexport.filmmap.user.model.UserEntity;

@Data
@AllArgsConstructor
public class ReviewResponse {
    UserEntity user;
    Integer userRate;
    String userReview;
}
