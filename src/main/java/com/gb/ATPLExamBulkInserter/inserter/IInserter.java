package com.gb.ATPLExamBulkInserter.inserter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gb.ATPLExamBulkInserter.Model.IModel;
import com.gb.ATPLExamBulkInserter.enums.InserterTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.http.HttpClient;

public interface IInserter {

    HttpClient httpClient = HttpClient.newHttpClient();
    Logger logger= LogManager.getLogger(IInserter.class);
    public void insertWithHttp(IModel model) throws IOException, InterruptedException;
    public void insertWithDB(IModel model);
    public void insertCsv(InserterTypes inserterType);

}
