package sopra.restapi.services;

import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sopra.restapi.dtos.Film;
import sopra.restapi.dtos.FilmWithHeader;
import sopra.restapi.dtos.NetflixResponse;
import sopra.restapi.repositories.FilmsRepository;
import sopra.restapi.util.Unchecked;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service
public class AddHeaderServiceImpl implements AddHeaderService {
    @Autowired
    private FilmsRepository filmsRepository;

    /*
    Main method of the class. Cast the input to JSON, checks if is valid and process it.
     */
    @Override
    public CompletableFuture<FilmWithHeader> getFilmWithHeader(String filmJson) {
        return Optional.ofNullable(filmJson)
                .map(JSONObject::new)
                .filter(this::checkEverything).flatMap(validJson ->
                        Optional.ofNullable(this.processJson(validJson)))
                .orElseThrow(IllegalArgumentException::new);
    }

    private CompletableFuture<FilmWithHeader> processJson(JSONObject jsonFilm) {
        var saveInDbPromise = this.saveToDatabase(jsonFilm);
        var responseOkPromise = this.sendOkToNetflix(jsonFilm);
        return responseOkPromise.thenCombine(saveInDbPromise, (resp, film) -> {
                    System.out.println("Ambos procesos han terminado!");
                    return this.buildFilmWithHeaderFromJson(jsonFilm);
                })
                .exceptionally(ex -> null);
    }
    //
    private CompletableFuture<Film> saveToDatabase(JSONObject jsonFilm) {
        return CompletableFuture.supplyAsync(() ->
                sampleProcessingSaveInDataBase(new Gson().fromJson(jsonFilm.toString(), Film.class)));
        //.thenApply(film -> filmsRepository.save(new Gson().fromJson(jsonFilm.toString(), Film.class)))
    }
    /*
    This method build a new NetflixResponse which returns inside in an OK from a ResponseEntity in form of a future.
     */
    private CompletableFuture<ResponseEntity<NetflixResponse>> sendOkToNetflix(JSONObject jsonFilm) {
        return CompletableFuture.supplyAsync(() ->
                sampleProcessingNetflix(NetflixResponse.builder()
                        .code(jsonFilm.get("filmName").toString())
                        .build()));
            /* CompletableFuture.supplyAsync(() -> jsonFilm)
                    .thenApply(js -> js.get("filmName"))
                    .thenApply(filmName -> toString())
                    .thenApply(filmNameString -> NetflixResponse.builder().code(filmNameString).build())
                    .thenApply(netflixResponse -> sampleProcessingNetflix(netflixResponse));
            */
    }
    //
    private FilmWithHeader buildFilmWithHeaderFromJson(JSONObject jsonObject) {
        return FilmWithHeader.builder()
                .date(LocalDate.now())
                .streamingApp("Netflix")
                .filmDetails(jsonObject.get("filmName").toString() + ", " + jsonObject.get("director").toString() + ", " + jsonObject.get("year").toString())
                .build();
    }
    //
    private Film sampleProcessingSaveInDataBase(Film film) {
        System.out.println("Se esta guardando en la base de datos....10s");
        return Optional.of(10l)
                .stream()
                .peek(Unchecked.consumer(TimeUnit.SECONDS::sleep))
                .findFirst()
                .map(sleepFinished -> {
                    System.out.println("Guradado en la base de datos");
                    return film;
                })
                .orElseThrow(RuntimeException::new);
    }
    //
    private ResponseEntity<NetflixResponse> sampleProcessingNetflix(NetflixResponse netflixResponse) {
        System.out.println("Se esta enviando la respuesta a netflix....20s");
        return Optional.of(20l)
                .stream()
                .peek(Unchecked.consumer(TimeUnit.SECONDS::sleep))
                .findFirst()
                .map(sleepFinished -> netflixResponse)
                .map(ResponseEntity::ok)
                .map(re -> {
                    System.out.println("Enviado a netflix");
                    return re;
                })
                .orElseThrow(RuntimeException::new);
    }
    /*
    Customizable method to check if a JSONObject is valid
     */
    private boolean checkEverything(JSONObject jsonObject) {
        return jsonObject.has("filmName") && jsonObject.has("director") && jsonObject.has("year");
    }
}