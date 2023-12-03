package com.gb.ATPLExamBulkInserter.inserter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.ATPLExamBulkInserter.Model.Category;
import com.gb.ATPLExamBulkInserter.Model.IModel;
import com.gb.ATPLExamBulkInserter.Model.SubModule;
import com.gb.ATPLExamBulkInserter.enums.InserterTypes;
import com.gb.ATPLExamBulkInserter.repositories.CategoryRepository;
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
public class SubModuleInserter implements IInserter{
    @Autowired
    SubModuleRepository subModuleRepository;
    @Autowired
    CSVReader csvSubModuleReader;
    @Autowired
    Environment env;
    @Override
    public void insertWithHttp(IModel subModule) throws IOException, InterruptedException {
        SubModule sb= (SubModule) subModule;
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(sb);
        String endpoint = env.getProperty("server-url") + "/api/v1/submoules/add";
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
    public void insertWithDB(IModel subModule) {
        logger.info("Inserting record {}",subModule.toString());
        subModuleRepository.save((SubModule)subModule);
    }

    public void insertCsv(InserterTypes insertType) {
        try {
            List<String[]> records = csvSubModuleReader.readAll();
            for (String[] record : records) {
                SubModule subModule= new SubModule(record);
                if (insertType == InserterTypes.DB_CLIENT) {
                    insertWithDB(subModule);
                } else if(insertType == InserterTypes.WEB_CLIENT) {
                    insertWithHttp(subModule);
                }
            }
        } catch (IOException | CsvException | InterruptedException e) {
            logger.error(e);
        }
    }
}
