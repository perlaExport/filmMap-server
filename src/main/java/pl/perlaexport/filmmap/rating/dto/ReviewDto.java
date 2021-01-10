package pl.perlaexport.filmmap.rating.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReviewDto {
    @NotNull(message = "review cannot be null")
    @NotEmpty(message = "review cannot be empty")
    String review;
}
