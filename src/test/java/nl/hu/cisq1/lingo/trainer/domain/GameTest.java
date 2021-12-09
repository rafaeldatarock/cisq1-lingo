package nl.hu.cisq1.lingo.trainer.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class GameTest {

    @Test
    @DisplayName("Starting a game")
    void startGame() {
        Game game = Game.start();
        assertAll("Score should be 0 and word to guess should be a 5 letter word",
            () -> assertEquals(0, game.getScore()),
            () -> assertEquals(5, game.getCurrentRound().getWordToGuess().length())
        );
    }

    // public static Stream<Arguments> gameStatusExamples() {
    //     return Stream.of(
    //         Arguments.of("baard", "baard", GameStatus.WAITING),
    //         Arguments.of("baard", "baard", GameStatus.PLAYING)
    //     );
    // }
    
    // @ParameterizedTest
    // @MethodSource("gameStatusExamples")
    // @DisplayName("Game status after first guess")
    // void gameStatus() {
    //     Game game = new Game()
    // }
}
