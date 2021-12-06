package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.*;
import nl.hu.cisq1.lingo.trainer.domain.exception.GuessLengthDoesNotMatchWordLengthException;

public class Attempt {
    private List<Feedback> feedback;

    public List<Feedback> getFeedback() {
        return this.feedback;
    }

    public Attempt() {}
    public Attempt(List<Feedback> feedback) {
        this.feedback = feedback;
    }

    public static Attempt guess(String wordToGuess, String guess) throws GuessLengthDoesNotMatchWordLengthException {
        Integer wordToGuessLength = wordToGuess.length();
        if (wordToGuessLength != guess.length()) {
            throw new GuessLengthDoesNotMatchWordLengthException(wordToGuessLength);
        }

        List<String> wordLetters = new ArrayList<>(List.of(wordToGuess.split("")));
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

        return new Attempt(feedback);
    }
}
