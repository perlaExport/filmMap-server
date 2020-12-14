package pl.perlaexport.filmmap.movie.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponse {
    String movieId;
    Double avgRate;
    Integer userRate;
    @Builder.Default
    boolean isFavourite = false;
    @Builder.Default
    boolean isWatchLater = false;
}
