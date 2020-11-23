package pl.perlaexport.filmmap.rating.service;

import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.List;

public interface RatingService {
    List<RatingEntity> getMovieRatings(String movie_id);
    List<RatingEntity> getUserRatings(Long user_id);
    List<RatingEntity> getUserRatings(UserEntity user);
}
