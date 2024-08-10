package org.parking.lot;

public interface Votable {
    void vote(User user, int value);
    int getVoteCount();
}
