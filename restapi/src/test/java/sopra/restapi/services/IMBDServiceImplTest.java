package sopra.restapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sopra.restapi.dtos.Film;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IMBDServiceImplTest {
    @Mock
    RestTemplate restTemplateMock;

    @InjectMocks
    IMDBService imdbService;


    @Test
    public void checkFilmsByYearBadYear() {

        //GIVEN
        Integer year = 1222;
        //WHEN
        ResponseEntity<List<Film>> re = imdbService.getFilmsByYear(year);
        //THEN
        assertEquals(re.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void checkFilmsByYearValidYear() {

        //GIVEN
        Integer year = 2000;
        //WHEN
        when(restTemplateMock.getForObject("url", List.class)).thenReturn(new ArrayList<>());
        ResponseEntity<List<Film>> re = imdbService.getFilmsByYear(year);
        //THEN
        assertEquals(re.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void checkFilmsByYearAPIFails() {

        //GIVEN
        Integer year = 2000;
        //WHEN
        when(restTemplateMock.getForObject("url", List.class)).thenReturn(null);
        var re = imdbService.getFilmsByYear(year);
        //THEN
        assertEquals(re.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
