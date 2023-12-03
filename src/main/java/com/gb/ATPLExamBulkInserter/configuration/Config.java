package com.gb.ATPLExamBulkInserter.configuration;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

@Configuration
public class Config {
    @Autowired
    Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(env.getProperty("driverClassName"));
        driverManagerDataSource.setUrl(env.getProperty("url"));
        driverManagerDataSource.setUsername(env.getProperty("user"));
        driverManagerDataSource.setPassword(env.getProperty("password"));
        return driverManagerDataSource;
    }
    @Bean
    public CSVReader csvCategoryReader() throws FileNotFoundException {
        String rootFolder = env.getProperty("csv.location");
        Reader reader = new FileReader(rootFolder+"/"+"category.csv");
        return new CSVReader(reader);
    }
    @Bean
    public CSVReader csvSubModuleReader() throws FileNotFoundException {
        String rootFolder = env.getProperty("csv.location");
        Reader reader = new FileReader(rootFolder+"/"+"submodule.csv");
        return new CSVReader(reader);
    }
    @Bean
    public CSVReader csvQuestionReader() throws FileNotFoundException {
        String rootFolder = env.getProperty("csv.location");
        Reader reader = new FileReader(rootFolder+"/"+"question.csv");
        return new CSVReader(reader);
    }
    @Bean
    public CSVReader csvAnswerReader() throws FileNotFoundException {
        String rootFolder = env.getProperty("csv.location");
        Reader reader = new FileReader(rootFolder+"/"+"answer.csv");
        return new CSVReader(reader);
    }
}
