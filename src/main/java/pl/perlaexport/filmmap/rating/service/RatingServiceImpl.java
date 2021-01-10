package pl.perlaexport.filmmap.rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.dto.ReviewDto;
import pl.perlaexport.filmmap.rating.exception.BadRatingRangeException;
import pl.perlaexport.filmmap.rating.exception.RatingNotFoundException;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.rating.repository.RatingRepository;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public MovieResponse rateMovie(String movieId, Integer rating, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        if (user.isToWatchLaterMovie(movie))
            user.getWatchLaterMovies().remove(movie);
        Optional<RatingEntity> userRating = movie.getRatings().stream().
                filter(r -> user.equals(r.getUser())).findAny();
        if (userRating.isEmpty()) {
            RatingEntity newRating = RatingEntity.builder().user(user).movie(movie).rating(validRating(rating)).build();
            movie.getRatings().add(newRating);
        } else
            userRating.get().setRating(validRating(rating));
        movie.calcRating();
        movieRepository.save(movie);
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(rating).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }

    @Override
    public MovieResponse deleteRating(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        RatingEntity rating = ratingRepository.findByMovieAndUser(movie, user).orElseThrow(
                () -> new RatingNotFoundException(movieId, user.getEmail())
        );
        movie.getRatings().remove(rating);
        movie.calcRating();
        movieRepository.save(movie);
        ratingRepository.delete(rating);
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(0).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }

    @Override
    public MovieResponse reviewMovie(ReviewDto review, String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId));
        RatingEntity rating = ratingRepository.findByMovieAndUser(movie, user).orElseThrow(
                () -> new RatingNotFoundException(movieId, user.getEmail()));
        rating.setReview(review.getReview());
        ratingRepository.save(rating);
        return MovieResponse.builder().movieId(movieId).userRate(rating.getRating()).
                avgRate(movie.getRating()).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }

    @Override
    public MovieResponse deleteReview(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId));
        RatingEntity rating = ratingRepository.findByMovieAndUser(movie, user).orElseThrow(
                () -> new RatingNotFoundException(movieId, user.getEmail()));
        rating.setReview(null);
        ratingRepository.save(rating);
        return MovieResponse.builder().movieId(movieId).userRate(rating.getRating()).
                avgRate(movie.getRating()).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }

    private int validRating(Integer rating) {
        if (rating < 1 || rating > 5)
            throw new BadRatingRangeException(rating);
        return rating;
    }
}
