package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Arrays;
import java.util.List;

public class Round {
    private String wordToGuess;
    private String[] hint;

    public String getHint() {
        return Arrays.toString(hint);
    }

    public Round() {}
    public Round(String wordToGuess, String[] hint) {
        this.wordToGuess = wordToGuess;
        this.hint = hint;
    }

    //TODO: Static named constructor called 'start'

    public void updateHint(List<Feedback> feedback) {
        for (int i = 0; i < feedback.size(); i++) {
            if (feedback.get(i) == Feedback.CORRECT) {
                this.hint[i] = wordToGuess.split("")[i];
            }
        }
    }
}
