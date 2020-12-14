package pl.perlaexport.filmmap.movie.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.perlaexport.filmmap.category.model.CategoryEntity;
import pl.perlaexport.filmmap.rating.model.RatingEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "movie")
public class MovieEntity {
    @Id
    @Column(name = "movie_id")
    private String id;
    @NotNull
    @EqualsAndHashCode.Exclude
    private double rating;
    @NotNull
    @NotBlank
    private String title;
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @Builder.Default
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "movies")
    Set<CategoryEntity> categories = new HashSet<>();
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RatingEntity> ratings = new ArrayList<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "favouriteMovies")
    Set<UserEntity> favouriteUsers = new LinkedHashSet<>();

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "watchLaterMovies")
    Set<UserEntity> watchLaterUsers = new LinkedHashSet<>();

    public void calcRating(){
        setRating(getRatings().stream().mapToDouble(RatingEntity::getRating).average().orElse(0d));
    }
}
