package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundTest {

    public static Stream<Arguments> initialHintExamples() {
        return Stream.of(
            Arguments.of("bapao", new String[]{"b", ".", ".", ".", "."}),
            Arguments.of("koekje", new String[]{"k", ".", ".", ".", ".", "."}),
            Arguments.of("brownie", new String[]{"b", ".", ".", ".", ".", ".", "."})
        );
    }

    @ParameterizedTest
    @MethodSource("initialHintExamples")
    @DisplayName("Initial hint should be based on word to guess")
    void initialHintBasedOnWord(String word, String[] hint) {
        Round round = Round.start(word);
        String actual = round.giveHint();
        String expected = Arrays.toString(hint);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> hintBeforeAfterExamples() {
        return Stream.of(
            Arguments.of("koekje", new String[]{"k", ".", ".", ".", ".", "."}, "badjas",    new String[]{"k", ".", ".", ".", ".", "."}),
            Arguments.of("koekje", new String[]{"k", ".", ".", ".", ".", "."}, "koebel",    new String[]{"k", "o", "e", ".", ".", "."}),
            Arguments.of("koekje", new String[]{"k", "o", "e", ".", ".", "."}, "bankje",    new String[]{"k", "o", "e", "k", "j", "e"}),
            Arguments.of("bapao",  new String[]{"k", ".", ".", ".", "."},      "baken",     new String[]{"b", "a", ".", ".", "."}),
            Arguments.of("babbel", new String[]{"b", ".", ".", ".", ".", "."}, "oorbel",    new String[]{"b", ".", ".", "b", "e", "l"})
        );
    }

    @ParameterizedTest
    @MethodSource("hintBeforeAfterExamples")
    @DisplayName("Hint should update correctly based on feedback")
    void updateHintBasedOnFeedback(String word, String[] currentHint, String guess, String[] nextHint) {
        Round round = new Round(word, currentHint);
        round.attemptGuess(guess);
        String actual = round.giveHint();
        String expected = Arrays.toString(nextHint);
        assertEquals(expected, actual);
    }

    @Test
    void roundIsWon() {
        Round round = Round.start("baard");
        assertEquals(GameStatus.WAITING, round.attemptGuess("baard"));
    }

    @Test
    void roundIsPlaying() {
        Round round = Round.start("baard");
        assertEquals(GameStatus.PLAYING, round.attemptGuess("board"));
    }

    @Test
    void roundIsLost() {
        Round round = Round.start("baard");
        round.attemptGuess("board"); // 1
        round.attemptGuess("board"); // 2
        round.attemptGuess("boardje"); // 3
        round.attemptGuess("board"); // 4
        assertEquals(GameStatus.GAMEOVER, round.attemptGuess("board")); // 5
    }
}
