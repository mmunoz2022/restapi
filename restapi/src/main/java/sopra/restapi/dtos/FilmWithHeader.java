package sopra.restapi.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
public class FilmWithHeader {
    private String streamingApp;
    private LocalDate date;
    private String filmDetails;
}
