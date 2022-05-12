
package sopra.restapi.dtos;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FilmWithHeader {
    private String streamingApp;
    private LocalDate date;
    private String filmDetails;



}
