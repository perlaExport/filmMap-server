package pl.perlaexport.filmmap.movie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.category.model.CategoryEntity;
import pl.perlaexport.filmmap.category.repository.CategoryRepository;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService{

    private CategoryRepository categoryRepository;
    private MovieRepository movieRepository;
    private UserRepository userRepository;

    @Autowired
    public MovieServiceImpl(CategoryRepository categoryRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MovieEntity addMovie(MovieDto movieDto, UserEntity user) {
        MovieEntity movie = MovieEntity.builder().id(movieDto.getId()).
                title(movieDto.getTitle()).categories(getCategories(movieDto.getCategories())).build();
        RatingEntity rating = RatingEntity.builder().movie(movie).user(user).rating(movieDto.getRating()).build();
        movie.getRatings().add(rating);
        movie.calcRating();
        return movieRepository.save(movie);
    }

    private Set<CategoryEntity> getCategories(List<String> categories){
        Set<CategoryEntity> finalCategories = new HashSet<>();
        for (String c: categories){
            Optional<CategoryEntity> optionalCategory = categoryRepository.findByCategoryName(c);
            if (optionalCategory.isEmpty()){
                CategoryEntity category = new CategoryEntity();
                category.setCategoryName(c);
                categoryRepository.save(category);
                finalCategories.add(category);
            }
            else
                finalCategories.add(optionalCategory.get());
        }
        return finalCategories;
    }

}
