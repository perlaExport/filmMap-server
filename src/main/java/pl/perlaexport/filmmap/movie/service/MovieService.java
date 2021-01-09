package pl.perlaexport.filmmap.movie.service;

import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.user.model.UserEntity;

public interface MovieService {
    MovieResponse addMovie(MovieDto movieDto);

    MovieResponse getMovie(String movieId, UserEntity user);

    MovieResponse rateMovie(String movieId, Integer rating, UserEntity user);

    MovieResponse deleteRating(String movieId, UserEntity user);

    MovieResponse getRandomMovie(UserEntity user);
}
