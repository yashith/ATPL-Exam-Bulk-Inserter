package com.gb.ATPLExamBulkInserter.inserter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.ATPLExamBulkInserter.Model.IModel;
import com.gb.ATPLExamBulkInserter.Model.Question;
import com.gb.ATPLExamBulkInserter.Model.SubModule;
import com.gb.ATPLExamBulkInserter.enums.InserterTypes;
import com.gb.ATPLExamBulkInserter.repositories.QuestionRepository;
import com.gb.ATPLExamBulkInserter.repositories.SubModuleRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class QuestionInserter implements IInserter{
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CSVReader csvQuestionReader;
    @Autowired
    Environment env;
    @Override
    public void insertWithHttp(IModel question) throws IOException, InterruptedException {
        Question q= (Question) question;
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(q);
        String endpoint = env.getProperty("server-url") + "/api/v1/questions/add";
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
        logger.info(response.body());
    }

    @Override
    public void insertWithDB(IModel question) {
        logger.info("Inserting record {}",question.toString());
        questionRepository.save((Question)question);
    }

    public void insertCsv(InserterTypes insertType) {
        try {
            List<String[]> records = csvQuestionReader.readAll();
            for (String[] record : records) {
                Question question = new Question(record);
                if (insertType == InserterTypes.DB_CLIENT) {
                    insertWithDB(question);
                } else if(insertType == InserterTypes.WEB_CLIENT) {
                    insertWithHttp(question);
                }
            }
        } catch (IOException | CsvException | InterruptedException e) {
            logger.error(e);
        }
    }
}
