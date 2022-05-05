package sopra.restapi.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sopra.restapi.dtos.FilmDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IMDBServiceImpl implements IMDBService{

    @Override
    public ResponseEntity<List<FilmDTO>> getFilmsByDirector(String director) {
        FilmDTO f1 = new FilmDTO("Pulp Fiction", "Tarantino", "1995");
        FilmDTO f2 = new FilmDTO("Gladiator", "Scott", "2005");
        FilmDTO f3 = new FilmDTO("Infiltrados", "Scorsese", "2011");
        FilmDTO f4 = new FilmDTO("Interstellar", "Nolan", "2015");
        FilmDTO f5 = new FilmDTO("Uno de los nuestros", "Scorsese", "1999");
        FilmDTO f6 = new FilmDTO("Malditos bastardos", "Tarantino", "2011");

        List<FilmDTO> films = Arrays.asList(f1, f2, f3, f4, f5, f6);

        films.stream()
                .filter(f -> f.getFilmDirector().equals(director))
                .collect(Collectors.toList());

        return new ResponseEntity<List<String>>(films, HttpStatus.OK);
    }
}
