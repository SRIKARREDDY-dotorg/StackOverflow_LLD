package org.parking.lot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Question implements Commentable, Votable {
    private final int id;
    private final String title;
    private final String content;
    private final User author;
    private final Date creationDate;
    private final List<Answer> answers;
    private final List<Tag> tags;
    private final List<Comment> comments;
    private final List<Vote> votes;

    public Question(User author, String title, String content, List<String> tagNames) {
        this.id = generateId();
        this.title = title;
        this.content = content;
        this.author = author;
        creationDate = new Date();
        answers = new ArrayList<>();
        comments = new ArrayList<>();
        votes = new ArrayList<>();
        tags = new ArrayList<>();
        for(String tagName: tagNames) {
            this.tags.add(new Tag(tagName));
        }
    }

    @Override
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addAnswer(Answer answer) {
        if(!answers.contains(answer)) {
            answers.add(answer);
        }
    }

    @Override
    public List<Comment> getComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public void vote(User user, int value) {
        if(value != -1 && value != 1) {
            throw new IllegalArgumentException("vote value must be -1 or +1 ");
        }

        votes.removeIf(vote -> vote.getUser().equals(user));
        votes.add(new Vote(user, value));
        author.updateReputation(value*5);
    }

    @Override
    public int getVoteCount() {
        return votes.stream().mapToInt(Vote::getValue).sum();
    }

    private int generateId() {
        return (int) (System.currentTimeMillis()%Integer.MAX_VALUE);
    }

    // getters
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public List<Tag> getTags() {
        return tags;
    }
    public List<Answer> getAnswers() {
        return answers;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public User getAuthor() {
        return author;
    }
}
