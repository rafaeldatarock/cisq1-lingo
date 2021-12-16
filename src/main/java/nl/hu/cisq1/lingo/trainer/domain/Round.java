package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Round {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String wordToGuess;

    @Column
    private String[] hint;
    
    @OneToMany
    private List<Attempt> attempts;
    
    public Round() {}
    public Round(String wordToGuess, String[] hint) {
        this.wordToGuess = wordToGuess;
        this.hint = hint;
        this.attempts = new ArrayList<>();
    }
    public static Round start(String word) {
        String[] hint = Collections.nCopies(word.length(), ".").toArray(new String[0]);
        hint[0] = word.split("")[0];
        return new Round(word, hint);
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public String giveHint() {
        return Arrays.toString(hint);
    }

    public List<Feedback> getLatestFeedback() {
        if (attempts.isEmpty()) return new ArrayList<>();

        // else take last item from list and get feedback
        return this.attempts.get(attempts.size() - 1).getFeedback();
    }

    public GameStatus attemptGuess(String guess) {
        Attempt attempt = Attempt.guess(this.wordToGuess, guess);
        attempts.add(attempt);

        updateHint(attempt.getFeedback());

        if (attempt.isGuessCorrect()) {
            return GameStatus.WAITING;
        } else if (attempts.size() < 5) {
            return GameStatus.PLAYING;
        } else {
            return GameStatus.GAMEOVER;
        }
    }

    private void updateHint(List<Feedback> feedback) {
        String[] splitWord = wordToGuess.split("");

        for (int i = 0; i < feedback.size(); i++) {
            if (feedback.get(i) == Feedback.CORRECT) {
                this.hint[i] = splitWord[i];
            }
        }
    }

    public int calculateScore() {
        return 5 * (5 - attempts.size()) + 5;
    }
}
