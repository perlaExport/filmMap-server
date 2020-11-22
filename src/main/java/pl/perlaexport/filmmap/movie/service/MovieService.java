package pl.perlaexport.filmmap.movie.service;

import pl.perlaexport.filmmap.movie.model.MovieEntity;

public interface MovieService {
    MovieEntity addMovie(MovieDto movieDto);
}
