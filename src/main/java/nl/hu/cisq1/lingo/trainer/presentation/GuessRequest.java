package nl.hu.cisq1.lingo.trainer.presentation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

class GuessRequest {
    @NotNull
    @Size(min = 5, max = 7, message = "Guess should be between 5 and 7 letters.")
    private String guess;

    public GuessRequest() {
        //* Empty constructor for serialization
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
