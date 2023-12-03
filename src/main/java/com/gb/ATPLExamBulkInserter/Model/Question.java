package com.gb.ATPLExamBulkInserter.Model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Question implements IModel{
    @Id
    private int id;
    @JsonProperty("sub_module_id")
    private int subModuleId;
    private String question;

    public Question(String[] record) {
        this.id = Integer.parseInt(record[0]);
        this.subModuleId= Integer.parseInt(record[1]);
        this.question= record[2];
    }

    public Question() {

    }
}
