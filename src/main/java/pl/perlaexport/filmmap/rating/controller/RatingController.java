package pl.perlaexport.filmmap.rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.perlaexport.filmmap.movie.response.MovieResponse;
import pl.perlaexport.filmmap.rating.dto.ReviewDto;
import pl.perlaexport.filmmap.rating.service.RatingService;
import pl.perlaexport.filmmap.user.current.CurrentUser;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PutMapping("/movie/{id}/rate")
    public MovieResponse rateMovie(@PathVariable String id, @RequestParam(name = "rate") Integer rate,
                                   HttpServletRequest request) {
        return ratingService.rateMovie(id, rate, CurrentUser.get(request));
    }

    @DeleteMapping("/movie/{id}/delete_rate")
    public MovieResponse deleteRating(@PathVariable String id, HttpServletRequest request) {
        return ratingService.deleteRating(id, CurrentUser.get(request));
    }

    @PutMapping("/movie/{id}/review")
    public MovieResponse reviewMovie(@RequestBody ReviewDto reviewDto, @PathVariable String id,
                                     HttpServletRequest request) {
        return ratingService.reviewMovie(reviewDto, id, CurrentUser.get(request));
    }

    @DeleteMapping("/movie/{id}/delete_review")
    public MovieResponse reviewMovie(@PathVariable String id, HttpServletRequest request) {
        return ratingService.deleteReview(id, CurrentUser.get(request));
    }
}
