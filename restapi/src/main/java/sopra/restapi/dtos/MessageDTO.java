package sopra.restapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {
    private String codOrigin;
    private LocalDate dateSystem;
    private String desMessage;
}
