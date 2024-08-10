package org.parking.lot;

import java.util.Arrays;
import java.util.List;

public class StackOverflowDemo {
    public static void run() {
        StackOverflow system = new StackOverflow();

        // create users
        User alice = system.createUser("Alice", "alice@example.com");
        User bob = system.createUser("Bob", "bob@example.com");
        User charlie = system.createUser("charlie", "charlie@example.com");

        Question javaQuestion = system.askQuestion(alice, "What is polymorphism in java?", "Can anyone explain polymorphism in java", Arrays.asList("java", "oop"));
        Answer bobAnswer = system.answerQuestion(bob, "Polymorphism is an ability to take on many forms", javaQuestion);

        system.addComment(charlie, "Great question! I am interested in learning more", javaQuestion);
        system.addComment(alice, "Thanks for the explanation, can you provide the example of code", bobAnswer);

        system.voteQuestion(charlie, javaQuestion, 1);
        system.voteAnswer(charlie, bobAnswer, 1);

        system.acceptAnswer(bobAnswer);

        // Bob asks another question
        Question pythonQuestion = system.askQuestion(bob, "How to use list comprehensions in Python?",
                "I'm new to Python and I've heard about list comprehensions. Can someone explain how to use them?",
                Arrays.asList("python", "list-comprehension"));

        // Alice answers Bob's question
        Answer aliceAnswer = system.answerQuestion(alice,
                "List comprehensions in Python provide a concise way to create lists...", pythonQuestion);

        // Charlie votes on Bob's question and Alice's answer
        system.voteQuestion(charlie, pythonQuestion, 1);  // Upvote
        system.voteAnswer(charlie, aliceAnswer, 1);  // Upvote

        // Print out the current state
        System.out.println("Question: " + javaQuestion.getTitle());
        System.out.println("Asked by: " + javaQuestion.getAuthor().getUsername());
        System.out.println("Tags: " + javaQuestion.getTags().stream().map(Tag::getName).reduce((a, b) -> a + ", " + b).orElse(""));
        System.out.println("Votes: " + javaQuestion.getVoteCount());
        System.out.println("Comments: " + javaQuestion.getComments().size());
        System.out.println("\nAnswer by " + bobAnswer.getAuthor().getUsername() + ":");
        System.out.println(bobAnswer.getContent());
        System.out.println("Votes: " + bobAnswer.getVoteCount());
        System.out.println("Accepted: " + bobAnswer.isAccepted());
        System.out.println("Comments: " + bobAnswer.getComments().size());

        System.out.println("\nUser Reputations:");
        System.out.println("Alice: " + alice.getReputation());
        System.out.println("Bob: " + bob.getReputation());
        System.out.println("Charlie: " + charlie.getReputation());

        System.out.println("\nSearch Results for 'java':");
        List<Question> searchResults = system.searchQuestions("java");
        for (Question q : searchResults) {
            System.out.println(q.getTitle());
        }

        System.out.println("\nSearch Results for 'python':");
        searchResults = system.searchQuestions("python");
        for (Question q : searchResults) {
            System.out.println(q.getTitle());
        }

        // Demonstrate getting questions by user
        System.out.println("\nBob's Questions:");
        List<Question> bobQuestions = system.getQuestionsByUser(bob);
        for (Question q : bobQuestions) {
            System.out.println(q.getTitle());
        }
    }
}
