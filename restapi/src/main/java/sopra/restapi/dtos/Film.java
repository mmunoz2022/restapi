package sopra.restapi.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class Film {

    private String filmName;
    private int year;
    private String director;

}
