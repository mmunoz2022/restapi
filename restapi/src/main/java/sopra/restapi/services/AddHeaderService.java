package sopra.restapi.services;

import org.springframework.http.ResponseEntity;
import sopra.restapi.dtos.Film;
import sopra.restapi.dtos.FilmWithHeader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public interface AddHeaderService {

    FilmWithHeader getFilmWithHeader(String filmJson);
}
