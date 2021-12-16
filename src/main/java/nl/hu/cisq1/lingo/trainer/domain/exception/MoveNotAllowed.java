package nl.hu.cisq1.lingo.trainer.domain.exception;

public class MoveNotAllowed extends RuntimeException {
    public MoveNotAllowed(String m) {
        super(m);
    }

    public static MoveNotAllowed cannotStartNewRoundUnlessWaiting() {
        return new MoveNotAllowed("Cannot start new round unless game status is 'WAITING'");
    }

    public static MoveNotAllowed cannotStartNewRoundWithIncorrectWordLength() {
        return new MoveNotAllowed("Cannot start new round unless game status is 'WAITING'");
    }
}
