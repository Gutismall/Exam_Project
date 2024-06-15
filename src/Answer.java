import java.io.Serializable;

public class Answer implements Serializable {
	private String answer;
	private boolean correct;
	private static int answerCounter ;
	private int answerID;
	
	public int getAnswerID() {
		return answerID;
	}
	
	public Answer(String ans) {
		this.answer = ans;
		this.answerID = ++answerCounter;
		this.correct = false;
	}
	public Answer(String ans,boolean correct) {
		this.answer = ans;
		this.correct = correct;
	}
	public Answer(Answer other) {
		this.answer = other.answer;
		this.correct = other.correct;
	}
	public String getAnswer() {
		return this.answer;
	}
	public void setAnswer(String ans) {
		this.answer = ans;
	}
	public boolean isCorrect() {
		return correct;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
	
	public String toString() {
		return this.answer;
	}
	
	public boolean equals(Answer answer) {
		return answer.getAnswer().equals(this.answer);
	}
}
	