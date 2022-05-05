package sopra.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopra.restapi.dtos.FilmDTO;
import sopra.restapi.services.IMDBService;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    IMDBService imdbService;

    @Operation(summary = "Peliculas dirigidas por un director concreto", description = "Peliculas", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "400", description = "Invalid station supplied")})
    @GetMapping("/filmsByDirector")
    public ResponseEntity<List<FilmDTO>> getFilmsByDirector(@RequestParam(value = "director") String director) {

        return  imdbService.getFilmsByDirector(director);
    }
}
