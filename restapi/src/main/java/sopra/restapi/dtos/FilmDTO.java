package sopra.restapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FilmDTO {
    private String filmName;
    private String filmDirector;
    private String filmYear;
}
