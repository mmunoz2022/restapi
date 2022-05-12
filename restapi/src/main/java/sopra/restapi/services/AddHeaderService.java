package sopra.restapi.services;

import sopra.restapi.dtos.FilmWithHeader;

import java.util.concurrent.CompletableFuture;

public interface AddHeaderService {

    CompletableFuture<FilmWithHeader> getFilmWithHeader(String filmJson);
}
