package pl.perlaexport.filmmap.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.category.model.CategoryEntity;
import pl.perlaexport.filmmap.category.repository.CategoryRepository;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.exception.MovieAlreadyExistsException;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.exception.NotMoviesInDatabaseException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final CategoryRepository categoryRepository;
    private final MovieRepository movieRepository;


    @Autowired
    public MovieServiceImpl(CategoryRepository categoryRepository, MovieRepository movieRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieResponse addMovie(MovieDto movieDto) {
        if (movieRepository.findById(movieDto.getId()).isPresent())
            throw new MovieAlreadyExistsException(movieDto.getId());
        MovieEntity movie = MovieEntity.builder().id(movieDto.getId()).
                title(movieDto.getTitle()).categories(getCategories(movieDto.getCategories())).imgPath(movieDto.getImgPath()).build();
        movieRepository.save(movie);
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(0).build();
    }

    @Override
    public MovieResponse getMovie(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                userRate(user.getUserRate(movie)).isFavourite(user.isFavouriteMovie(movie)).
                isWatchLater(user.isToWatchLaterMovie(movie)).build();
    }


    @Override
    public MovieResponse getRandomMovie(UserEntity user) {
        List<MovieEntity> movies = (List<MovieEntity>) movieRepository.findAll();
        movies.removeAll(user.getRatings().stream().map(RatingEntity::getMovie).collect(Collectors.toList()));
        if (movies.isEmpty())
            throw new NotMoviesInDatabaseException();
        MovieEntity movie = movies.get(new Random().nextInt(movies.size()));
        return MovieResponse.builder().movieId(movie.getId()).avgRate(movie.getRating()).
                isWatchLater(user.isToWatchLaterMovie(movie)).isFavourite(user.isFavouriteMovie(movie)).build();
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
}
