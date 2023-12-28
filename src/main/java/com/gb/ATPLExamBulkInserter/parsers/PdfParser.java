package com.gb.ATPLExamBulkInserter.parsers;

import com.gb.ATPLExamBulkInserter.Model.Answer;
import com.gb.ATPLExamBulkInserter.Model.Question;
import com.opencsv.CSVWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PdfParser {
    List<Question> questionList = new ArrayList<>();
    List<Answer> answerList = new ArrayList<>();
    public void init() {
        String pdfFilePath = "C:\\Users\\IceBerg\\Downloads\\pdfcoffee.com_070-operational-procedurespdf-pdf-free.pdf";
        try {
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setStartPage(7);
                stripper.setEndPage(88);
                String text = stripper.getText(document);
                document.close();
                String[] qa = text.split("\\d{4,5}\\ \\(.*:..*");
                parseQuestions(qa);
                writeQuestionCsv();
                writeAnswerCsv();
                document.close();
            }

        } catch (IOException e) {

        }
    }

    void parseQuestions(String[] qa) {
        //Ignore 1st item since not important
        for (int i = 1; i < qa.length; i++) {
            String skippedQuestion = "";
            try {
                //split for question
                String[] q = qa[i].split(":\n");
                String question = q[0];
                skippedQuestion = question;
                questionList.add(new Question(i,100,question));

                String[] answer1Split = q[0].split("A\\)");
                String answer1 = answer1Split[0];
                answerList.add(new Answer((i-1)*4+1,i,answer1,false ,false));

                String[] answer2Split = answer1Split[1].split("B\\)");
                String answer2 = answer2Split[0];
                answerList.add(new Answer((i-1)*4+2,i,answer2,false ,false));

                String[] answer3Split = answer2Split[1].split("C\\)");
                String answer3 = answer3Split[0];
                answerList.add(new Answer((i-1)*4+3,i,answer3,false ,false));

                String answer4 = answer3Split[1];
                answerList.add(new Answer((i-1)*4+4,i,answer4,false ,false));

                System.out.println(answer3);

            }catch (ArrayIndexOutOfBoundsException e){

            }

        }
    }

    void writeQuestionCsv() throws IOException {
        Writer writer = new FileWriter("E:\\Projects\\Q typing\\question.csv");
        CSVWriter csvWriter = new CSVWriter(writer);
        for(Question q:questionList){
            csvWriter.writeNext(new String[]{String.valueOf(q.getId()),"100",q.getQuestion()});
        }
        csvWriter.close();
    }
    void writeAnswerCsv() throws IOException {

        Writer writer = new FileWriter("E:\\Projects\\Q typing\\answer.csv");
        CSVWriter csvWriter = new CSVWriter(writer);
        for(Answer a:answerList){
            csvWriter.writeNext(new String[]{String.valueOf(a.getId()),String.valueOf(a.getQuestionId()),a.getAnswer(),"false","false"});
        }
        csvWriter.close();
    }
}
