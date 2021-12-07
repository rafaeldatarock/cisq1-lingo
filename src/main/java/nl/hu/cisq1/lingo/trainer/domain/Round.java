package nl.hu.cisq1.lingo.trainer.domain;

import java.util.Arrays;
import java.util.Collections;
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

    public static Round start(String word) {
        String[] hint = Collections.nCopies(word.length(), ".").toArray(new String[0]);
        hint[0] = word.split("")[0];
        return new Round(word, hint);
    }

    public void updateHint(List<Feedback> feedback) {
        for (int i = 0; i < feedback.size(); i++) {
            if (feedback.get(i) == Feedback.CORRECT) {
                this.hint[i] = wordToGuess.split("")[i];
            }
        }
    }
}
