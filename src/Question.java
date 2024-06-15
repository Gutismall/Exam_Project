import java.io.Serializable;

public abstract class Question implements Serializable {
	protected String question;
	protected static int counter;
	protected int questionID;
	protected Difficulty difficultyOfQuestion;

	public Question(String question,Difficulty difficultyOfQuestion) {
		this.difficultyOfQuestion = difficultyOfQuestion;
		this.question = question;
		this.questionID = ++counter;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestion() {
		return this.question;
	}
	public int getQuestionID() {
		return questionID;
	}
	public Difficulty getDifficulty() {
		return this.difficultyOfQuestion;
	}
	public String toString() {
		return this.question+"\n"+this.difficultyOfQuestion.toString()+"\n";
	}
	
}
