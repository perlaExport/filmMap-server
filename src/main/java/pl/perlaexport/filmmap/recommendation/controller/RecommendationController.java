package pl.perlaexport.filmmap.recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.recommendation.service.RecommendationService;
import pl.perlaexport.filmmap.user.current.CurrentUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/movie/{id}/recommendation")
    public Integer getMovieRecommendation(@PathVariable String id, HttpServletRequest request){
        return recommendationService.getRecommendation(id, CurrentUser.get(request));
    }

    @GetMapping("/movie/recommendation")
    public List<MovieEntity> getTopMovies(HttpServletRequest request, @RequestParam Integer size) {
        return recommendationService.getTopRecommendations(CurrentUser.get(request),size);
    }
}
