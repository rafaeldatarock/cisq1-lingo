package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameNotStartedWith5LetterWord extends RuntimeException {
    public GameNotStartedWith5LetterWord() {
        super("Game must be started with a 5 letter word.");
    }
}
