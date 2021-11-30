package nl.hu.cisq1.lingo.words.domain;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.hu.cisq1.lingo.words.domain.exception.GuessLengthDoesNotMatchWordLengthException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static nl.hu.cisq1.lingo.words.domain.Feedback.*;

class AttemptTest {

    public static Stream<Arguments> guessExamples() {
        return Stream.of(
            Arguments.of(new Word("braam"), "braam", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),
            Arguments.of(new Word("aangaan"), "koelrib", List.of(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT)),
            Arguments.of(new Word("braam"), "braad", List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),
            Arguments.of(new Word("braam"), "vroom", List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT)),
            Arguments.of(new Word("braam"), "baard", List.of(CORRECT, PRESENT, CORRECT, PRESENT, ABSENT)),
            Arguments.of(new Word("baard"), "braam", List.of(CORRECT, PRESENT, CORRECT, PRESENT, ABSENT)),
            Arguments.of(new Word("baard"), "bedde", List.of(CORRECT, ABSENT, PRESENT, ABSENT, ABSENT)),
            Arguments.of(new Word("baard"), "bonje", List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
            Arguments.of(new Word("baard"), "barst", List.of(CORRECT, CORRECT, PRESENT, ABSENT, ABSENT))
        );
    }
    
    @ParameterizedTest
    @MethodSource("guessExamples")
    @DisplayName("Guessing a word")
    void attemptGuess(Word word, String guess, List<Feedback> feedback) throws Exception {
        List<Feedback> actual = Attempt.guess(word, guess);
        List<Feedback> expected = feedback;
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> incorrectLengthExamples() {
        return Stream.of(
            Arguments.of(new Word("braam"), "bramen"),
            Arguments.of(new Word("bramen"), "braam"),
            Arguments.of(new Word("aalmoes"), "braam"),
            Arguments.of(new Word("braam"), "knor"),
            Arguments.of(new Word("aalmoes"), "confisceren")
        );
    }

    @ParameterizedTest
    @MethodSource("incorrectLengthExamples")
    @DisplayName("Incorrect word length throws exception")
    void attemptIncorrectWordLength(Word word, String guess) {
        assertThrows(
            GuessLengthDoesNotMatchWordLengthException.class, 
            () -> { Attempt.guess(word, guess); } 
        );

    }
}
