package pl.perlaexport.filmmap.movie.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.perlaexport.filmmap.category.model.CategoryEntity;
import pl.perlaexport.filmmap.user.model.UserEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;
    @NotNull
    @NotBlank
    private double rating;
    @NotNull
    @NotBlank
    private String title;
    @JsonBackReference
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY, mappedBy = "movieEntities")
    Set<CategoryEntity> categoryEntities = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "rating", cascade = CascadeType.ALL)
    private List<RatingEntity> ratingEntityList = new ArrayList<>();
}
