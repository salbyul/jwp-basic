package next.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Question {

    private long questionId;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdDate;
    private int countOfAnswer;

    public Question(final long questionId, final String writer, final String title, final String contents, final LocalDateTime createdDate, final int countOfAnswer) {
        this.questionId = questionId;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.countOfAnswer = countOfAnswer;
    }

    public long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Question question = (Question) o;
        return getQuestionId() == question.getQuestionId() && getCountOfAnswer() == question.getCountOfAnswer() && Objects.equals(getWriter(), question.getWriter()) && Objects.equals(getTitle(), question.getTitle()) && Objects.equals(getContents(), question.getContents()) && Objects.equals(getCreatedDate(), question.getCreatedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionId(), getWriter(), getTitle(), getContents(), getCreatedDate(), getCountOfAnswer());
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDate=" + createdDate +
                ", countOfAnswer=" + countOfAnswer +
                '}';
    }
}
