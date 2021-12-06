package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundTest {

    public static Stream<Arguments> hintBeforeAfterExamples() {
        return Stream.of(
            Arguments.of("koekje", new String[]{"k", ".", ".", ".", ".", "."}, List.of(ABSENT, ABSENT, ABSENT, ABSENT, PRESENT, ABSENT),    new String[]{"k", ".", ".", ".", ".", "."}),
            Arguments.of("koekje", new String[]{"k", ".", ".", ".", ".", "."}, List.of(CORRECT, CORRECT, CORRECT, ABSENT, PRESENT, ABSENT), new String[]{"k", "o", "e", ".", ".", "."}),
            Arguments.of("koekje", new String[]{"k", "o", "e", ".", ".", "."}, List.of(ABSENT, ABSENT, ABSENT, CORRECT, CORRECT, CORRECT),  new String[]{"k", "o", "e", "k", "j", "e"}),
            Arguments.of("bapao",  new String[]{"k", ".", ".", ".", "."},      List.of(CORRECT, CORRECT, PRESENT, ABSENT, ABSENT),          new String[]{"b", "a", ".", ".", "."}),
            Arguments.of("babbel", new String[]{"b", ".", ".", ".", ".", "."}, List.of(ABSENT, ABSENT, ABSENT, CORRECT, CORRECT, CORRECT),  new String[]{"b", ".", ".", "b", "e", "l"})
        );
    }

    @ParameterizedTest
    @MethodSource("")
    @DisplayName("Hint should update correctly after guessing")
    void updateHintAfterGuess(String word, String[] currentHint, List<Feedback> feedbackAfterGuess, String[] nextHint) {
        Round round = new Round(word, currentHint);
        String[] actual = round.updateHint(feedbackAfterGuess);
        String[] expected = nextHint;
        assertEquals(expected, actual);
    }
}
