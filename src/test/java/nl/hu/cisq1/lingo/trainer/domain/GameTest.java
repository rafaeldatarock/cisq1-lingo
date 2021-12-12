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
import nl.hu.cisq1.lingo.trainer.domain.exception.MoveNotAllowed;

class GameTest {

    @Test
    @DisplayName("Game should start using a 5 letter word")
    void startGame() {
        // When using the start() named constructor, a new Round should automatically be started, thus GameStatus should be PLAYING
        Game game = Game.start("baard");
        //! change these seperate assertions for an assertion of the public getProgress() method
        assertAll("Score should be 0 and word to guess should be a 5 letter word",
            () -> assertEquals(0, game.getScore()),
            () -> assertEquals(GameStatus.PLAYING, game.getStatus())
            // getCurrentRound() is and should be private, so unable to call directly from testclass
            // () -> assertEquals(5, game.getCurrentRound().getWordToGuess().length())
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
        // game.attemptGuess(guess);
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
        // game.attemptGuess("foutj");
        // game.attemptGuess("foutj");
        // game.attemptGuess("foutj");
        // game.attemptGuess("foutj");
        // game.attemptGuess("foutj");
        assertThrows(MoveNotAllowed.cannotStartNewRound().getClass(), () -> {});//game.startNewRound("woord"));
    }
}
