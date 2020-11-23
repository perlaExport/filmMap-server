package pl.perlaexport.filmmap.movie.service;

import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.List;

public interface MovieService {
    MovieEntity addMovie(MovieDto movieDto, UserEntity user);
}
