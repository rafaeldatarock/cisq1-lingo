package nl.hu.cisq1.lingo.trainer.application;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.ABSENT;
import static nl.hu.cisq1.lingo.trainer.domain.Feedback.CORRECT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFound;
import nl.hu.cisq1.lingo.words.application.WordService;

public class GameServiceTest {
    @Test
    void testStartGame() {
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        WordService wordService = mock(WordService.class);
        GameService service = new GameService(gameRepository, wordService);
        when(wordService.provideRandomWord(5)).thenReturn("baard");
        when(gameRepository.save(any(Game.class)))
            .thenAnswer(i -> {
                //* This takes the input argument for save() & sets the id attribute to 1L
                ReflectionTestUtils.setField((Game) i.getArgument(0), "id", 1L);
                return null;
            });

        var expected = new GameProgress(1L, 0, GameStatus.PLAYING, "[b, ., ., ., .]", new ArrayList<>());
        var actual = service.startGame();

        assertEquals(expected, actual);
    }

    @Test
    void testGuessWord() {
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        WordService wordService = mock(WordService.class);
        GameService service = new GameService(gameRepository, wordService);
        when(wordService.provideRandomWord(5)).thenReturn("baard");
        when(gameRepository.findById(anyLong())).thenReturn(Optional.of(Game.start("baard")));
        when(gameRepository.save(any(Game.class)))
            .thenAnswer(i -> {
                //* This takes the input argument for save() & sets the id attribute to 1L
                ReflectionTestUtils.setField((Game) i.getArgument(0), "id", 1L);
                return null;
            });
        
        service.startGame();
            
        var expected = new GameProgress(1L, 0, GameStatus.PLAYING, "[b, ., a, r, d]", List.of(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT));
        var actual = service.guessWord(1L, "board");

        assertEquals(expected, actual);
    }

    @Test
    void testNewRound() {
        GameService service = new GameService(mock(SpringGameRepository.class), mock(WordService.class));

    }

    @Test
    void testShowProgress() {
        GameService service = new GameService(mock(SpringGameRepository.class), mock(WordService.class));

    }


    @Test
    void testStopGame() {
        GameService service = new GameService(mock(SpringGameRepository.class), mock(WordService.class));

    }

    @Test
    void throwExceptionForMissingGame() {
        SpringGameRepository gameRepository = mock(SpringGameRepository.class);
        WordService wordService = mock(WordService.class);
        GameService service = new GameService(gameRepository, wordService);
        when(gameRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(GameNotFound.class, () -> service.showProgress(1L));
    }
}
