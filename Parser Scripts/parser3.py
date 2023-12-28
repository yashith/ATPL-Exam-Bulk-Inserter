import re
import csv


fquestion= open("question.csv","w+",encoding='UTF8')
fanswers= open("answer.csv","w+",encoding='UTF8')
q  = open("qustions2.txt","r").read()
questions_arr=[]
answers_arr=[]

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
 
def extact_q_a():
    # creating a page object
    qId = 960
    aId = 3837 
    qs_re = r"!!!"
    questions = re.split(qs_re,q);
    for i,full_question in enumerate(questions):
        q_re = r"\n?A\)"
        correct_A_re = r"#[A-Z]"
        questionNAnswers= re.split(q_re,full_question)
        question = questionNAnswers[0]
        answers= questionNAnswers[1]
        question = question.replace("\n","")
        answers = answers.replace("\n"," ")
        answer1 = re.search(r".*(?=B\))",answers).group(0)
        answer2 = re.search(r"(?<=B\)).*(?=C\))",answers).group(0)
        answer3 = re.search(r"(?<=C\)).*(?=D\))",answers).group(0)
        answer4 = re.search(r"(?<=D\)).*(?=#[A-Z])",answers).group(0)
        real_answer= re.search(correct_A_re,answers).group(0).replace("#","")
        print(str(qId) +"========>" +question)
        questions_arr.append([str(qId),"100",question]) 
        print(answer1)
        answers_arr.append([str(aId),str(qId),answer1,"false",("false","true")["A" == real_answer]])
        aId+=1
        print(answer2)
        answers_arr.append([str(aId),str(qId),answer2,"false",("false","true")["B" == real_answer]])
        aId+=1
        print(answer3)
        answers_arr.append([str(aId),str(qId),answer3,"false",("false","true")["C" == real_answer]])
        aId+=1
        print(answer4)
        answers_arr.append([str(aId),str(qId),answer4,"false",("false","true")["D" == real_answer]])
        aId+=1
        qId+=1
    
extact_q_a()
write_question_csv()
write_answers_csv()