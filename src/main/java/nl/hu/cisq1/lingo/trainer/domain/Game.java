package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotStartedWith5LetterWordException;

public class Game {
    private Integer score;
    private GameStatus status;
    private List<Round> rounds;

    public Integer getScore() {
        return this.score;
    }

    public GameStatus getStatus() {
        return this.status;
    }

    public Round getCurrentRound() {
        // Pick last item from List
        return this.rounds.get(rounds.size() - 1);
    }

    private Game() {}
    private Game(Integer score, List<Round> rounds) {
        this.score = score;
        this.rounds = rounds;
    }

    public static Game start(String word) throws GameNotStartedWith5LetterWordException {
        if (word.length() != 5) {
            throw new GameNotStartedWith5LetterWordException();
        }

        List<Round> rounds = new ArrayList<>();
        rounds.add(Round.start(word));
        return new Game(0, rounds);
    }

    public attemptGuess(String guess) {
        
    }

}
