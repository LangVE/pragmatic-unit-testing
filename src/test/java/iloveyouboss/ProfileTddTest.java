package iloveyouboss;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileTddTest {
    @Test
    public void matchesNothingWhenProfileEmpty() {
        ProfileTdd profileTdd = new ProfileTdd();
        Question question = new BooleanQuestion(1, "Relocation package");
        Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);

        boolean result = profileTdd.matches(criterion);

        assertFalse(result);
    }

    @Test
    public void matchesWhenProfileContainsMatchingAnswer() {
        ProfileTdd profileTdd = new ProfileTdd();
        Question question = new BooleanQuestion(1, "Relocation package?");
        Answer answer = new Answer(question, Bool.TRUE);
        profileTdd.add(answer);
        Criterion criterion = new Criterion(answer, Weight.DontCare);

        boolean result = profileTdd.matches(criterion);

        assertTrue(result);
    }
}
