package pl.perlaexport.filmmap.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.service.MovieService;
import pl.perlaexport.filmmap.user.current.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @PostMapping("/movie/add")
    public MovieEntity addMovie(@Valid @RequestBody MovieDto movieDto, HttpServletRequest request){
        return movieService.addMovie(movieDto, CurrentUser.get(request));
    }
}
