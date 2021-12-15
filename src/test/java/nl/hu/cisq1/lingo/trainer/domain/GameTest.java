package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotStartedWith5LetterWordException;
import nl.hu.cisq1.lingo.trainer.domain.exception.MoveNotAllowed;

class GameTest {

    @Test
    @DisplayName("Game should start using a 5 letter word, score 0, status playing, first letter hint and empty feedback")
    void startGame() {
        // When using the start() named constructor, a new Round should automatically be started, thus GameStatus should be PLAYING
        Game game = Game.start("baard");
        var expected = new GameProgressDTO(0, GameStatus.PLAYING, "[b, ., ., ., .]", new ArrayList<Feedback>());
        var actual = game.giveProgress();
        assertEquals(expected, actual);
    }

    @Test
    void gameProgress() {
        Game game = Game.start("baard");
        game.attemptGuess("board");
        var expected = new GameProgressDTO(0, GameStatus.PLAYING, "[b, ., a, r, d]", List.of(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT));
        var actual = game.giveProgress();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Game should not start if word length is not 5")
    void dontStartGame() {
        assertThrows(GameNotStartedWith5LetterWordException.class, () -> Game.start("baardje"));
    }

    public static Stream<Arguments> gameStatusExamples() {
        return Stream.of(
            Arguments.of("baard", "baard", GameStatus.WAITING),
            Arguments.of("baard", "board", GameStatus.PLAYING)
        );
    }
    
    @ParameterizedTest
    @MethodSource("gameStatusExamples")
    @DisplayName("Game status after first guess")
    void gameStatus(String word, String guess, GameStatus status) {
        Game game = Game.start(word);
        game.attemptGuess(guess);
        assertEquals(status, game.getStatus());
    }

    @Test
    @DisplayName("Should not be able to start new round when status is PLAYING")
    void cannotStartWhenPlaying() {
        Game game = Game.start("woord");
        assertThrows(MoveNotAllowed.cannotStartNewRound().getClass(), () -> game.startNewRound("woord"));
    }

    @Test
    @DisplayName("Should not be able to start new round when status is GAMEOVER")
    void cannotStartWhenGameover() {
        Game game = Game.start("woord");
        game.attemptGuess("foutj");
        game.attemptGuess("foutj");
        game.attemptGuess("foutj");
        game.attemptGuess("foutj");
        game.attemptGuess("foutj");
        assertThrows(MoveNotAllowed.cannotStartNewRound().getClass(), () -> game.startNewRound("woord"));
    }

    // TODO: test and implement score calculation!
}
