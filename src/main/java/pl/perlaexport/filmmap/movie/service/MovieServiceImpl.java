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
    private UserRepository userRepository;
    private RatingRepository ratingRepository;


    @Autowired
    public MovieServiceImpl(CategoryRepository categoryRepository, MovieRepository movieRepository,
                            UserRepository userRepository, RatingRepository ratingRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public MovieEntity addMovie(MovieDto movieDto, UserEntity user) {
        if (movieRepository.findById(movieDto.getId()).isPresent())
            throw new MovieAlreadyExistsException(movieDto.getId());
        MovieEntity movie = MovieEntity.builder().id(movieDto.getId()).
                title(movieDto.getTitle()).categories(getCategories(movieDto.getCategories())).build();
        RatingEntity rating = RatingEntity.builder().movie(movie).user(user).rating(movieDto.getRating()).build();
        movie.getRatings().add(rating);
        movie.calcRating();
        return movieRepository.save(movie);
    }

    @Override
    public MovieResponse getMovie(String movieId, UserEntity user) {
        MovieEntity movie =  movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        Optional<RatingEntity> ratingEntity = movie.getRatings().stream().filter(e -> user.equals(e.getUser())).findAny();
        int userRate = ratingEntity.map(RatingEntity::getRating).orElse(0);
        return new MovieResponse(movieId,movie.getRating(),userRate);
    }

    @Override
    public MovieEntity rateMovie(String movieId, Integer rating, UserEntity user) {
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
        return movieRepository.save(movie);
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

    private Integer validRating(Integer rating) {
        if (rating < 1 || rating > 5)
            throw new BadRatingRangeException(rating);
        return rating;
    }
}
