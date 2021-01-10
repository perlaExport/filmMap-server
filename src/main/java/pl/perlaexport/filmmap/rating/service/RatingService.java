package pl.perlaexport.filmmap.rating.service;

import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.dto.ReviewDto;
import pl.perlaexport.filmmap.user.model.UserEntity;

public interface RatingService {
    MovieResponse rateMovie(String movieId, Integer rating, UserEntity user);

    MovieResponse deleteRating(String movieId, UserEntity user);

    MovieResponse reviewMovie(ReviewDto review, String movieId, UserEntity user);

    MovieResponse deleteReview(String movieId, UserEntity user);
}
