package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileTddTest {

    private ProfileTdd profileTdd;
    private Question question;
    private Answer answerThereIsRelocation;

    @Before
    public void createProfile() {
        profileTdd = new ProfileTdd();
    }

    @Before
    public void createQuestionAndAnswer() {
        question = new BooleanQuestion(1, "Relocation package");
        answerThereIsRelocation = new Answer(question, Bool.TRUE);
    }

    @Test
    public void matchesNothingWhenProfileEmpty() {
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profileTdd.matches(criterion);

        assertFalse(result);
    }

    @Test
    public void matchesWhenProfileContainsMatchingAnswer() {
        profileTdd.add(answerThereIsRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profileTdd.matches(criterion);

        assertTrue(result);
    }
}
