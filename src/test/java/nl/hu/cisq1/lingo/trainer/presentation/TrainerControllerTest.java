package nl.hu.cisq1.lingo.trainer.presentation;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;

class TrainerControllerTest {
    @Test
    void testGuessWord() {
        TrainerService service = mock(TrainerService.class);
        TrainerController controller = new TrainerController(service);

    }
}
