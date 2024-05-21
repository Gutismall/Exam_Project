import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public abstract class Question implements Serializable {
	protected String question;
	public static int counter;
	protected int questionNum = 0;
	protected Difficulty difficultyOfQuestion;

	public Question(String question,Difficulty difficultyOfQuestion) {
		this.difficultyOfQuestion = difficultyOfQuestion;
		this.question = question;
		this.questionNum = ++counter;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestion() {
		return this.question;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public Difficulty getDifficulty() {
		return this.difficultyOfQuestion;
	}

	public String toString() {
		return this.question+"\n"+this.difficultyOfQuestion.toString()+"\n";
	}
	
	public abstract void addAnswerToQuestion(Answer answer);
}
