package pl.perlaexport.filmmap.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.movie.response.MovieListResponse;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieListsServiceImpl implements MovieListsService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

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
    public MovieListResponse getUserFavouritesMovies(UserEntity user, int limit, int page) {
        return getMovieListResponse(limit, page, user.getFavouriteMovies());
    }

    @Override
    public MovieListResponse getUserWatchLaterMovies(UserEntity user, int limit, int page) {
        return getMovieListResponse(limit, page, user.getWatchLaterMovies());
    }

    @Override
    public MovieListResponse getUserRatedMovies(UserEntity user, int limit, int page) {
        return getMovieListResponse(limit, page,
                user.getRatings().stream().map(RatingEntity::getMovie).collect(Collectors.toList()));
    }

    private MovieListResponse getMovieListResponse(int limit, int page, List<MovieEntity> list) {
        if (list.isEmpty())
            return new MovieListResponse(new ArrayList<>(),1,1);
        Collections.reverse(list);
        int listSize = list.size();
        int start = page * limit >= listSize ?  (page - 1) * limit : page * limit;
        int end = Math.min(page * limit + limit, listSize);
        int amountOfPages = (int) Math.ceil((double) listSize / limit);
        return new MovieListResponse(list.subList(start,end),Math.min(page,amountOfPages), amountOfPages);
    }

}
