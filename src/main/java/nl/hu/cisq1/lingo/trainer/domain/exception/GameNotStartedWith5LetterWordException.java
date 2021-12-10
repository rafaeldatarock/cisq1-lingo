package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameNotStartedWith5LetterWordException extends RuntimeException {
    public GameNotStartedWith5LetterWordException() {
        super("Game must be started with a 5 letter word.");
    }
}
