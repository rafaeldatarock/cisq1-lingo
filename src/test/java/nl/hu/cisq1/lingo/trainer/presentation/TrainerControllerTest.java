package nl.hu.cisq1.lingo.trainer.presentation;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;

@WebMvcTest(controllers = TrainerController.class)
class TrainerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainerService trainerService;

    @Test
    @DisplayName("Should return 200 and gameprogress in json after starting new round")
    void shouldStartNewRound() throws Exception {
        var given = new GameProgress(1L, 25, GameStatus.PLAYING, "[b, ., ., ., ., .]", new ArrayList<>());
        given(trainerService.newRound(anyLong())).willReturn(given);

        RequestBuilder request = MockMvcRequestBuilders
            .post("/trainer/game/{id}/round", 1);

        mockMvc.perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.score", is(25)))
            .andExpect(jsonPath("$.status", is("PLAYING")))
            .andExpect(jsonPath("$.hint", is("[b, ., ., ., ., .]")))
            .andExpect(jsonPath("$.feedback").isArray())
            .andExpect(jsonPath("$.feedback", hasSize(0)));
    }

    @Test
    @DisplayName("Should return HTTP 500 if any internal exception is thrown")
    void shouldThrowInternalServiceError() throws Exception {
        //* scenario where words package throws error
        given(trainerService.startGame()).willThrow(WordLengthNotSupportedException.class);

        RequestBuilder request = MockMvcRequestBuilders.post("/trainer/game");

        mockMvc.perform(request).andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Should return HTTP 404 if game is not found")
    void shouldNotFindGame() throws Exception {
        given(trainerService.newRound(anyLong())).willThrow(GameNotFound.class);
        given(trainerService.guessWord(anyLong(), anyString())).willThrow(GameNotFound.class);
        given(trainerService.showFullGame(anyLong())).willThrow(GameNotFound.class);
        given(trainerService.stopGame(anyLong())).willThrow(GameNotFound.class);

        RequestBuilder roundRequest = MockMvcRequestBuilders.post("/trainer/game/{id}/round", 1);
        RequestBuilder progressRequest = MockMvcRequestBuilders.get("/trainer/game/{id}", 1);
        RequestBuilder stopRequest = MockMvcRequestBuilders.patch("/trainer/game/{id}", 1);
        RequestBuilder guessRequest = MockMvcRequestBuilders
            .post("/trainer/game/{id}/guess", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"guess\":\"baard\"}");

        mockMvc.perform(roundRequest).andExpect(status().isNotFound())
            .andDo(result -> mockMvc.perform(guessRequest).andExpect(status().isNotFound()))
            .andDo(result -> mockMvc.perform(progressRequest).andExpect(status().isNotFound()))
            .andDo(result -> mockMvc.perform(stopRequest).andExpect(status().isNotFound()));
    }
}
