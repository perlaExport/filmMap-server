package pl.perlaexport.filmmap.movie.service;

import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.List;

public interface MovieService {
    MovieResponse addMovie(MovieDto movieDto, UserEntity user);
    MovieResponse getMovie(String movieId, UserEntity user);
    MovieResponse rateMovie(String movieId, Integer rating, UserEntity user);
    MovieResponse deleteRating(String movieId, UserEntity user);
    MovieResponse getRandomMovie(UserEntity user);
}
