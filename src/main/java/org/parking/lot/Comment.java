package org.parking.lot;

import java.util.Date;

public class Comment {
    private final int id;
    private final User author;
    private final String content;
    private final Date creationDate;

    public Comment(User author, String content) {
        this.author = author;
        this.content = content;
        this.id = generateId();
        this.creationDate = new Date();
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
    public Date getCreationDate() {
        return creationDate;
    }
}
