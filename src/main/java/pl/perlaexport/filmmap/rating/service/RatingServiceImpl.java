package pl.perlaexport.filmmap.rating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.perlaexport.filmmap.movie.exception.MovieNotFoundException;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.movie.repository.MovieRepository;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.rating.repository.RatingRepository;
import pl.perlaexport.filmmap.security.exception.UserNotFoundException;
import pl.perlaexport.filmmap.user.model.UserEntity;
import pl.perlaexport.filmmap.user.repository.UserRepository;

import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private UserRepository userRepository;
    private MovieRepository movieRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository,
                             MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public List<RatingEntity> getMovieRatings(String movie_id) {
        MovieEntity movie = movieRepository.findById(movie_id).orElseThrow(
                () -> new MovieNotFoundException(movie_id)
        );
        return ratingRepository.findAllByMovie(movie);
    }

    @Override
    public List<RatingEntity> getUserRatings(Long user_id) {
        UserEntity user = userRepository.findById(user_id).orElseThrow(
                () -> new UserNotFoundException(user_id)
        );
        return ratingRepository.findAllByUser(user);
    }

    @Override
    public List<RatingEntity> getUserRatings(UserEntity user) {
        return ratingRepository.findAllByUser(user);
    }
}
