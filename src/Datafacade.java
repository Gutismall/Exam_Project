import java.util.*;

public class Datafacade {
    public Scanner s = new Scanner(System.in);
    public void addAnswerToData(String newAnswer,DataBase data){
        data.addAnswerToData(new Answer(newAnswer));
    }
    public void addQuestionToData(String newQuestion,QuestionType type,Difficulty qDifficulty,DataBase data) throws Exception {
        switch (type) {
            case QuestionType.Closed:
                data.addQuestionToData(new ClosedQuestion(newQuestion, qDifficulty, null));
                break;
            case QuestionType.Open:
                System.out.println("Enter the answer for that question:");
                String openAnswer = s.nextLine();
                Answer newAnswer = new Answer(openAnswer);
                data.addQuestionToData(new OpenQuestion(newQuestion,qDifficulty,newAnswer));
                data.addAnswerToData(newAnswer);
                break;
        }
        System.out.println("Question added successfully");
    }
    public void removeAnswerFromData(int answerId,DataBase data){
        data.removeAnswerFromData(answerId);
    }
    //check that the answer itself doesnt get removed only the pointer
    public  void removeQuestionFromData(int questionId,DataBase data){
        data.removeQuestionFromData(questionId);
    }
    public void addAnswerToQuestion(int questionId,int answerId,DataBase data){
        Question question = data.getQuestion(questionId);
        if (question instanceof OpenQuestion){
            System.out.println("Can't add answer to open question");
            return;
        }
        else {
            if (((ClosedQuestion) question).getAnswer().size() >= 6){
                System.out.println("Closed Question is limited to 6 answers");
                return;
            }
            else {
                Answer answer = data.getAnswer(answerId);
                System.out.println("Is this answer correct for this question ?");
                boolean correct = s.nextBoolean();
                ((ClosedQuestion) question).getAnswer().put(answer,correct);
            }
        }
    }
    public void removeAnswerFromQuestion(int questionId,DataBase data){
        Question question = data.getQuestion(questionId);
        if (question instanceof OpenQuestion){
            System.out.println("Cannot remove answers from open questions");
            return;
        }
        else {
            System.out.println("Enter answer ID that you would like to remove:");
            ((ClosedQuestion)question).printAnswersFromQuestion();
            int key = s.nextInt();
            ((ClosedQuestion)question).getAnswer().remove(key);
        }
    
    }
    
}
