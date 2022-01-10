package nl.hu.cisq1.lingo.trainer.presentation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import nl.hu.cisq1.lingo.trainer.domain.Generated;

class GuessRequest {
    @NotNull
    @Size(min = 5, max = 7, message = "Guess should be between 5 and 7 letters.")
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String guess;

    @Generated
    public GuessRequest() {
        //* Empty constructor for serialization
    }

    @Generated
    public String getGuess() {
        return guess;
    }

    @Generated
    public void setGuess(String guess) {
        this.guess = guess;
    }
}
