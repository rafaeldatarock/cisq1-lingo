package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import nl.hu.cisq1.lingo.trainer.domain.exception.GuessLengthDoesNotMatchWordLengthException;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AttemptTest {

    public static Stream<Arguments> guessExamples() {
        return Stream.of(
            Arguments.of("braam", "braam", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT)),
            Arguments.of("aangaan", "koelrib", List.of(ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT, ABSENT)),
            Arguments.of("braam", "braad", List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT)),
            Arguments.of("braam", "vroom", List.of(ABSENT, CORRECT, ABSENT, ABSENT, CORRECT)),
            Arguments.of("braam", "baard", List.of(CORRECT, PRESENT, CORRECT, PRESENT, ABSENT)),
            Arguments.of("baard", "braam", List.of(CORRECT, PRESENT, CORRECT, PRESENT, ABSENT)),
            Arguments.of("baard", "bedde", List.of(CORRECT, ABSENT, PRESENT, ABSENT, ABSENT)),
            Arguments.of("baard", "bonje", List.of(CORRECT, ABSENT, ABSENT, ABSENT, ABSENT)),
            Arguments.of("baard", "barst", List.of(CORRECT, CORRECT, PRESENT, ABSENT, ABSENT))
        );
    }
    
    @ParameterizedTest
    @MethodSource("guessExamples")
    @DisplayName("Guessing a word")
    void attemptGuess(String word, String guess, List<Feedback> feedback) throws Exception {
        List<Feedback> actual = Attempt.guess(word, guess).getFeedback();
        List<Feedback> expected = feedback;
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> incorrectLengthExamples() {
        return Stream.of(
            Arguments.of("braam", "bramen"),
            Arguments.of("bramen", "braam"),
            Arguments.of("aalmoes", "braam"),
            Arguments.of("braam", "knor"),
            Arguments.of("aalmoes", "confisceren")
        );
    }

    @ParameterizedTest
    @MethodSource("incorrectLengthExamples")
    @DisplayName("Incorrect word length throws exception")
    void attemptIncorrectWordLength(String word, String guess) {
        assertThrows(
            GuessLengthDoesNotMatchWordLengthException.class, 
            () -> { Attempt.guess(word, guess); } 
        );
    }

    @Test
    @DisplayName("Word is guessed if all letters are correct")
    void wordIsGuessed() {
        
    }
}
