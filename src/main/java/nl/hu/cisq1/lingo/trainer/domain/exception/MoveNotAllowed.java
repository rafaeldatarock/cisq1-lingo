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

    public static MoveNotAllowed cannotStopIfAlreadyGameoverOrStopped() {
        return new MoveNotAllowed("Cannot manually stop game if status is 'GAMEOVER'");
    }    
    
    public static MoveNotAllowed cannotGuessUnlessPlaying() {
        return new MoveNotAllowed("Cannot guess word unless status is 'PLAYING'");
    }
}
