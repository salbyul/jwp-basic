package next.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Answer {

    private long answerId;
    private String writer;
    private String contents;
    private LocalDateTime createdDate;
    private long questionId;

    public Answer(final long answerId, final String writer, final String contents, final LocalDateTime createdDate, final long questionId) {
        this.answerId = answerId;
        this.writer = writer;
        this.contents = contents;
        this.createdDate = createdDate;
        this.questionId = questionId;
    }

    public long getAnswerId() {
        return answerId;
    }

    public String getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public long getQuestionId() {
        return questionId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Answer answer = (Answer) o;
        return getAnswerId() == answer.getAnswerId() && getQuestionId() == answer.getQuestionId() && Objects.equals(getWriter(), answer.getWriter()) && Objects.equals(getContents(), answer.getContents()) && Objects.equals(getCreatedDate(), answer.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnswerId(), getWriter(), getContents(), getCreatedDate(), getQuestionId());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", questionId=" + questionId +
                '}';
    }
}
