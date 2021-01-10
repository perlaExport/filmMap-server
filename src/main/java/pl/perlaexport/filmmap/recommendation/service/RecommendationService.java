package pl.perlaexport.filmmap.recommendation.service;

import pl.perlaexport.filmmap.user.model.UserEntity;

public interface RecommendationService {
    Integer getRecommendation(String movieId, UserEntity user);
}
