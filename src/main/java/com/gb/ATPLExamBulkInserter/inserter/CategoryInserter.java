package com.gb.ATPLExamBulkInserter.inserter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.ATPLExamBulkInserter.Model.Category;
import com.gb.ATPLExamBulkInserter.Model.IModel;
import com.gb.ATPLExamBulkInserter.enums.InserterTypes;
import com.gb.ATPLExamBulkInserter.repositories.CategoryRepository;
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
public class CategoryInserter implements IInserter {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CSVReader csvCategoryReader;
    @Autowired
    Environment env;
    @Override
    public void insertWithHttp(IModel category) throws IOException, InterruptedException {
        Category cat = (Category) category;
        String json = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(cat);
        String endpoint = env.getProperty("server-url") + "/api/v1/categories/add";
        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .uri(URI.create(endpoint))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void insertWithDB(IModel category) {
        logger.info("Inserting record {}",category.toString());
        categoryRepository.save((Category) category);
    }

    public void insertCsv(InserterTypes insertType) {
        try {
            List<String[]> records = csvCategoryReader.readAll();
            for (String[] record : records) {
                Category category = new Category(record);
                if (insertType == InserterTypes.DB_CLIENT) {
                    insertWithDB(category);
                } else if(insertType == InserterTypes.WEB_CLIENT) {
                    insertWithHttp(category);
                }
            }
        } catch (IOException | CsvException | InterruptedException e) {
            logger.error(e);
        }
    }
}
