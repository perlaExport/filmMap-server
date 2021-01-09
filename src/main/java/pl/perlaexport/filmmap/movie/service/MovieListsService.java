package pl.perlaexport.filmmap.movie.service;

import pl.perlaexport.filmmap.movie.response.MovieListResponse;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.user.model.UserEntity;

public interface MovieListsService {
    MovieResponse addMovieToFavourites(String movieId, UserEntity user);

    MovieResponse deleteMovieFromFavourites(String movieId, UserEntity user);

    MovieResponse addMovieToWatchLater(String movieId, UserEntity user);

    MovieResponse deleteMovieFromWatchLater(String movieId, UserEntity user);

    MovieListResponse getUserFavouritesMovies(UserEntity user, int limit, int page);

    MovieListResponse getUserWatchLaterMovies(UserEntity user, int limit, int page);

    MovieListResponse getUserRatedMovies(UserEntity user, int limit, int page);
}
