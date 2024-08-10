package org.parking.lot;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private final String username;
    private final String email;
    private int reputation;
    private final List<Answer> answers;
    private final List<Question> questions;
    private final List<Comment> comments;

    private static final int QUESTION_REPUTATION = 5;
    private static final int ANSWER_REPUTATION = 10;
    private static final int COMMENT_REPUTATION = 2;

    public User(int id, String username, String email) {
        this.id = id;
        this.answers = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.email = email;
        this.username = username;
    }

    public Question askQuestion(String title, String content, List<String> tags) {
        Question question = new Question(this, title, content, tags);
        questions.add(question);
        updateReputation(QUESTION_REPUTATION);
        return question;
    }

    public Answer answerQuestion(Question question, String content) {
        Answer answer = new Answer(this, content, question);
        answers.add(answer);
        question.addAnswer(answer);
        updateReputation(ANSWER_REPUTATION);
        return answer;
    }

    public Comment addComment(String context, Commentable commentable) {
        Comment comment = new Comment(this, context);
        commentable.addComment(comment);
        comments.add(comment);
        updateReputation(COMMENT_REPUTATION);
        return comment;
    }

    public synchronized void updateReputation(int value) {
        reputation += value;
        if(reputation < 0) reputation = 0;
    }

    // getters
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public List<Answer> getAnswers() {
        return answers;
    }
    public List<Comment> getComments() {
        return comments;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public int getReputation() {
        return reputation;
    }
}
