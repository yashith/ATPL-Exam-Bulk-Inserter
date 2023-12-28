import PyPDF2
import re
import csv

filePath = "questions.pdf"
pdfFileObj = open(filePath,'rb')

pdfReader = PyPDF2.PdfReader(pdfFileObj)
 
# printing number of pages in pdf file
print(len(pdfReader.pages))
 
questions_arr=[]
answers_arr=[]
real_answers={}
def write_question_csv():
    f= open("question.csv","w+",encoding='UTF8')
    writer = csv.writer(f)
    writer.writerows(questions_arr)
    f.close()

def write_answers_csv():
    f= open("answers.csv","w+",encoding='UTF8')
    writer = csv.writer(f)
    writer.writerows(answers_arr)
    f.close()

def read_answers_doc():
    f = open("answers.txt","r",encoding='UTF8')
    answerText = f.read()
    matches = re.findall(r"\d{4,5}\s\(.\)",answerText)
    for match in matches:
        qa = str(match).split(" ")
        real_answers[qa[0]] = qa[1].replace("(","").replace(")","")
    f.close()

def extact_q_a():
    # creating a page object
    qId = 960
    longQId =""
    aId = 3837
    last_page = 73
    first_page = 12
    while first_page<=last_page:
        pageObj = pdfReader.pages[first_page]
        extractedText = pageObj.extract_text() 
        extractedText = extractedText.split(r"© 2008AVIATIONEXAM.com")[1]
        reg = r"(\d{4,5}\.\s\(AIR:.*\))|(\d{4,5}\.\s\(all\))|(\d{4,5}\.\s\(HELI:.*\))"
        qList = re.split(reg,extractedText)

        for i,q in enumerate(qList):
            try:
                if(i!=0):
                    q_re = r"\n?A\)"
                    if(re.match(r"\d{4,5}\.\s\(.*\)",q)):
                        longQId=re.split(r"\.",q)[0]
                        print(q)
                    question = re.split(q_re,q)[0]
                    question = question.replace("\n","")
                    answers = q
                    answers = answers.replace("\n"," ")
                    answers.find
                    answer1 = re.search(r"(?<=A\)).*(?=B\))",answers).group(0)
                    answer2 = re.search(r"(?<=B\)).*(?=C\))",answers).group(0)
                    answer3 = re.search(r"(?<=C\)).*(?=D\))",answers).group(0)
                    answer4 = re.search(r"(?<=D\)).*",answers).group(0)
                    
                    print(str(first_page) +" "+ str(qId) +"========>" +question)
                    questions_arr.append([str(qId),"100",question])
                    print(answer1)
                    answers_arr.append([str(aId),str(qId),answer1,"false",("false","true")["A" == real_answers.get(str(longQId))]]) 
                    aId+=1
                    print(answer2)
                    answers_arr.append([str(aId),str(qId),answer2,"false",("false","true")["B" == real_answers.get(str(longQId))]]) 
                    aId+=1
                    print(answer3)
                    answers_arr.append([str(aId),str(qId),answer3,"false",("false","true")["C" == real_answers.get(str(longQId))]]) 
                    aId+=1
                    print(answer4)
                    answers_arr.append([str(aId),str(qId),answer4,"false",("false","true")["D" == real_answers.get(str(longQId))]]) 
                    aId+=1

                    qId+=1
                else:
                    somethin = re.search(r"\d{4,5}\s\(\C\)",q).groupdict()
                    print(q)
            except:
                print()
        first_page+=1
        # closing the pdf file object
    pdfFileObj.close()
    
read_answers_doc()    
extact_q_a()
write_question_csv()
write_answers_csv()