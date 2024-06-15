

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

public class ClosedQuestion extends Question implements Cloneable, Serializable {
    protected HashMap<Answer,Boolean> answers;

    public ClosedQuestion(String question, Difficulty difficulty, HashMap answers) throws Exception {
        super(question, difficulty);
        this.answers = answers;
        }
    

//    public ClosedQuestion(Scanner s, int numOfAnswers, String question, Difficulty difficulty) throws FileNotFoundException {
//        super(question, difficulty);
//        this.answers = s.next();
//        this.correctAns = new boolean[0];
//        for (int i = 0; i < numOfAnswers; i++) {
//            Answer ans = new Answer(s.nextLine());
//            boolean isCorrect = s.nextBoolean();
//            addAnswer(ans, isCorrect);
//            s.nextLine();
//        }
//    }
//    public ClosedQuestion(ClosedQuestion other){
//        super(other.question, other.difficultyOfQuestion);
//        this.answers = new HashMap<>();
//        for (Map.Entry<Answer,Boolean> answer: other.getAnswer().entrySet()){
//            this.answers.put()
//        }
//    }
    public HashMap<Answer,Boolean> getAnswer() {
        return this.answers;
    }

    public void setAnswer(HashMap<Answer,Boolean> answers) {
        this.answers = answers;
    }
    public void moreThenOneCorrectOrNoCorrect() {
        int counter = 0;
        for (boolean correct : this.answers.values()) {
            if (correct == true)
                counter++;
        }
        if (counter >= 2 || counter == 0) {
            for (Map.Entry<Answer, Boolean> entry : answers.entrySet()) {
                entry.setValue(false);
            }
            if (counter >= 2){
                this.answers.put(new Answer("More the one correct"),true);
            }
            else
                this.answers.put(new Answer("No answer is correct"),true);
        }
    }
    
    @Override
    public String toString() {
        String res = super.toString();
        for (Answer ans : this.answers.keySet()){
            res += ans.toString();
        }
        return res;
    }
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    public void printAnswersFromQuestion(){
        for (Answer ans : this.answers.keySet()){
            System.out.println(ans.toString() + "[" + ans.getAnswerID() + "]");
        }
    }
}
