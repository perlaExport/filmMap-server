package pl.perlaexport.filmmap.recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.als.ALS;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.rating.repository.RatingRepository;
import pl.perlaexport.filmmap.recommendation.exception.MovieAlreadyRatedException;
import pl.perlaexport.filmmap.recommendation.exception.TooLessRatingsException;
import pl.perlaexport.filmmap.user.model.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    private final MovieRepository movieRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public RecommendationServiceImpl(MovieRepository movieRepository, RatingRepository ratingRepository) {
        this.movieRepository = movieRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Integer getRecommendation(String movieId, UserEntity user) {
        MovieEntity movie = movieRepository.findById(movieId).orElseThrow(
                () -> new MovieNotFoundException(movieId)
        );
        if (ratingRepository.findByMovieAndUser(movie,user).isPresent())
            throw new MovieAlreadyRatedException(movieId);
        int [][] matrix = getRecommendationMatrix(movie,user);
        double [][] newMatrix = ALS.getALS(matrix);
        return (int) Math.min(newMatrix[0][0]/5 * 100,100);
    }

    private int [][] getRecommendationMatrix(MovieEntity movie, UserEntity user){
        List<RatingEntity> ratingEntities = ratingRepository.findAllByMovie(movie);
        if (ratingEntities.size() < 9)
            throw new TooLessRatingsException(movie.getId());
        List<UserEntity> users = ratingEntities.stream().map(RatingEntity::getUser).sorted(
                Comparator.comparingInt(o -> o.getRatings().size())).limit(9).collect(Collectors.toList());
        List<MovieEntity> allMovies = new ArrayList<>();
        for (UserEntity u: users){
            allMovies.addAll(u.getRatings().stream().map(RatingEntity::getMovie).collect(Collectors.toList()));
        }
        List<MovieEntity> movies = user.getRatings().stream().map(RatingEntity::getMovie).
                filter(allMovies::contains).sorted(Comparator.comparingInt(o -> Collections.frequency(allMovies, o))).
                distinct().limit(9).collect(Collectors.toList());
        if (movies.size() < 9)
            throw new TooLessRatingsException(movie.getId());
        int [][] matrix = new int[10][10];
        matrix[0][0] = 0;
        users.add(0,user);
        for (int i = 1; i < users.size(); i++){
            matrix[i][0] = users.get(i).getRatings().stream().filter(e -> movie.equals(e.getMovie())).
                    mapToInt(RatingEntity::getRating).findFirst().orElse(0);
        }

        for (int i = 1; i < 10; i++){
            MovieEntity m = movies.get(i - 1);
            for (int j = 0; j < 10; j++){
                matrix[j][i] = users.get(j).getRatings().stream().filter(e -> m.equals(e.getMovie())).
                        mapToInt(RatingEntity::getRating).findFirst().orElse(0);
            }
        }
        return matrix;
    }


}
