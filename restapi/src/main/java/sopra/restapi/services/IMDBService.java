package sopra.restapi.services;

import org.springframework.http.ResponseEntity;
import sopra.restapi.dtos.FilmDTO;

import java.util.List;

public interface IMDBService {

    ResponseEntity<List<FilmDTO>> getFilmsByDirector(String director);
}
