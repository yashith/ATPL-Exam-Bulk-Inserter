import re


fquestion= open("question.csv","w+",encoding='UTF8')
fanswers= open("answer.csv","w+",encoding='UTF8')
 
def extact_q_a():
    # creating a page object
    qId_s =input("first question id :") 
    qId = int(qId_s) 
    aId_s =  input("first answer id :") 
    aId =  int(aId_s) 
    q_re = r"\n?A\)"
    while True:
        q = input("question text :")
        real_answer = input("real answer :")
        question = re.split(q_re,q)[0]
        question = question.replace("\n","")
        answers = q
        answers = answers.replace("\n"," ")
        answers.find
        # answer1 = re.search(r"(?<=A\)).*(?=B\))",answers).group(0)
        # answer2 = re.search(r"(?<=B\)).*(?=C\))",answers).group(0)
        # answer3 = re.search(r"(?<=C\)).*(?=D\))",answers).group(0)
        # answer4 = re.search(r"(?<=D\)).*",answers).group(0)
        answer1 = input("Answer A: ")  
        answer2 = input("Answer B: ")  
        answer3 = input("Answer C: ")  
        answer4 = input("Answer D")  
        
        print(str(qId) +"========>" +question)
        questions_arr = [str(qId),"100",question]
        print(answer1)
        answers_arr = [str(aId),str(qId),answer1,"false",("false","true")["A" == real_answer]]
        aId+=1
        print(answer2)
        answers_arr = [str(aId),str(qId),answer2,"false",("false","true")["B" == real_answer]] 
        aId+=1
        print(answer3)
        answers_arr = [str(aId),str(qId),answer3,"false",("false","true")["C" == real_answer]]
        aId+=1
        print(answer4)
        answers_arr = [str(aId),str(qId),answer4,"false",("false","true")["D" == real_answer]]
        aId+=1

    qId+=1
    
extact_q_a()