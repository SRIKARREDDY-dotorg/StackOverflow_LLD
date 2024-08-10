package org.parking.lot;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class StackOverflow {
    private final Map<Integer, User> users;
    private final Map<Integer, Answer> answers;
    private final Map<Integer, Question> questions;
    private final Map<String, Tag> tags;

    public StackOverflow() {
        this.users = new ConcurrentHashMap<>();
        this.tags = new ConcurrentHashMap<>();
        this.answers = new ConcurrentHashMap<>();
        this.questions = new ConcurrentHashMap<>();
    }

    public User createUser(String username, String email) {
        int id = users.size() + 1;
        User user = new User(id, username, email);
        users.put(id, user);
        return user;
    }

    public Question askQuestion(User user, String title, String content, List<String> tags) {
        Question question = user.askQuestion(title, content, tags);
        questions.put(question.getId(), question);
        for(Tag tag: question.getTags()) {
            this.tags.putIfAbsent(tag.getName(), tag);
        }
        return question;
    }

    public Answer answerQuestion(User user, String content, Question question) {
        Answer answer = user.answerQuestion(question, content);
        answers.put(answer.getId(), answer);
        return answer;
    }

    public Comment addComment(User user, String content, Commentable commentable) {
        return user.addComment(content, commentable);
    }

    public void voteAnswer(User user, Answer answer, int value) {
        answer.vote(user, value);
    }

    public void voteQuestion(User user, Question question, int value) {
        question.vote(user, value);
    }

    public void acceptAnswer(Answer answer) {
        answer.markAsAccepted();
    }

    public List<Question> searchQuestions(String query) {
        return questions.values().stream().filter(question -> question.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                question.getContent().toLowerCase().contains(query.toLowerCase()) ||
                question.getTags().stream().anyMatch(tag -> tag.getName().equalsIgnoreCase(query))).collect(Collectors.toList());
    }

    public List<Question> getQuestionsByUser(User user) {
        return user.getQuestions();
    }

    // getters
    public User getUser(int id) {
        return users.get(id);
    }
    public Answer getAnswer(int id) {
        return answers.get(id);
    }
    public Question getQuestion(int id) {
        return questions.get(id);
    }
    public Tag getTag(String name) {
        return tags.get(name);
    }
}
