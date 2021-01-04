package pl.perlaexport.filmmap.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.perlaexport.filmmap.movie.response.MovieListResponse;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.movie.service.MovieListsService;
import pl.perlaexport.filmmap.user.current.CurrentUser;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MovieListsController {

    private MovieListsService movieListsService;

    @Autowired
    public MovieListsController(MovieListsService movieListsService) {
        this.movieListsService = movieListsService;
    }

    @GetMapping("/movie/favourites")
    public MovieListResponse getFavourites(HttpServletRequest request, @RequestParam Integer limit, @RequestParam Integer page){
        return movieListsService.getUserFavouritesMovies(CurrentUser.get(request), limit, page);
    }

    @GetMapping("/movie/watch_later")
    public MovieListResponse getWatchLaterMovies(HttpServletRequest request, @RequestParam Integer limit, @RequestParam Integer page){
        return movieListsService.getUserWatchLaterMovies(CurrentUser.get(request), limit, page);
    }

    @GetMapping("/movie/rated")
    public MovieListResponse getUserRatedMovies(HttpServletRequest request, @RequestParam Integer limit, @RequestParam Integer page){
        return movieListsService.getUserRatedMovies(CurrentUser.get(request), limit, page);
    }

    @PostMapping("/movie/favourites/add/{movieId}")
    public MovieResponse addMovieToFavourites(@PathVariable String movieId, HttpServletRequest request){
        return movieListsService.addMovieToFavourites(movieId,CurrentUser.get(request));
    }

    @PostMapping("/movie/watch_later/add/{movieId}")
    public MovieResponse addMovieToWatchLaterMovies(@PathVariable String movieId, HttpServletRequest request){
        return movieListsService.addMovieToWatchLater(movieId,CurrentUser.get(request));
    }

    @DeleteMapping("/movie/favourites/delete/{movieId}")
    public MovieResponse deleteMovieFromFavourites(@PathVariable String movieId, HttpServletRequest request){
        return movieListsService.deleteMovieFromFavourites(movieId,CurrentUser.get(request));
    }

    @DeleteMapping("/movie/watch_later/delete/{movieId}")
    public MovieResponse deleteMovieFromWatchLaterMovies(@PathVariable String movieId, HttpServletRequest request){
        return movieListsService.deleteMovieFromWatchLater(movieId,CurrentUser.get(request));
    }
}
