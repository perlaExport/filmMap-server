package pl.perlaexport.filmmap.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.perlaexport.filmmap.movie.dto.MovieDto;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
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
    @PutMapping("/movie/{id}/rate")
    public MovieEntity rateMovie(@PathVariable String id, @RequestParam(name="rate") Integer rate,
                                 HttpServletRequest request){
        return movieService.rateMovie(id,rate,CurrentUser.get(request));
    }
    @GetMapping("/movie/{id}")
    public MovieResponse getMovie(@PathVariable String id, HttpServletRequest request){
        return movieService.getMovie(id,CurrentUser.get(request));
    }
}
