package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotStartedWith5LetterWord;
import nl.hu.cisq1.lingo.trainer.domain.exception.MoveNotAllowed;

@Entity
public class Game {
    //* Attributes
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer score = 0;

    @Column
    private GameStatus status = GameStatus.WAITING;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    private Game() {}
    
    @Generated
    public Long getId() {
        return id;
    }

    @Generated
    public Integer getScore() {
        return score;
    }

    @Generated
    public GameStatus getStatus() {
        return status;
    }

    @Generated
    public List<Round> getRounds() {
        return rounds;
    }

    private Round getCurrentRound() {
        return this.rounds.get(rounds.size() - 1);
    }

    //* Logic
    public static Game start(String word) throws GameNotStartedWith5LetterWord {
        if (word.length() != 5) throw new GameNotStartedWith5LetterWord();
        
        Game game = new Game();
        game.startNewRound(word);
        
        return game;
    }

    public void startNewRound(String word) {
        if (this.status != GameStatus.WAITING) {
            throw MoveNotAllowed.cannotStartNewRoundUnlessWaiting();
        }

        if (!rounds.isEmpty()) {
            int prevWordLength = getCurrentRound().giveWordToGuessLength();
            if (prevWordLength != 7 && prevWordLength + 1 != word.length()) {
                throw MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength();
            }
            if (prevWordLength == 7 && word.length() != 5) {
                throw MoveNotAllowed.cannotStartNewRoundWithIncorrectWordLength();
            }
        }

        this.status = GameStatus.PLAYING;
        rounds.add(Round.start(word));
    }

    public void attemptGuess(String guess) {
        if (this.status != GameStatus.PLAYING) throw MoveNotAllowed.cannotGuessUnlessPlaying();

        Round currentRound = getCurrentRound();
        this.status = currentRound.attemptGuess(guess);

        //* if status is set to 'WAITING' after attempt, that means that the word has been guessed correctly,
        //* so the score for that round can be calculated
        if (this.status == GameStatus.WAITING) this.score += currentRound.calculateScore();
    }

    public GameProgress giveProgress() {
        Round currentRound = getCurrentRound();
        return new GameProgress(this.id, this.score, this.status, currentRound.giveHint(), currentRound.giveLatestFeedback());
    }

    public void stop() {
        if (this.status == GameStatus.GAMEOVER || this.status == GameStatus.STOPPED) {
            throw MoveNotAllowed.cannotStopIfAlreadyGameoverOrStopped();
        }

        this.status = GameStatus.STOPPED;
    }
}
