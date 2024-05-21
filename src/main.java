import javax.xml.crypto.*;
import java.io.*;
import java.util.*;

public class main {
    public enum QuestionType {open, closed}
    public static void printMenu(DataBase mainData,String fileName) throws Exception {
        Scanner s = new Scanner(System.in);
        int input = 0;
        do {
            try {
                System.out.println("Main menu please enter what you would like to do:\n"
                        + "1)Print all data\n"
                        + "2)add answer to the data\n"
                        + "3)add answer to a question\n"
                        + "4)add new question to the data\n"
                        + "5)remove answer for a question\n"
                        + "6)remove a question from the data\n"
                        + "7)create manual exam\n"
                        + "8)create automatic exam\n"
                        + "-1)to Exit");
                input = s.nextInt();
                s.nextLine();
                switch (input) {
                    case 1:
                        System.out.println("Questions:");
                        DataBase.printQuestions(mainData.getAllQuestions());
                        System.out.println("Answers:");
                        DataBase.printAnswers(mainData.getAllAnswers());
                        break;
                    case 2:
                        String answer;
                        System.out.println("Enter the answer that will be added to the data:");
                        answer = s.nextLine();
                        mainData.addAnswerToData(new Answer(answer));
                        break;
                    case 3:
                        int indexOfAnswer = 0, indexOfQuestion;
                        boolean isCorrect;
                        String inputCorrect;
                        System.out.println("Enter the question number that you would like to edit :");
                        DataBase.printQuestions(mainData.getAllQuestions());
                        indexOfQuestion = s.nextInt() - 1;
                        s.nextLine();
                        if (mainData.getAllQuestions()[indexOfQuestion] instanceof OpenQuestion) {
                            throw new Exception("You cannot add an answer to a open question");
                        } else {
                            System.out.println("Enter the index of the answer that you would like to add :");
                            printAnswers(mainData.getAllAnswers());
                            indexOfAnswer = s.nextInt() - 1;
                            s.nextLine();
                            if(indexOfAnswer < 0 || indexOfAnswer > mainData.allAnswers.length - 1){
                                throw new Exception("Enter 1-"+mainData.allAnswers.length);
                            }
                            System.out.println("Is this answer Correct ? [Y/N]");
                            inputCorrect = s.nextLine();
                            if (inputCorrect.compareToIgnoreCase("y") == 0) {
                                isCorrect = true;
                            } else if (inputCorrect.compareToIgnoreCase("n") == 0) {
                                isCorrect = false;
                            } else {
                                throw new Exception("enter Y/N");
                            }

                            ClosedQuestion selectedQuestion = (ClosedQuestion) mainData.getAllQuestions()[indexOfQuestion];
                            addAnswerToQuestion(isCorrect, selectedQuestion, mainData.getAllAnswers()[indexOfAnswer]);
                            System.out.println("Answer was added to question successfully");
                        }
                        break;
                    case 4:
                        int numOfAnswers;
                        String question;

                        System.out.println("Enter the question that you would like to add:");
                        question = s.nextLine();

                        System.out.println("What type of question will it be ?");
                        QuestionType[] temp = QuestionType.values();
                        for (int i = 0; i < temp.length; i++) {
                            System.out.println(temp[i].name());
                        }
                        QuestionType type = QuestionType.valueOf(s.nextLine());
                        System.out.println("Enter the question difficulty: ");
                        Difficulty[] allDifficulty = Difficulty.values();
                        for (int i = 0; i < allDifficulty.length; i++) {
                            System.out.println(allDifficulty[i].name());
                        }
                        Difficulty difficulty = Difficulty.valueOf(s.next());
                        switch (type) {
                            case closed:
                                System.out.println("You can add 4 - 8 answers , how many would you like to add ?");
                                numOfAnswers = s.nextInt();
                                s.nextLine();
                                if(numOfAnswers < 4 || numOfAnswers > 8){
                                    throw new Exception("Enter 4 - 8");
                                }
                                Answer[] answersOfQuestion = new Answer[numOfAnswers];
                                boolean[] correctAnswer = new boolean[numOfAnswers];

                                for (int i = 0; i < numOfAnswers; i++) {
                                    System.out.println("Select the index of answer you would like to add:");
                                    printAnswers(mainData.getAllAnswers());
                                    indexOfAnswer = s.nextInt() - 1;
                                    s.nextLine();
                                    answersOfQuestion[i] = mainData.allAnswers[indexOfAnswer];
                                    System.out.println("Is this answer correct ? [Y/N]");
                                    inputCorrect = s.nextLine();
                                    if (inputCorrect.compareToIgnoreCase("y") == 0) {
                                        correctAnswer[i] = true;
                                    } else if (inputCorrect.compareToIgnoreCase("n") == 0) {
                                        correctAnswer[i] = false;
                                    } else {
                                        throw new Exception("enter Y/N");
                                    }
                                }
                                Question newClosedQuestion = new ClosedQuestion(question, difficulty, answersOfQuestion, correctAnswer);
                                mainData.addQuestionToData(newClosedQuestion);
                                break;
                            case open:
                                System.out.println("Enter the answer for that question:");
                                String openAnswer = s.nextLine();
                                OpenQuestion newOpenQuestion = new OpenQuestion(question, difficulty, openAnswer);
                                mainData.addQuestionToData(newOpenQuestion);
                                System.out.println("Question added successfully");
                                break;
                        }
                        break;
                    case 5:
                        System.out.println("Select the index of question you would like to remove:");
                        printQuestions(mainData.getAllQuestions());
                        indexOfQuestion = s.nextInt() - 1;
                        s.nextLine();
                        if (mainData.getAllQuestions()[indexOfQuestion] instanceof OpenQuestion) {
                            throw new Exception("You cannot remove answer of an open question");
                        } else {
                            ClosedQuestion selectedQuestion = (ClosedQuestion) mainData.getAllQuestions()[indexOfQuestion];
                            System.out.println("Select the index of answer you would like to remove:");
                            printAnswers(selectedQuestion.getAnswer());
                            indexOfAnswer = s.nextInt() - 1;
                            s.nextLine();
                            removeAnswerFromQuestion(selectedQuestion, indexOfAnswer);
                        }
                        break;
                    case 6:
                        if (mainData.getAllQuestions().length == 0) {
                            throw new Exception("There is no questions in the data");
                        }
                        System.out.println("Enter the index of Question you would like to remove:");
                        printQuestions(mainData.getAllQuestions());
                        indexOfQuestion = s.nextInt() - 1;
                        s.nextLine();
                        mainData.removeQuestionFromData(indexOfQuestion);
                        System.out.println("Question was removed");
                        break;

                    case 7:
                        ManualExam manualExam = new ManualExam();
                        manualExam.creatExam(mainData);
                        break;
                    case 8:
                        AutomaticExam automaticExam = new AutomaticExam();
                        automaticExam.creatExam(mainData);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Index Invalid");
            } catch (InputMismatchException e) {
                s.nextLine();
                System.out.println("Input mismatch");
            } catch(IllegalArgumentException e){
                System.out.println("Not an option");
            }
            catch (Exception e) {
                System.out.println("Wrong Input:" + e.getMessage());
            }
        } while (input != -1);
        mainData.saveDataToFile(fileName);
    }
    public static int YesOrNoAnswer(String ans){
        switch (ans){
            case "Y":
                return 1;
            case "N":
                return 0;
            default:
                return -1;
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        String fileName;
        System.out.println("Enter subject data file name:");
        fileName = s.nextLine();
        try{
            DataBase mainData = DataBase.getInstance(fileName);
            printMenu(mainData,fileName);
        }
        catch (FileNotFoundException e){
            System.out.println("Creating new file");
            ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream(fileName+".dat"));
            savedFile.close();
            DataBase mainData = DataBase.getInstance(fileName);
            printMenu(mainData,fileName);
        }


    }
}
//