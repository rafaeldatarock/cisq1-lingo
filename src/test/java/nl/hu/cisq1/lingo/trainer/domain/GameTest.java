package nl.hu.cisq1.lingo.trainer.domain;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.ABSENT;
import static nl.hu.cisq1.lingo.trainer.domain.Feedback.CORRECT;
import static org.junit.jupiter.api.Assertions.assertAll;
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

import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotStartedWith5LetterWord;
import nl.hu.cisq1.lingo.trainer.domain.exception.MoveNotAllowed;

class GameTest {

    @Test
    @DisplayName("Game should start using a 5 letter word, score 0, status playing, first letter hint and empty feedback")
    void startGame() {
        // When using the start() named constructor, a new Round should automatically be started, thus GameStatus should be PLAYING
        Game game = Game.start("baard");
        var expected = new GameProgress(null, 0, GameStatus.PLAYING, "[b, ., ., ., .]", new ArrayList<Feedback>());
        var actual = game.giveProgress();
        assertEquals(expected, actual);
    }

    @Test
    void gameProgress() {
        Game game = Game.start("baard");
        game.attemptGuess("board");
        var expected = new GameProgress(null, 0, GameStatus.PLAYING, "[b, ., a, r, d]", List.of(CORRECT, ABSENT, CORRECT, CORRECT, CORRECT));
        var actual = game.giveProgress();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Game should not start if word length is not 5")
    void dontStartGame() {
        assertThrows(GameNotStartedWith5LetterWord.class, () -> Game.start("baardje"));
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
    @DisplayName("Should not be able to attempt guess when status is WAITING")
    void cannotGuessWhenStatusWaiting() {
        Game game = Game.start("baard");
        game.attemptGuess("baard");
        assertThrows(MoveNotAllowed.cannotGuessUnlessPlaying().getClass(), () -> game.attemptGuess("gokje"));
    }

    @Test
    @DisplayName("Should not be able to attempt guess when status is GAMEOVER")
    void cannotGuessWhenStatusGameover() {
        Game game = Game.start("baard");
        game.attemptGuess("board");
        game.attemptGuess("board");
        game.attemptGuess("board");
        game.attemptGuess("board");
        game.attemptGuess("board");
        assertThrows(MoveNotAllowed.cannotGuessUnlessPlaying().getClass(), () -> game.attemptGuess("baard"));
    }

    @Test
    @DisplayName("Should not be able to attempt guess when status is STOPPED")
    void cannotGuessWhenStatusStopped() {
        Game game = Game.start("baard");
        game.stop();
        assertThrows(MoveNotAllowed.cannotGuessUnlessPlaying().getClass(), () -> game.attemptGuess("baard"));
    }

    @Test
    @DisplayName("Should not be able to stop game with status STOPPED")
    void cannotStopStoppedGame() {
        Game game = Game.start("baard");
        game.stop();

        assertThrows(MoveNotAllowed.cannotStopIfAlreadyGameoverOrStopped().getClass(), () -> game.stop());
    }

    @Test
    @DisplayName("Should not be able to stop game with status GAMEOVER")
    void cannotStopLostGame() {
        Game game = Game.start("baard");
        game.attemptGuess("board");
        game.attemptGuess("board");
        game.attemptGuess("board");
        game.attemptGuess("board");
        game.attemptGuess("board");

        assertThrows(MoveNotAllowed.cannotStopIfAlreadyGameoverOrStopped().getClass(), () -> game.stop());
    }

    @Test
    @DisplayName("Should not be able to start new round when status is PLAYING")
    void cannotStartWhenPlaying() {
        Game game = Game.start("woord");
        assertThrows(MoveNotAllowed.cannotStartNewRoundUnlessWaiting().getClass(), () -> game.startNewRound("woord"));
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
        assertThrows(MoveNotAllowed.cannotStartNewRoundUnlessWaiting().getClass(), () -> game.startNewRound("woord"));
    }

    @Test
    void cannotStartNewRoundWithIncorrectWordLength() {
        Game game = Game.start("woord");
        game.attemptGuess("woord");
        assertAll(
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("woord")),
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("woordje"))
        );

        game.startNewRound("worden");
        game.attemptGuess("worden");
        assertAll(
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("worden")),
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("woord"))
        );

        game.startNewRound("woordje");
        game.attemptGuess("woordje");
        assertAll(
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("woordje")),
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("worden"))
        );

        game.startNewRound("woord");
        game.attemptGuess("woord");
        assertAll(
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("woord")),
            () -> assertThrows(MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength().getClass(), () -> game.startNewRound("woordje"))
        );
    }

    public static Stream<Arguments> scoreExamples() {
        return Stream.of(
            Arguments.of(25, 0),
            Arguments.of(20, 1),
            Arguments.of(15, 2),
            Arguments.of(10, 3),
            Arguments.of(5, 4)
        );
    }

    @ParameterizedTest
    @MethodSource("scoreExamples")
    void scoreCalculation(int score, int wrongGuesses) {
        Game game = Game.start("woord");
        for (int i = wrongGuesses; i > 0; i--) {
            game.attemptGuess("foutje");
        }
        game.attemptGuess("woord");
        var expected = new GameProgress(null, score, GameStatus.WAITING, "[w, o, o, r, d]", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        var actual = game.giveProgress();
        assertEquals(expected, actual);
    }

    @Test
    void sumScore() {
        Game game = Game.start("woord");
        game.attemptGuess("woord");

        game.startNewRound("worden");
        game.attemptGuess("worden");

        var expected = new GameProgress(null, 50, GameStatus.WAITING, "[w, o, r, d, e, n]", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT, CORRECT));
        var actual = game.giveProgress();
        assertEquals(expected, actual);
    }
}
