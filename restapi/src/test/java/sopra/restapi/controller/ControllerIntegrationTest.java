package sopra.restapi.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void controllerIntegrationTestOk() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/imdb/filmsByYear").param("year", "1999")).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void controllerIntegrationTestWithoutYear() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/imdb/filmsByYear").param("year", "1221")).andExpect(status().isBadRequest()).andReturn();

    }

    @Test
    public void controllerIntegrationTestBadYear() {
        assertThrows(NumberFormatException.class, () ->
        mockMvc.perform(MockMvcRequestBuilders.get("/imdb/filmsByYear")
                        .param("year", "ERROR")));

    }

    @Test
    public void controllerIntegrationTestAsync() throws Exception {
        String film = "{\n" +
                "  \"filmName\": \"El silencio de los corderos\",\n" +
                "  \"director\": \"Jonathan Demme\",\n" +
                "  \"year\": \"1991\"\n" +
                "}";
      mockMvc.perform(
                MockMvcRequestBuilders.get("/imdb/filmWithHeader").param("film", film));


    }

}
