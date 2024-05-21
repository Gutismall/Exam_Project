import java.io.*;
import java.util.*;

public class DataBase implements Serializable {
    private HashSet<Answer> allAnswers = new HashSet<>();
    private HashSet<Question> allQuestions = new HashSet<>();
    private static DataBase instance;
    private DataBase(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream savedFile = new ObjectInputStream(new FileInputStream(fileName+".dat"));
        try{
            this.allQuestions = (HashSet<Question>) savedFile.readObject();
        }catch (Exception e){
            savedFile.close();
            return;
        }
        savedFile.close();
        for (Question qsn : allQuestions){
            if (qsn instanceof ClosedQuestion){
                for (int j = 0 ; j < ((ClosedQuestion)allQuestions[i]).getAnswer().length;j++){
                    if (((ClosedQuestion)allQuestions[i]).getAnswer()[j].toString().equals("More than one is correct") || ((ClosedQuestion)allQuestions[i]).getAnswer()[j].toString().equals("All wrong")){
                        continue;
                    }
                    addAnswerToData(((ClosedQuestion)allQuestions[i]).getAnswer()[j].toString());
                }
            }

        }
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

    public void addAnswerToData(Answer answer) {
        this.allAnswers.add(answer);
    }
    public HashSet<Answer> getAllAnswers() {
        return allAnswers;
    }
    public HashSet<Question> getAllQuestions() {
        return allQuestions;
    }

    public void addQuestionToData(Question q) {
        this.allQuestions.add(q);
    }
    public void removeQuestionFromData(int questionNumber) {
        Iterator<Question> questionIterator = this.allQuestions.iterator();
        for (Question qsn : this.allQuestions){
            if (qsn.getQuestionNum() == questionNumber){
                this.allQuestions.remove(qsn);
                break;
            }
        }
    }
    public static void printQuestions(HashSet<Question> allQuestions) {
        if (allQuestions.size() == 0) {
            System.out.println("There is no questions in the data");
        } else {
            for (Question qsn : allQuestions) {
                System.out.println(qsn.getQuestionNum() + ")" + qsn.toString());
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
    public static void printAnswers(HashSet<Answer> allAnswers) {
        if (allAnswers.size() == 0) {
            System.out.println("There is no answers in the data\n");
        } else {
            for (Answer ans : allAnswers) {
                System.out.println(ans.toString() + "\n");
            }
        }
    }
}
