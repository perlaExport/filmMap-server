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

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    @PostMapping("/movie/add")
    public MovieResponse addMovie(@Valid @RequestBody MovieDto movieDto, HttpServletRequest request){
        return movieService.addMovie(movieDto);
    }
    @PutMapping("/movie/{id}/rate")
    public MovieResponse rateMovie(@PathVariable String id, @RequestParam(name="rate") Integer rate,
                                 HttpServletRequest request){
        return movieService.rateMovie(id,rate,CurrentUser.get(request));
    }
    @DeleteMapping("/movie/{id}/delete_rate")
    public MovieResponse deleteRating(@PathVariable String id,HttpServletRequest request){
        return movieService.deleteRating(id, CurrentUser.get(request));
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
