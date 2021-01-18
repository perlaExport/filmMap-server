package pl.perlaexport.filmmap.recommendation.service;

import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.List;

public interface RecommendationService {
    Integer getRecommendation(String movieId, UserEntity user);
    List<MovieEntity> getTopRecommendations(UserEntity user, int size);
}
