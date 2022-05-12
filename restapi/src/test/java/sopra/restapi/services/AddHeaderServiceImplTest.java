package sopra.restapi.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sopra.restapi.dtos.Film;
import sopra.restapi.dtos.FilmWithHeader;
import sopra.restapi.repositories.FilmsRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class AddHeaderServiceImplTest {

    @Mock
    FilmsRepository filmsRepository;
    @Mock
    Thread thread;
    @InjectMocks
    AddHeaderServiceImpl addHeaderService;



    @Test
    public void getFilmWithHeaderNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            addHeaderService.getFilmWithHeader(null);
        });
    }

    @Test
    public void getFilmWithHeaderWrongJson() {
        var film = "{\n" +
                "  \"title\": \"El silencio de los corderos\",\n" +
                "  \"director\": \"Jonathan Demme\",\n" +
                "  \"year\": \"1991\"\n" +
                "}";
        assertThrows(IllegalArgumentException.class, () -> {
            addHeaderService.getFilmWithHeader(film);
        });
    }


    @Test
    public void getFilmWithHeaderValidJson() throws ExecutionException, InterruptedException {
        var film = "{\n" +
                "  \"filmName\": \"El silencio de los corderos\",\n" +
                "  \"director\": \"Jonathan Demme\",\n" +
                "  \"year\": \"1991\"\n" +
                "}";
        var filmWithHeader = FilmWithHeader.builder()
                .date(LocalDate.now())
                .streamingApp("Netflix")
                .filmDetails("El silencio de los corderos" + ", " + "Jonathan Demme" + ", " + "1991")
                .build();
        assertEquals(filmWithHeader,
            addHeaderService.getFilmWithHeader(film).get()
        );
    }


}
