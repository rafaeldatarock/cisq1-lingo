package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameNotFound extends RuntimeException {
    public GameNotFound(Long id) {
        super(String.format("Game with id %d not found", id));
    }
}
