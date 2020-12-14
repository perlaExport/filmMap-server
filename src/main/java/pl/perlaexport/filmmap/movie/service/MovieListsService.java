package pl.perlaexport.filmmap.movie.service;

import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.List;

public interface MovieListsService {
    MovieResponse addMovieToFavourites(String movieId, UserEntity user);

    MovieResponse deleteMovieFromFavourites(String movieId, UserEntity user);

    MovieResponse addMovieToWatchLater(String movieId, UserEntity user);

    MovieResponse deleteMovieFromWatchLater(String movieId, UserEntity user);

    List<MovieEntity> getUserFavouritesMovies(UserEntity user);

    List<MovieEntity> getUserWatchLaterMovies(UserEntity user);
}
