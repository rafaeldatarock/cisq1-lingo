package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class GameProgressDTO {
    private int score;
    private GameStatus status;
    private String hint;
    private List<Feedback> feedback;

    public GameProgressDTO(
        int score,
        GameStatus status,
        String hint,
        List<Feedback> feedback
    ) {
        this.score = score;
        this.status = status;
        this.hint = hint;
        this. feedback = feedback;
    }

    // * Getters for serializing as JSON?
    // public int getScore() {
    //     return this.score;
    // }

    // public String getStatus() {
    //     return this.status.toString();
    // }

    // public String getHint() {
    //     return this.hint;
    // }

    // public List<Feedback> getFeedback() {
    //     return this.feedback;
    // }

    @Override
    @Generated
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameProgressDTO other = (GameProgressDTO) obj;
        if (feedback == null) {
            if (other.feedback != null)
                return false;
        } else if (!feedback.equals(other.feedback))
            return false;
        if (hint == null) {
            if (other.hint != null)
                return false;
        } else if (!hint.equals(other.hint))
            return false;
        if (score != other.score)
            return false;
        if (status != other.status)
            return false;
        return true;
    }

    @Override
    @Generated
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((feedback == null) ? 0 : feedback.hashCode());
        result = prime * result + ((hint == null) ? 0 : hint.hashCode());
        result = prime * result + score;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }
}
