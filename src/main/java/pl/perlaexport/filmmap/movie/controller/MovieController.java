package pl.perlaexport.filmmap.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.movie.service.MovieService;
import pl.perlaexport.filmmap.user.current.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @PostMapping("/movie/add")
    public MovieResponse addMovie(@Valid @RequestBody MovieDto movieDto, HttpServletRequest request){
        return movieService.addMovie(movieDto);
    }

    @GetMapping("/movie/{id}")
    public MovieResponse getMovie(@PathVariable String id, HttpServletRequest request){
        return movieService.getMovie(id,CurrentUser.get(request));
    }
    @GetMapping("/movie/random")
    public MovieResponse getRandomMovie(HttpServletRequest request){
        return movieService.getRandomMovie(CurrentUser.get(request));
    }
}
