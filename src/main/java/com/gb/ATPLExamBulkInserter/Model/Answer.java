package com.gb.ATPLExamBulkInserter.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer implements IModel {
    @Id
    private int id;
    @JsonProperty("question_id")
    private int questionId;
    private String answer;
    @JsonProperty("is_image")
    private boolean isImage;
    @JsonProperty("is_correct_answer")
    private boolean isCorrectAnswer;

    public Answer(String[] record) {
        if (record.length == 5) {
            this.id = Integer.parseInt(record[0]);
            this.questionId= Integer.parseInt(record[1]);
            this.answer= record[2];
            this.isImage = Boolean.getBoolean(record[3]);
            this.isCorrectAnswer= Boolean.getBoolean(record[4]);
        }
    }

    public Answer() {

    }
}
