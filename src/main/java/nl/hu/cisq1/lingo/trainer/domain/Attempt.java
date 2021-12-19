package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static nl.hu.cisq1.lingo.trainer.domain.Feedback.*;

@Entity
public class Attempt {
    
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String guess;

    @ElementCollection(targetClass=Feedback.class)
    @CollectionTable(name="attempt_feedback")
    @Column
    @Enumerated(EnumType.STRING)
    private List<Feedback> feedback;
    
    private Attempt() {}
    private Attempt(String guess, List<Feedback> feedback) {
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

    public List<Feedback> getFeedback() {
        return this.feedback;
    }

    public boolean isGuessCorrect() {
        return this.feedback.stream().allMatch(CORRECT::equals);
    }
}
