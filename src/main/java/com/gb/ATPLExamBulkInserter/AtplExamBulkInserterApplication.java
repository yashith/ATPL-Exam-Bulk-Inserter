package com.gb.ATPLExamBulkInserter;

import com.gb.ATPLExamBulkInserter.enums.InserterTypes;
import com.gb.ATPLExamBulkInserter.inserter.IInserter;
import com.gb.ATPLExamBulkInserter.parsers.PdfParser;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AtplExamBulkInserterApplication implements CommandLineRunner {

    @Autowired
    IInserter categoryInserter;
    @Autowired
    IInserter subModuleInserter;
    @Autowired
    IInserter questionInserter;
    @Autowired
    IInserter answersInserter;
    @Autowired
    PdfParser pdfParser;
    private static Logger logger = LoggerFactory.getLogger(AtplExamBulkInserterApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AtplExamBulkInserterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Options options = new Options();
        options.addOption(new Option("d", "To insert directly to db"));
        options.addOption(new Option("w", "To insert via endpoints"));
        options.addOption(new Option("c", "To insert Category"));
        options.addOption(new Option("s", "To insert Submodules"));
        options.addOption(new Option("q", "To insert Questions"));
        options.addOption(new Option("a", "To insert Answers"));
        options.addOption(new Option("p", "To parse PDF"));

        CommandLineParser cliParser = new DefaultParser();
        CommandLine cli = cliParser.parse(options, args);

        InserterTypes it = null;
        if (cli.hasOption("d")) {
            it = InserterTypes.DB_CLIENT;
        } else if (cli.hasOption("w")) {
            it = InserterTypes.WEB_CLIENT;
        }else{
            logger.error("Provide method to insert -d / -w");
            System.exit(1);
        }
        if (cli.hasOption("c")) {
            categoryInserter.insertCsv(it);
        }
        if (cli.hasOption("s")) {
            subModuleInserter.insertCsv(it);
        }
        if (cli.hasOption("q")) {
            questionInserter.insertCsv(it);
        }
        if (cli.hasOption("a")) {
            answersInserter.insertCsv(it);
        }
        if (cli.hasOption("p")){
            pdfParser.init();
        }
    }
}
