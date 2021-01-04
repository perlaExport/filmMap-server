package pl.perlaexport.filmmap.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    @NotNull(message = "Id must be given")
    @NotBlank(message = "Id cannot be blank")
    String id;
    @NotNull(message = "Title must be given")
    @NotBlank(message = "Title cannot be blank")
    String title;
    @NotNull(message = "imgPath must be given")
    @NotBlank(message = "imgPath cannot be blank")
    String imgPath;
    List<String> categories;
}
