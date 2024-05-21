import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class AutomaticExam implements Examable {
    @Override
    public void creatExam(DataBase mainData) throws Exception {
        Scanner s = new Scanner(System.in);
        int numOfQuestions,moreThenOneCounter = 0;
        Random rnd = new Random();
        Question[] examQuestions;
        Answer[] rndAnswers = new Answer[4];
        boolean[] rndAnswersCorrect = new boolean[4];
        ClosedQuestion closedQuestion;
        OpenQuestion openQuestion;

        System.out.println("How many questions would you like to add ?");
        numOfQuestions = s.nextInt();
        s.nextLine();
        if(numOfQuestions > mainData.allQuestions.length){
            if (numOfQuestions > 10){
                throw new Exception("Cannot enter more then 10 questions");
            }
            throw new Exception("Cannot enter more then "+mainData.allQuestions.length+" questions");
        }
        examQuestions = new Question[numOfQuestions];

        for (int i = 0; i < numOfQuestions; i++) {
            int randomQuestion = rnd.nextInt(mainData.allQuestions.length);
            if (mainData.allQuestions[randomQuestion] instanceof ClosedQuestion) {
                for (int j = 0; j < 4;j++){
                    int randomIndexOfAnswer = rnd.nextInt(((ClosedQuestion) mainData.allQuestions[randomQuestion]).answers.length);
                    if(((ClosedQuestion) mainData.allQuestions[randomQuestion]).answers[randomIndexOfAnswer].toString().equals("More than one is correct") || ((ClosedQuestion) mainData.allQuestions[randomQuestion]).answers[randomIndexOfAnswer].toString().equals("All wrong")){
                        j--;
                        continue;
                    }
                    rndAnswers[j] = ((ClosedQuestion) mainData.allQuestions[randomQuestion]).answers[randomIndexOfAnswer];
                    rndAnswersCorrect[j] = ((ClosedQuestion) mainData.allQuestions[randomQuestion]).correctAns[randomIndexOfAnswer];
                    if(((ClosedQuestion) mainData.allQuestions[randomQuestion]).correctAns[randomIndexOfAnswer]){
                        moreThenOneCounter++;
                        if (moreThenOneCounter == 2){
                            j--;
                        }
                    }
                }
                closedQuestion = new ClosedQuestion(mainData.allQuestions[randomQuestion].getQuestion(),mainData.allQuestions[randomQuestion].getDifficulty(),rndAnswers,rndAnswersCorrect);
                examQuestions[i] = closedQuestion;

            } else {
                examQuestions[i] = (OpenQuestion) mainData.allQuestions[randomQuestion];
            }
        }

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
        File exam = new File("exam_" + time.format(format) + ".txt");
        PrintWriter pw = new PrintWriter(exam);


        for (int i = 0; i < examQuestions.length; i++) {
            if (examQuestions[i] instanceof ClosedQuestion) {
                pw.println((i + 1) + "}" + examQuestions[i].getQuestion());
                for (int j = 0; j < ((ClosedQuestion) examQuestions[i]).getAnswer().length; j++) {
                    pw.println((j + 1) + ")" + ((ClosedQuestion) examQuestions[i]).getAnswer()[j].toString());
                }
                pw.println();
            } else {
                openQuestion = (OpenQuestion) examQuestions[i];
                pw.println((i + 1) + "}" + openQuestion.getQuestion());
                pw.println();
            }

        }
        pw.close();

        File solution = new File("solution_" + time.format(format) + ".txt");
        PrintWriter pwAnswers = new PrintWriter(solution);
        for (int i = 0; i < examQuestions.length; i++) {
            if (examQuestions[i] instanceof ClosedQuestion) {
                pwAnswers.println((i + 1) + "}" + examQuestions[i].getQuestion());
                for (int j = 0; j < ((ClosedQuestion) examQuestions[i]).getAnswer().length; j++) {
                    pwAnswers.println((j + 1) + ") " + ((ClosedQuestion) examQuestions[i]).getCorrect()[j]);
                }
                pwAnswers.println();
            } else {
                openQuestion = (OpenQuestion) examQuestions[i];
                pwAnswers.println((i + 1) + ")" + openQuestion.getAnswer());
                pwAnswers.println();
            }

        }
        pwAnswers.close();
        System.out.println("Test was created");
    }

}
