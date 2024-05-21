
import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class ManualExam implements Examable {
    public void creatExam(DataBase mainData) throws Exception {
        Scanner s = new Scanner(System.in);
        int numOfQuestions = 0, indexOfQuestion = 0, indexOfAnswer = 0;
        String input, inputOfIsCorrect;
        boolean isCorrect, isValid = false;

        System.out.println("Enter the amount of questions you would like to add to you're test");
        System.out.println("There is " + mainData.getAllQuestions().length + " questions in the data");
        numOfQuestions = s.nextInt();
        s.nextLine();
        if (numOfQuestions > mainData.allQuestions.length || numOfQuestions < 1) {
            if (numOfQuestions > 10) {
                throw new Exception("Cannot enter more then 10 questions");
            }
            throw new Exception("Please enter 1- " + mainData.allQuestions.length + " questions");
        }
        Question[] examQuestions = new Question[numOfQuestions];
        ClosedQuestion closedQuestion;
        OpenQuestion openQuestion;

        for (int i = 0; i < numOfQuestions; i++) {
            System.out.println("Select the index of question you would like to add :");
            printQuestions(mainData.getAllQuestions());
            indexOfQuestion = s.nextInt() - 1;
            s.nextLine();
            if (mainData.getAllQuestions()[indexOfQuestion] instanceof ClosedQuestion) {
                closedQuestion = new ClosedQuestion((ClosedQuestion) mainData.getAllQuestions()[indexOfQuestion]);
                if (closedQuestion.getAnswer().length < 3) {
                    throw new Exception("This questions has less then 3 answers to it and it cant be added");
                }
                System.out.println("Are these the answers that you want for this question [Y/N] ? ");
                input = s.nextLine();
                if (input.compareToIgnoreCase("y") == 0) {
                    examQuestions[i] = mainData.getAllQuestions()[indexOfQuestion];
                } else if (input.compareToIgnoreCase("n") == 0) {
                    System.out.println("Would you like to add or remove answers from this question [Add/Remove] ?");
                    input = s.nextLine();
                    int numOfActions = 0;
                    System.out.println("How many ?");
                    numOfActions = s.nextInt();
                    s.nextLine();
                    for (int j = 0; j < numOfActions; j++) {
                        if (numOfActions < 0) {
                            throw new Exception("Number of actions has to be more than 1");
                        }
                        if (input.compareToIgnoreCase("add") == 0 && (numOfActions + closedQuestion.getAnswer().length) < 10) {
                            System.out.println("Select the answer that you would like to add");
                            printAnswers(mainData.getAllAnswers());
                            indexOfAnswer = s.nextInt() - 1;
                            s.nextLine();
                            if (indexOfAnswer > mainData.getAllAnswers().length - 1) {
                                System.out.println("Invalid input");
                                return;
                            } else {
                                System.out.println("Is this answer correct or false [F/T] ?");
                                inputOfIsCorrect = s.nextLine();

                                if (inputOfIsCorrect.compareToIgnoreCase("f") == 0) {
                                    isCorrect = false;
                                } else if (inputOfIsCorrect.compareToIgnoreCase("t") == 0) {
                                    isCorrect = true;
                                } else {
                                    throw new Exception("enter Y/N");
                                }
                                addAnswerToQuestion(isCorrect, closedQuestion, mainData.getAllAnswers()[indexOfAnswer]);
                            }
                        } else if (input.compareToIgnoreCase("remove") == 0) {
                            if (numOfActions > closedQuestion.getAnswer().length || numOfActions < 0 || (closedQuestion.getAnswer().length - numOfActions) <= 4) {
                                System.out.println("number of actions is invalid");
                                return;
                            }
                            System.out.println("Select the answer that you would like to remove");
                            printAnswers(closedQuestion.getAnswer());
                            indexOfAnswer = s.nextInt() - 1;
                            s.nextLine();
                            closedQuestion.removeAnswer(indexOfAnswer);
                        } else {
                            throw new Exception("Invalid number of actions");
                        }
                    }
                } else {
                    throw new Exception("Enter Y/N");
                }

            } else {
                examQuestions[i] = mainData.getAllQuestions()[indexOfQuestion];
                System.out.println("Question added");
            }
        }

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm");
        File exam = new File("exam_" + time.format(format) + ".txt");
        PrintWriter pw = new PrintWriter(exam);


        for (int i = 0; i < examQuestions.length; i++) {
            if (examQuestions[i] instanceof ClosedQuestion) {
                pw.println((i + 1) + "}" + examQuestions[i].getQuestion());
                closedQuestion = (ClosedQuestion) examQuestions[i];
                for (int j = 0; j < closedQuestion.getAnswer().length; j++) {
                    pw.println((j + 1) + ")" + closedQuestion.getAnswer()[j].toString());
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
                closedQuestion = (ClosedQuestion) examQuestions[i];
                for (int j = 0; j < closedQuestion.getAnswer().length; j++) {
                    pwAnswers.println((j + 1) + ") " + closedQuestion.getCorrect()[j]);
                }
                pwAnswers.println();
            } else {
                openQuestion = (OpenQuestion) examQuestions[i];
                pwAnswers.println((i + 1) + ")" + openQuestion.getAnswer());
            }

        }
        pwAnswers.close();
        System.out.println("Test was created");
    }
}
