package sopra.restapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sopra.restapi.dtos.Film;
import sopra.restapi.repositories.FilmsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class IMDBServiceImpl implements IMDBService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private FilmsRepository filmsRepository;

    private final String EXTERNALAPIERROR = "EXTERNAL API ERROR";

    private final String WRONGYEARERROR = "WRONG YEAR ERROR";

    @Override
    public ResponseEntity<List<Film>> getFilmsByYear(Integer year) {
        return Optional.of(year)
                .filter(x -> x > 1985)
                .map(validYear -> Optional.ofNullable(getFilmsFromExternalIMDBApi(year))
                        .map(ResponseEntity::ok)
                        .orElse(handleError(this.EXTERNALAPIERROR)))
                .orElse(handleError(this.WRONGYEARERROR));

    }


    private List<Film> getFilmsFromExternalIMDBApi(Integer year) {

        return filmsRepository.findByYear(year);


    }

    private ResponseEntity<List<Film>> handleError(String error) {

        switch (error) {
            case "EXTERNAL API ERROR":
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            case "WRONG YEAR ERROR":
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            default:
                return null;
        }
    }
}
