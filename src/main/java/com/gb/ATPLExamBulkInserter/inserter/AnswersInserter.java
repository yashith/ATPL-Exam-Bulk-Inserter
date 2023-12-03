package com.gb.ATPLExamBulkInserter.inserter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.ATPLExamBulkInserter.Model.Answer;
import com.gb.ATPLExamBulkInserter.Model.IModel;
import com.gb.ATPLExamBulkInserter.Model.Question;
import com.gb.ATPLExamBulkInserter.enums.InserterTypes;
import com.gb.ATPLExamBulkInserter.repositories.AnswerRepository;
import com.gb.ATPLExamBulkInserter.repositories.QuestionRepository;
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
public class AnswersInserter implements IInserter {
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    CSVReader csvAnswerReader;
    @Autowired
    Environment env;
    @Override
    public void insertWithHttp(IModel answer) throws IOException, InterruptedException {
        Answer a= (Answer) answer;
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(answer);
        String endpoint = env.getProperty("server-url") + "/api/v1/answers/add";
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
    public void insertWithDB(IModel answer) {
        answerRepository.save((Answer) answer);
    }

    public void insertCsv(InserterTypes insertType) {
        try {
            List<String[]> records = csvAnswerReader.readAll();
            for (String[] record : records) {
                Answer answer= new Answer(record);
                if (insertType == InserterTypes.DB_CLIENT) {
                    insertWithDB(answer);
                } else if(insertType == InserterTypes.WEB_CLIENT) {
                    insertWithHttp(answer);
                }
            }
        } catch (IOException | CsvException | InterruptedException e) {
            logger.error(e);
        }
    }
}
