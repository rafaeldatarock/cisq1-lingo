package nl.hu.cisq1.lingo.words.domain;

import java.util.ArrayList;
import java.util.Arrays;
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

        List<String> wordLetters = new ArrayList<>(List.of(wordToGuess.getValue().split("")));
        String[] guessLetters = guess.split("");
        List<Feedback> feedback = new ArrayList<>(Collections.nCopies(wordToGuessLength, ABSENT));

        for (int i = 0; i < guessLetters.length; i++) {
            if (wordLetters.get(i).equals(guessLetters[i])) {
                feedback.set(i, CORRECT);
                wordLetters.set(i, " ");
            }
        }

        for (int i = 0; i < guessLetters.length; i++) {
            if (feedback.get(i) == CORRECT) {
                continue;
            }
            
            if (wordLetters.contains(guessLetters[i])) {
                feedback.set(i, PRESENT);
                wordLetters.set(wordLetters.indexOf(guessLetters[i]), " ");
            }
        }

        return feedback;
    }
}
