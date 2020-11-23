package pl.perlaexport.filmmap.movie.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.perlaexport.filmmap.category.model.CategoryEntity;
import pl.perlaexport.filmmap.rating.model.RatingEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private double rating;
    @NotNull
    @NotBlank
    private String title;
    @JsonBackReference
    @Builder.Default
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "movies")
    Set<CategoryEntity> categories = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Builder.Default
    private List<RatingEntity> ratings = new ArrayList<>();

    public void calcRating(){
        setRating(getRatings().stream().mapToDouble(RatingEntity::getRating).average().orElse(0d));
    }
}
