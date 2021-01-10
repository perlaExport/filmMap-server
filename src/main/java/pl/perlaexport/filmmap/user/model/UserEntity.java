package pl.perlaexport.filmmap.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.perlaexport.filmmap.movie.model.MovieEntity;
import pl.perlaexport.filmmap.rating.model.RatingEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Email
    @NotNull
    private String email;
    @NotNull
    @NotBlank
    private String name;
    @JsonIgnore
    private String password;
    @Builder.Default
    private boolean enabled = false;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthType authType;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RatingEntity> ratings = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    List<MovieEntity> favouriteMovies = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "watch_later",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id")
    )
    List<MovieEntity> watchLaterMovies = new ArrayList<>();

    public Optional<RatingEntity> getUserRate(MovieEntity movie){
        return ratings.stream().filter(e -> e.getUser() == this && e.getMovie().equals(movie)).findFirst();
    }

    public boolean isFavouriteMovie(MovieEntity movie){
        return favouriteMovies.contains(movie);
    }

    public boolean isToWatchLaterMovie(MovieEntity movie){
        return watchLaterMovies.contains(movie);
    }

}

