package sopra.restapi.services;

import org.springframework.http.ResponseEntity;
import sopra.restapi.dtos.Film;

import java.util.List;

public interface IMDBService {

    ResponseEntity<List<Film>> getFilmsByYear(Integer year);
}
