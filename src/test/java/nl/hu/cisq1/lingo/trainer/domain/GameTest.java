package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotStartedWith5LetterWordException;

class GameTest {

    @Test
    @DisplayName("Game should start using a 5 letter word")
    void startGame() {
        Game game = Game.start("baard");
        assertAll("Score should be 0 and word to guess should be a 5 letter word",
            () -> assertEquals(0, game.getScore()),
            () -> assertEquals(5, game.getCurrentRound().getWordToGuess().length())
        );
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
}
