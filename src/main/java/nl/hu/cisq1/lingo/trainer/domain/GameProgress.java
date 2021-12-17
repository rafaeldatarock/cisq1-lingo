package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class GameProgress {
    private Long id;
    private int score;
    private GameStatus status;
    private String hint;
    private List<Feedback> feedback;

    public GameProgress(
        Long id,
        int score,
        GameStatus status,
        String hint,
        List<Feedback> feedback
    ) {
        this.id = id;
        this.score = score;
        this.status = status;
        this.hint = hint;
        this. feedback = feedback;
    }

    @Generated
    public Long getId() {
        return this.id;
    }

    @Generated
    public int getScore() {
        return this.score;
    }

    @Generated
    public String getStatus() {
        return this.status.toString();
    }

    @Generated
    public String getHint() {
        return this.hint;
    }

    @Generated
    public List<Feedback> getFeedback() {
        return this.feedback;
    }

    @Override
    @Generated
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameProgress other = (GameProgress) obj;
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
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
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + score;
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }
}
