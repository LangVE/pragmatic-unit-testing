/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package iloveyouboss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Profile {
    private Map<String, Answer> answers = new HashMap<>();
    private int score;
    private String name;

    public Profile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void add(Answer answer) {
        answers.put(answer.getQuestionText(), answer);
    }

    public boolean matches(Criteria criteria) {
        calculateScore(criteria);

        if (doseNotMeetAnyMustMatchCriterion(criteria))
            return false;

        return anyMatches(criteria);
    }

    private boolean doseNotMeetAnyMustMatchCriterion(Criteria criteria) {
        boolean kill = false;

        for (Criterion criterion : criteria) {
            boolean match = criterion.matches(answerMatching(criterion));

            if (!match && criterion.getWeight() == Weight.MustMatch) {
                kill = true;
            }
        }
        return kill;
    }

    private void calculateScore(Criteria criteria) {
        score = 0;
        for (Criterion criterion : criteria) {
            if (criterion.matches(answerMatching(criterion))) {
                score += criterion.getWeight().getValue();
            }
        }
    }

    private boolean anyMatches(Criteria criteria) {
        boolean anyMatches = false;

        for (Criterion criterion : criteria) {
            anyMatches |= criterion.matches(answerMatching(criterion));
        }

        return anyMatches;
    }

    private Answer answerMatching(Criterion criterion) {
        return answers.get(
                criterion.getAnswer().getQuestionText());
    }

    public int score() {
        return score;
    }

    public List<Answer> find(Predicate<Answer> pred) {
        return answers.values().stream()
                .filter(pred)
                .collect(Collectors.toList());
    }
}
