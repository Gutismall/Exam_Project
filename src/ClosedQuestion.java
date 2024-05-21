

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

public class ClosedQuestion extends Question implements Cloneable, Serializable {
    protected HashSet<Answer> answers = new HashSet<>();

    public ClosedQuestion(String question, Difficulty difficulty, HashSet answers) throws Exception {
        super(question, difficulty);
        if (answers.size() > 8 || answers.size() < 4) {
            throw new Exception("Number of Answers invalid,must be 4-8 answers");
        } else {
            this.answers = answers;
            Answer moreThanOneCorrect = new Answer("More than one is correct");
            Answer allWrong = new Answer("All wrong");
            moreThenOneCorrect(moreThanOneCorrect,allWrong);
            noCorrectAnswers(moreThanOneCorrect,allWrong);
        }
    }

    public ClosedQuestion(Scanner s, int numOfAnswers, String question, Difficulty difficulty) throws FileNotFoundException {
        super(question, difficulty);
        this.answers = new Answer[0];
        this.correctAns = new boolean[0];
        for (int i = 0; i < numOfAnswers; i++) {
            Answer ans = new Answer(s.nextLine());
            boolean isCorrect = s.nextBoolean();
            addAnswer(ans, isCorrect);
            s.nextLine();
        }
    }
    public ClosedQuestion(ClosedQuestion other){
        super(other.question, other.difficultyOfQuestion);
        this.answers = new HashSet<>();
        for (Answer ans : other.getAnswer()){
            this.answers.add(new Answer(ans));
        }
    }
    public HashSet<Answer> getAnswer() {
        return answers;
    }

    public void setAnswer(HashSet<Answer> answers) {
        this.answers = answers;
    }
    public void moreThenOneCorrect() {
        int counter = 0;

        for (Answer ans : this.answers) {
            if (ans.isCorrect() == true)
                counter++;
        }
        if (counter >= 2) {
            addAnswerToQuestion(new Answer("More then one is Correct",true));
        }
    }

    public void noCorrectAnswers(Answer moreThanOneCorrect,Answer allWrong) {
        for (int i = 0; i < this.answers.length; i++) {
            if (this.correctAns[i] == true)
                return;
        }
        boolean[] updatedCorrect = new boolean[this.correctAns.length];
        for (int j = 0; j < updatedCorrect.length; j++) {
            updatedCorrect[j] = false;
        }
        setCorrect(updatedCorrect);
        addAnswer(allWrong, true);
        addAnswer(moreThanOneCorrect, false);
    }
    @Override
    public String toString() {
        String res = super.toString();
        for (Answer ans : this.answers){
            res += ans.toString();
        }
        return res;
    }
    
    @Override
    public void addAnswerToQuestion(Answer answer) {
        this.answers.add(answer);
    }
    
    public void removeAnswerFromQuestion(int indexOfAnswer) {
        Iterator<Answer> answerIterator = this.answers.iterator();
        for (int i = 0; i < indexOfAnswer - 1; i++) {
            answerIterator.next();
        }
        this.answers.remove(answerIterator.next());
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
