import javax.xml.crypto.*;
import java.io.*;
import java.util.*;

public class main {
    public static boolean validInputRange(int bottom,int upper,int input){
        if (input < bottom || input > upper){
            return false;
        }
        return true;
    }
    public static void printMenu(DataBase mainData,String fileName) throws Exception {
        Scanner s = new Scanner(System.in);
        Datafacade facade = new Datafacade();
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
                        facade.addAnswerToData(answer,mainData);
                        break;
                    case 3:
                        System.out.println("Enter the question number that you would like to edit :");
                        DataBase.printQuestions(mainData.getAllQuestions());
                        int indexOfQuestion = s.nextInt();
                        s.nextLine();
                        Question selectedQuestion = mainData.getQuestion(indexOfQuestion);
                        if (selectedQuestion instanceof OpenQuestion) {
                            throw new Exception("You cannot add an answer to a open question");
                        } else {
                            System.out.println("Enter the index of the answer that you would like to add :");
                            DataBase.printAnswers(mainData.getAllAnswers());
                            int indexOfAnswer = s.nextInt();
                            s.nextLine();
                            Answer selectedAnswer = mainData.getAnswer(indexOfAnswer);
                            if (selectedAnswer == null){
                                throw new Exception("Index of answer not valid");
                            }
                            System.out.println("Is this answer Correct ? [True/False]");
                            String inputCorrect = s.nextLine();
                            boolean isCorrect;
                            switch (inputCorrect.toLowerCase()){
                                case "true":
                                    isCorrect = true;
                                    break;
                                case "false":
                                    isCorrect = false;
                                    break;
                                default:
                                    throw new Exception("Incorrect answer");
                                    break;
                            }
                            facade.addAnswerToQuestion(selectedQuestion.getQuestionID(), selectedAnswer.getAnswerID(), mainData);
                            System.out.println("Answer was added to question successfully");
                        }
                        break;
                    case 4:
                        System.out.println("Enter the question that you would like to add:");
                        String question = s.nextLine();
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
                        facade.addQuestionToData();
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