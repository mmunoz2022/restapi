package sopra.restapi.services;

import org.springframework.http.ResponseEntity;

public interface IMDBService {

    ResponseEntity<T> getFilmsYear(int year);
}
