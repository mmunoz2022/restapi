package sopra.restapi.services;

import org.springframework.http.ResponseEntity;
import sopra.restapi.dtos.FilmDTO;
import sopra.restapi.dtos.MessageDTO;

import java.util.List;

public interface IMDBService {

    ResponseEntity<List<FilmDTO>> getFilmsByDirector(String director);

    ResponseEntity<String> netflixMessage(String titulo, String director, String año);

    void sendMessage (MessageDTO mensaje);
}
