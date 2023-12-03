package com.gb.ATPLExamBulkInserter.repositories;

import com.gb.ATPLExamBulkInserter.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
