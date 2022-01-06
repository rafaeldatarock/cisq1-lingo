package nl.hu.cisq1.lingo.trainer.presentation;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;

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
            .post("/trainer/game/{id}/guess", id)
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
    @DisplayName("starts game and shows progress including history")
    void showProgress() throws Exception {
        RequestBuilder arrangeOne = MockMvcRequestBuilders.post("/trainer/game");
        String arrangeResult = mockMvc.perform(arrangeOne).andReturn().getResponse().getContentAsString();
        int id = JsonPath.read(arrangeResult, "$.id");

        RequestBuilder arrangeTwo = MockMvcRequestBuilders
            .post("/trainer/game/{id}/guess", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"guess\":\"baard\"}");  
        mockMvc.perform(arrangeTwo);

        RequestBuilder act = MockMvcRequestBuilders
            .get("/trainer/game/{id}", id);
        mockMvc.perform(act)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.score", is(0)))
            .andExpect(jsonPath("$.status", is("PLAYING")))
            .andExpect(jsonPath("$.rounds").isArray())
            .andExpect(jsonPath("$.rounds", hasSize(1)))
            .andExpect(jsonPath("$.rounds[0].lastHint").isString())
            .andExpect(jsonPath("$.rounds[0].attempts").isArray())
            .andExpect(jsonPath("$.rounds[0].attempts", hasSize(1)))
            .andExpect(jsonPath("$.rounds[0].attempts[0].guess", is("baard")))
            .andExpect(jsonPath("$.rounds[0].attempts[0].feedback").isArray())
            .andExpect(jsonPath("$.rounds[0].attempts[0].feedback", hasSize(5)));
    }

    @Test
    @DisplayName("starts game and stops it")
    void stopGame() throws Exception {
        RequestBuilder arrangeRequest = MockMvcRequestBuilders.post("/trainer/game");
        String arrangeResult = mockMvc.perform(arrangeRequest).andReturn().getResponse().getContentAsString();
        int id = JsonPath.read(arrangeResult, "$.id");

        RequestBuilder request = MockMvcRequestBuilders
            .patch("/trainer/game/{id}", id);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.score", is(0)))
            .andExpect(jsonPath("$.status", is("STOPPED")))
            .andExpect(jsonPath("$.hint", is(notNullValue())))
            .andExpect(jsonPath("$.feedback").isArray())
            .andExpect(jsonPath("$.feedback", hasSize(0)));
    }

    @Test
    @DisplayName("Should not start new round when a round is active")
    void shouldNotStartNewRound() throws Exception {
        RequestBuilder arrangeRequest = MockMvcRequestBuilders.post("/trainer/game");
        String arrangeResult = mockMvc.perform(arrangeRequest).andReturn().getResponse().getContentAsString();
        int id = JsonPath.read(arrangeResult, "$.id");

        RequestBuilder request = MockMvcRequestBuilders
            .post("/trainer/game/{id}/round", id);

        mockMvc.perform(request)
            .andExpect(status().isBadRequest());
    }
}
