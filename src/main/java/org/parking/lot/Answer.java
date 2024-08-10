package org.parking.lot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Answer implements Commentable, Votable{
    private final int id;
    private final String content;
    private final User author;
    private final List<Comment> comments;
    private final Question question;
    private boolean isAccepted;
    private final List<Vote> votes;
    private final Date creationDate;

    public Answer(User author, String content, Question question) {
        this.id = generateId();
        this.author = author;
        this.content = content;
        this.question = question;
        this.comments = new ArrayList<>();
        this.votes = new ArrayList<>();
        this.creationDate = new Date();
        isAccepted = false;
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void vote(User user, int value) {
        if(value != -1 && value != 1) {
            throw new IllegalArgumentException("vote value must be either 1 or -1");
        }
        votes.removeIf(vote -> vote.getUser().equals(user));
        votes.add(new Vote(user, value));
        author.updateReputation(value * 10);
    }

    @Override
    public int getVoteCount() {
        return votes.stream().mapToInt(Vote::getValue).sum();
    }

    public void markAsAccepted() {
        if(isAccepted) {
            throw new IllegalStateException("This answer is already accepted");
        }
        isAccepted = true;
        author.updateReputation(15);
    }

    private int generateId() {
        return (int) (System.currentTimeMillis()%Integer.MAX_VALUE);
    }

    // getters
    public int getId() {
        return id;
    }
    public String getContent() {
        return content;
    }
    public User getAuthor() {
        return author;
    }
    public Question getQuestion() {
        return question;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public boolean isAccepted() {
        return isAccepted;
    }
}
