package nl.hu.cisq1.lingo.trainer.presentation;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Random;

import com.jayway.jsonpath.JsonPath;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import nl.hu.cisq1.lingo.CiTestConfiguration;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
@Tag("integration")
class TrainerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("starts game and returns progress")
    void startGame() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
            .post("/trainer/game");      

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.score", is(0)))
            .andExpect(jsonPath("$.status", is("PLAYING")))
            .andExpect(jsonPath("$.hint", is(notNullValue())))
            .andExpect(jsonPath("$.feedback").isArray())
            .andExpect(jsonPath("$.feedback", hasSize(0)));
    }

    @Test
    @DisplayName("starts game and performs guess")
    void attemptGuess() throws Exception {
        RequestBuilder arrangeRequest = MockMvcRequestBuilders.post("/trainer/game");
        String arrangeResult = mockMvc.perform(arrangeRequest).andReturn().getResponse().getContentAsString();
        int id = JsonPath.read(arrangeResult, "$.id");

        RequestBuilder request = MockMvcRequestBuilders
            .post(String.format("/trainer/game/%d/guess", id))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"guess\":\"baard\"}");      

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(id)))
            .andExpect(jsonPath("$.score", is(lessThanOrEqualTo(25))))
            .andExpect(jsonPath("$.status", anyOf(is("PLAYING"), is("WAITING"))))
            .andExpect(jsonPath("$.hint").isString())
            .andExpect(jsonPath("$.feedback").isArray())
            .andExpect(jsonPath("$.feedback", hasSize(5)));
    }

    @Test
    @DisplayName("tries to guess on unexisting game")
    void shouldNotAttemptGuess() throws Exception {
        //* In theory this test is not deterministic when tested to an actual database, 
        //* but it is deterministic in the CI pipeline.
        //* The probability of this test failing in a development environment 
        //* are about 1 : (100 million - amount of games you plaid)
        //* BUT STILL NOT DETERMINISTIC hahaha
        int randomId = new Random().nextInt((999999999 - 100000000) + 1) + 100000000;

        RequestBuilder request = MockMvcRequestBuilders
            .post(String.format("/trainer/game/%d/guess", randomId))
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"guess\":\"baard\"}");      

        mockMvc.perform(request)
            .andExpect(status().isNotFound());
    }
}
