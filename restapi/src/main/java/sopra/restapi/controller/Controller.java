package sopra.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopra.restapi.dtos.Film;
import sopra.restapi.dtos.FilmWithHeader;
import sopra.restapi.services.AddHeaderService;
import sopra.restapi.services.IMDBService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("imdb")
public class Controller {

    @Autowired
    private IMDBService imdbService;

    @Autowired
    private AddHeaderService addHeaderService;

    @Operation(summary = "films by year", description = "Provides a list of films released in chosen year", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid year supplied")})
    @GetMapping("/filmsByYear")
    public ResponseEntity<List<Film>> getFilmsByYear(@RequestParam(value = "year") String year) {
        return Optional.ofNullable(year)
                .filter(y -> y.matches("\\d+"))
                .map(x -> imdbService.getFilmsByYear(Integer.valueOf(year)))
                .orElseThrow(NumberFormatException::new);

    }

    @Operation(summary = "film details with header", description = "Film description with Streaming Application header", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid film request")})
    @GetMapping("/filmWithHeader")
    public ResponseEntity<FilmWithHeader> getFilmWithHeader(@RequestParam(value = "film") String film) {

        return Optional.of(film)
                .map(addHeaderService::getFilmWithHeader)
                .map(ResponseEntity::ok)
                .orElseThrow(RuntimeException::new);
    }

    private ResponseEntity<FilmWithHeader> handleError(Throwable throwable) {
        System.out.println("Exception thrown when translating words");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}