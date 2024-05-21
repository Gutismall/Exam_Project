import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class OpenQuestion extends Question implements Serializable {
	protected Answer answer;
	public OpenQuestion(String question,Difficulty difficulty,Answer answer) {
		super(question,difficulty);
		this.answer = answer;
	}
	public Answer getAnswer() {
		return this.answer;
	}
	public void setAnswer(String answer) {
		Answer ans = new Answer(answer);
		this.answer = ans;
	}
	public String toString(){
		return super.toString()+answer.toString()+"\n";
	}
	
	@Override
	public void addAnswerToQuestion(Answer answer) {
		System.out.println("Cant add answer to open question");
	}
}
