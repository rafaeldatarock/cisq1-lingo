package nl.hu.cisq1.lingo.trainer.presentation;

import java.util.List;
import nl.hu.cisq1.lingo.trainer.domain.Generated;


class FullGameResponse {
    Long id;
    int score;
    String status;
    List<Rounds> rounds;

    @Generated
    public FullGameResponse(Long id, int score, String status, List<Rounds> rounds) {
        this.id = id;
        this.score = score;
        this.status = status;
        this.rounds = rounds;
    }

    @Generated
    public Long getId() {
        return id;
    }

    @Generated
    public void setId(Long id) {
        this.id = id;
    }

    @Generated
    public int getScore() {
        return score;
    }

    @Generated
    public void setScore(int score) {
        this.score = score;
    }

    @Generated
    public String getStatus() {
        return status;
    }

    @Generated
    public void setStatus(String status) {
        this.status = status;
    }

    @Generated
    public List<Rounds> getRounds() {
        return rounds;
    }

    @Generated
    public void setRounds(List<Rounds> rounds) {
        this.rounds = rounds;
    }
}

class Rounds {
    String lastHint;
    List<Attempts> attempts;

    @Generated
    public Rounds(String lastHint, List<Attempts> attempts) {
        this.lastHint = lastHint;
        this.attempts = attempts;
    }

    @Generated
    public String getLastHint() {
        return lastHint;
    }

    @Generated
    public void setLastHint(String lastHint) {
        this.lastHint = lastHint;
    }

    @Generated
    public List<Attempts> getAttempts() {
        return attempts;
    }

    @Generated
    public void setAttempts(List<Attempts> attempts) {
        this.attempts = attempts;
    }
}

class Attempts {
    String guess;
    List<String> feedback;

    @Generated
    public Attempts(String guess, List<String> feedback) {
        this.guess = guess;
        this.feedback = feedback;
    }

    @Generated
    public String getGuess() {
        return guess;
    }

    @Generated
    public void setGuess(String guess) {
        this.guess = guess;
    }

    @Generated
    public List<String> getFeedback() {
        return feedback;
    }

    @Generated
    public void setFeedback(List<String> feedback) {
        this.feedback = feedback;
    }
}
