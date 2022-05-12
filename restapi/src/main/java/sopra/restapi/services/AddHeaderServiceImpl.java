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

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class AddHeaderServiceImpl implements AddHeaderService {
    @Autowired private FilmsRepository filmsRepository;
    /*
    Main method of the class. Cast the input to JSON, checks if is valid and process it.
     */
    @Override
    public FilmWithHeader getFilmWithHeader(String filmJson) {
        return Optional.of(filmJson)
                .map(JSONObject::new)
                .filter(this::checkEverything)
                .map(this::processJson)
                .orElseThrow(IllegalArgumentException::new);
    }

    private FilmWithHeader processJson(JSONObject jsonFilm) {
        CompletableFuture<ResponseEntity<NetflixResponse>> responseOkPromise = this.sendOkToNetflix(jsonFilm);
        CompletableFuture<FilmWithHeader> buildFilmWithHeaderPromise = this.buildFilmWithHeaderFromJson(jsonFilm);

        try {
            System.out.println("Main program keeps runnig...Saving film in DB");
            Film film = new Gson().fromJson(jsonFilm.toString(), Film.class);
            filmsRepository.save(film);
            return (FilmWithHeader) CompletableFuture.anyOf(buildFilmWithHeaderPromise).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    This method build a new NetflixResponse which returns inside in an OK from a ResponseEntity in form of a future.
     */
    private CompletableFuture<ResponseEntity<NetflixResponse>> sendOkToNetflix(JSONObject jsonFilm) {


        return CompletableFuture.supplyAsync(() -> {
            //Build the answer
            NetflixResponse netflixResponse = NetflixResponse.builder().code(jsonFilm.get("filmName").toString()).build();
            //Process it
            return sampleProcessingNetflix(netflixResponse);
        });
    }


    private CompletableFuture<FilmWithHeader> buildFilmWithHeaderFromJson(JSONObject jsonObject) {


        return CompletableFuture.supplyAsync(() -> {
            //Build the answer
            FilmWithHeader filmWithHeader = FilmWithHeader.builder()
                    .date(LocalDate.now())
                    .streamingApp("Netflix")
                    .filmDetails(jsonObject.get("filmName").toString() + ", " + jsonObject.get("director").toString() + ", " + jsonObject.get("year").toString())
                    .build();
            //Process it
            return sampleProcessingAnotherMicroService(filmWithHeader);
        });
    }

    private FilmWithHeader sampleProcessingAnotherMicroService(FilmWithHeader filmWithHeader) {

        System.out.println("Se esta enviando la respuesta al microServicio....");

        try {
            Thread.sleep(10000);
            System.out.println("Enviado al micro... ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return filmWithHeader;
    }

    private ResponseEntity<NetflixResponse> sampleProcessingNetflix(NetflixResponse netflixResponse) {
        System.out.println("Se esta enviando la respuesta a netflix....");

        try {
            Thread.sleep(20000);
            System.out.println("Enviado a netflix");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(netflixResponse);
    }

    /*
    Customizable method to check if a JSONObject is valid
     */
    private boolean checkEverything(JSONObject jsonObject) {
        return jsonObject.has("filmName") && jsonObject.has("director") && jsonObject.has("year");
    }

    /*
    Build a promise
     */


}
