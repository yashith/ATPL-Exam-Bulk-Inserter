package com.gb.ATPLExamBulkInserter.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category implements IModel {
    @Id
    private int id;
    private String name;
    private String description;

    public Category(String[] record) {
        this.id = Integer.parseInt(record[0]);
        this.name = record[1];
        this.description = record[2];
    }

    public Category() {

    }
}
