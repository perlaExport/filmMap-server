package pl.perlaexport.filmmap.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.category.model.CategoryEntity;
import pl.perlaexport.filmmap.category.repository.CategoryRepository;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.exception.MovieAlreadyExistsException;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.exception.BadRatingRangeException;
import pl.perlaexport.filmmap.rating.exception.RatingNotFoundException;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.rating.repository.RatingRepository;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {

    private CategoryRepository categoryRepository;
    private MovieRepository movieRepository;
    private RatingRepository ratingRepository;


    @Autowired
    public MovieServiceImpl(CategoryRepository categoryRepository, MovieRepository movieRepository,
                            RatingRepository ratingRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public MovieResponse addMovie(MovieDto movieDto, UserEntity user) {
        if (movieRepository.findById(movieDto.getId()).isPresent())
            throw new MovieAlreadyExistsException(movieDto.getId());
        MovieEntity movie = MovieEntity.builder().id(movieDto.getId()).
                title(movieDto.getTitle()).categories(getCategories(movieDto.getCategories())).build();
        RatingEntity rating = RatingEntity.builder().movie(movie).user(user).rating(movieDto.getRating()).build();
        movie.getRatings().add(rating);
        movie.calcRating();
        movieRepository.save(movie);
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(rating.getRating()).build();
    }

    @Override
    public MovieResponse getMovie(String movieId, UserEntity user) {
        MovieEntity movie =  movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(user.getUserRate(movie)).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }

    @Override
    public MovieResponse rateMovie(String movieId, Integer rating, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
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
        RatingEntity rating = ratingRepository.findByMovieAndUser(movie,user).orElseThrow(
                () -> new RatingNotFoundException(movieId,user.getEmail())
        );
        movie.getRatings().remove(rating);
        movie.calcRating();
        movieRepository.save(movie);
        ratingRepository.delete(rating);
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(0).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }

    private Set<CategoryEntity> getCategories(List<String> categories) {
        Set<CategoryEntity> finalCategories = new HashSet<>();
        for (String c : categories) {
            Optional<CategoryEntity> optionalCategory = categoryRepository.findByCategoryName(c);
            if (optionalCategory.isEmpty()) {
                CategoryEntity category = new CategoryEntity();
                category.setCategoryName(c);
                categoryRepository.save(category);
                finalCategories.add(category);
            } else
                finalCategories.add(optionalCategory.get());
        }
        return finalCategories;
    }

    private int validRating(Integer rating) {
        if (rating < 1 || rating > 5)
            throw new BadRatingRangeException(rating);
        return rating;
    }

}
