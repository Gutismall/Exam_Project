import java.io.*;
import java.util.*;

public class DataBase implements Serializable {
    private HashMap<Integer,Answer> allAnswers = new HashMap<>();
    private HashMap<Integer,Question> allQuestions = new HashMap<>();
    private static DataBase instance;
    private DataBase(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream savedFile = new ObjectInputStream(new FileInputStream(fileName+".dat"));
        try{
            this.allQuestions = (HashMap<Integer,Question>) savedFile.readObject();
        }catch (Exception e){
            savedFile.close();
            return;
        }
        savedFile.close();
    }
    public static DataBase getInstance(String fileName) throws IOException, ClassNotFoundException {
        if (DataBase.instance == null){
            DataBase.instance = new DataBase(fileName);
        }
        return DataBase.instance;
    }
    public void saveDataToFile(String fileName) throws IOException {
        ObjectOutputStream savedFile = new ObjectOutputStream(new FileOutputStream(fileName+".dat"));
        savedFile.writeObject(this.allQuestions);
        savedFile.close();
    }
    public HashMap<Integer,Answer> getAllAnswers() {
        return allAnswers;
    }
    public HashMap<Integer,Question> getAllQuestions() {
        return allQuestions;
    }

    public void addQuestionToData(Question qsn) {
        this.allQuestions.put(qsn.getQuestionID(),qsn);
    }
    public void addAnswerToData(Answer answer) {
        this.allAnswers.put(answer.getAnswerID(),answer);
    }
    public void removeQuestionFromData(int questionNumber) {
        this.allQuestions.remove(questionNumber);
    }
    public void removeAnswerFromData(int answerNumber) {
        this.allAnswers.remove(answerNumber);
    }
    public static void printQuestions(HashMap<Integer,Question> allQuestions) {
        if (allQuestions.size() == 0) {
            System.out.println("There is no questions in the data");
        } else {
            for (Question qsn : allQuestions.values()) {
                System.out.println(qsn.getQuestionID() + ")" + qsn.toString());
                if(qsn instanceof ClosedQuestion){
                    for (Answer ans : ((ClosedQuestion) qsn).getAnswer()) {
                        System.out.println(ans.toString() + " : " + ans.isCorrect() + "\n");
                    }
                }
                else {
                    System.out.println(((OpenQuestion) qsn).getAnswer() + "\n");
                }
            }
        }
    }
    public static void printAnswers(HashMap<Integer,Answer> allAnswers) {
        if (allAnswers.size() == 0) {
            System.out.println("There is no answers in the data\n");
        } else {
            for (Answer ans : allAnswers.values()) {
                System.out.println(ans.toString() + "\n");
            }
        }
    }
    public Question getQuestion(int questionNum){
        return this.allQuestions.get(questionNum);
    }
    
    public Answer getAnswer(int answerNum){
        return this.allAnswers.get(answerNum);
    }
    
}
