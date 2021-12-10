package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.*;

public class Attempt {
    private String guess;
    private List<Feedback> feedback;

    public String getGuess() {
        return this.guess;
    }

    public List<Feedback> getFeedback() {
        return this.feedback;
    }

    public Attempt() {}
    public Attempt(String guess, List<Feedback> feedback) {
        this.guess = guess;
        this.feedback = feedback;
    }

    public static Attempt guess(String wordToGuess, String guess) {
        int wordToGuessLength = wordToGuess.length();
        if (wordToGuessLength != guess.length()) {
            return new Attempt(guess, Collections.nCopies(wordToGuessLength, INVALID));
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

        return new Attempt(guess, feedback);
    }

    public boolean isGuessCorrect() {
        return this.feedback.stream().allMatch(CORRECT::equals);
    }
}
