package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotStartedWith5LetterWordException;

public class Game {
    private Integer score = 0;
    private GameStatus status = GameStatus.WAITING;
    private List<Round> rounds = new ArrayList<>();

    public Integer getScore() {
        return this.score;
    }

    public GameStatus getStatus() {
        return this.status;
    }

    private Round getCurrentRound() {
        // Pick last item from List
        return this.rounds.get(rounds.size() - 1);
    }

    private Game() {}

    public static Game start(String word) throws GameNotStartedWith5LetterWordException {
        if (word.length() != 5) {
            throw new GameNotStartedWith5LetterWordException();
        }

        Game game = new Game();
        game.startNewRound(word);

        return game;
    }

    public void startNewRound(String word) {
        if (!status.equals(GameStatus.WAITING)) {
            // throw new MoveNotAllowedException();
        }

        status = GameStatus.PLAYING;
        rounds.add(Round.start(word));
    }

    // TODO: public attemptGuess(String guess) {
        
    // }

    // TODO: getProgress()
    // Returns hint, score

}
