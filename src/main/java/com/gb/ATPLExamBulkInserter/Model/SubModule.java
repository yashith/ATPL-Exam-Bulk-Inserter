package com.gb.ATPLExamBulkInserter.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
public class SubModule implements IModel{
    @Id
    int id;
    String name;
    @JsonProperty("category")
    int Category;

    public SubModule(String[] record) {
        this.id = Integer.parseInt(record[0]);
        this.name = record[1];
        this.Category= Integer.parseInt(record[2]);
    }

    public SubModule() {

    }
}
