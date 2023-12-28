package com.gb.ATPLExamBulkInserter.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
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
        this.id = Integer.parseInt(record[0]);
        this.questionId= Integer.parseInt(record[1]);
        this.answer= record[2];
        this.isImage = record[3].equalsIgnoreCase("true")?true:false;
        this.isCorrectAnswer= record[4].equalsIgnoreCase("true")?true:false;

    }

    public Answer() {

    }
}
