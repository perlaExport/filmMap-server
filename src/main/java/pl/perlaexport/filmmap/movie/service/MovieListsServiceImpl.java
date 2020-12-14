package pl.perlaexport.filmmap.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.*;

@Service
public class MovieListsServiceImpl implements MovieListsService {

    private UserRepository userRepository;
    private MovieRepository movieRepository;

    @Autowired
    public MovieListsServiceImpl(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieResponse addMovieToFavourites(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() ->
            new MovieNotFoundException(movieId)
        );
        user.getFavouriteMovies().add(movie);
        userRepository.save(user);
        return MovieResponse.builder().movieId(movieId).avgRate(movie.getRating()).
                userRate(user.getUserRate(movie)).isWatchLater(user.isToWatchLaterMovie(movie)).
                isFavourite(true).build();
    }

    @Override
    public MovieResponse deleteMovieFromFavourites(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() ->
                new MovieNotFoundException(movieId)
        );
        user.getFavouriteMovies().remove(movie);
        userRepository.save(user);
        return MovieResponse.builder().movieId(movieId).avgRate(movie.getRating()).
                userRate(user.getUserRate(movie)).isWatchLater(user.isToWatchLaterMovie(movie)).
                isFavourite(false).build();
    }

    @Override
    public MovieResponse addMovieToWatchLater(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() ->
                new MovieNotFoundException(movieId)
        );
        user.getWatchLaterMovies().add(movie);
        userRepository.save(user);
        return MovieResponse.builder().movieId(movieId).avgRate(movie.getRating()).
                userRate(user.getUserRate(movie)).isWatchLater(true).
                isFavourite(user.isFavouriteMovie(movie)).build();
    }

    @Override
    public MovieResponse deleteMovieFromWatchLater(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(() ->
                new MovieNotFoundException(movieId)
        );
        user.getWatchLaterMovies().remove(movie);
        userRepository.save(user);
        return MovieResponse.builder().movieId(movieId).avgRate(movie.getRating()).
                userRate(user.getUserRate(movie)).isWatchLater(true).
                isFavourite(user.isFavouriteMovie(movie)).build();
    }

    @Override
    public List<MovieEntity> getUserFavouritesMovies(UserEntity user) {
        List<MovieEntity> favourites = new ArrayList<>(user.getFavouriteMovies());
        Collections.reverse(favourites);
        return favourites;
    }

    @Override
    public List<MovieEntity> getUserWatchLaterMovies(UserEntity user) {
        List<MovieEntity> watchLaterMovies = new ArrayList<>(user.getWatchLaterMovies());
        Collections.reverse(watchLaterMovies);
        return watchLaterMovies;
    }
}
