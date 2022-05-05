package sopra.restapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sopra.restapi.services.IMDBService;

@RestController("/")
public class Controller {

    @Autowired
    IMDBService imdbService;

    @Operation(summary = "La temperatura maxima de una estación en los últimos 15 días", description = "Temperatura maxima 15 días", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Double.class))),
            @ApiResponse(responseCode = "400", description = "Invalid station supplied")})
    @GetMapping("/titulo")
    public ResponseEntity<T> getmaxima15(@RequestParam(value = "station") String station) {

        return  imdbService.getFilmsYear()
    }
}
