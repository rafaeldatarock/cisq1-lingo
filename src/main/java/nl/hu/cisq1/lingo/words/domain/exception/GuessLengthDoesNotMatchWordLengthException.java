package nl.hu.cisq1.lingo.words.domain.exception;

public class GuessLengthDoesNotMatchWordLengthException extends RuntimeException {
    public GuessLengthDoesNotMatchWordLengthException(Integer correctLength) {
        super(String.format("Guess does not match needed length of %d characters", correctLength));
    }
}
