package nl.hu.cisq1.lingo.words.domain;

import java.util.Collections;
import java.util.List;

import nl.hu.cisq1.lingo.words.domain.exception.GuessLengthDoesNotMatchWordLengthException;

import static nl.hu.cisq1.lingo.words.domain.Feedback.*;

public class Attempt {
    Attempt(){}

    public static List<Feedback> guess(Word wordToGuess, String guess) throws GuessLengthDoesNotMatchWordLengthException {
        Integer wordToGuessLength = wordToGuess.getLength();
        if (wordToGuessLength != guess.length()) {
            throw new GuessLengthDoesNotMatchWordLengthException(wordToGuessLength);
        }

        if (wordToGuess.getValue().equals(guess)) {
            return Collections.nCopies(wordToGuessLength, CORRECT);
        }

        return Collections.nCopies(wordToGuessLength, ABSENT);
    }
}
