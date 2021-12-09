package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Integer score;
    private List<Round> rounds;

    public Integer getScore() {
        return this.score;
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

    public static Game start() {
        List<Round> rounds = new ArrayList<>();
        rounds.add(Round.start(""));
        return new Game(0, rounds);
    }

}
