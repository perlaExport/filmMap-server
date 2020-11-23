package pl.perlaexport.filmmap.rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.rating.service.RatingService;
import pl.perlaexport.filmmap.user.current.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/movie/{id}/ratings")
    public List<RatingEntity> getMovieRatings(@PathVariable String id) {
        return ratingService.getMovieRatings(id);
    }

    @GetMapping("/user/{id}/ratings")
    public List<RatingEntity> getUserRatings(@PathVariable Long id){
        return ratingService.getUserRatings(id);
    }
    @GetMapping("/user/ratings")
    public List<RatingEntity> getCurrentUserRatings(HttpServletRequest request){
        return ratingService.getUserRatings(CurrentUser.get(request));
    }
}
