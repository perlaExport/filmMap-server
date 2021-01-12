package pl.perlaexport.filmmap.rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.movie.response.MovieListResponse;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.dto.ReviewDto;
import pl.perlaexport.filmmap.rating.exception.BadRatingRangeException;
import pl.perlaexport.filmmap.rating.exception.RatingNotFoundException;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.rating.repository.RatingRepository;
import pl.perlaexport.filmmap.rating.response.ReviewListResponse;
import pl.perlaexport.filmmap.rating.response.ReviewResponse;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String userReview = userRating.map(RatingEntity::getReview).orElse(null);
        movie.calcRating();
        movieRepository.save(movie);
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(rating).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).userReview(userReview).build();
    }

    @Override
    public MovieResponse deleteRating(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        RatingEntity rating = ratingRepository.findByMovieAndUser(movie, user).orElseThrow(
                () -> new RatingNotFoundException(movieId, user.getEmail())
        );
        if (user.isFavouriteMovie(movie))
            user.getFavouriteMovies().remove(movie);
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
                isWatchLater(user.isToWatchLaterMovie(movie)).userReview(rating.getReview()).build();
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
                isWatchLater(user.isToWatchLaterMovie(movie)).userReview(rating.getReview()).build();
    }

    @Override
    public ReviewListResponse getMovieReviews(String movieId, int limit, int page) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId));
        return getReviewListResponse(limit,page,movie);
    }

    private int validRating(Integer rating) {
        if (rating < 1 || rating > 5)
            throw new BadRatingRangeException(rating);
        return rating;
    }

    private ReviewListResponse getReviewListResponse(int limit, int page, MovieEntity movie) {
        List<ReviewResponse> list = movie.getRatings().stream().map(e -> new ReviewResponse(e.getUser(),e.getRating(),
                e.getReview())).filter(e -> e.getUserReview()!= null).collect(Collectors.toList());
        if (list.isEmpty())
            return new ReviewListResponse(new ArrayList<>(),1,1);
        Collections.reverse(list);
        int listSize = list.size();
        int start = page * limit >= listSize ?  (page - 1) * limit : page * limit;
        int end = Math.min(page * limit + limit, listSize);
        int amountOfPages = (int) Math.ceil((double) listSize / limit);
        return new ReviewListResponse(list.subList(start,end),Math.min(page,amountOfPages), amountOfPages);
    }
}
