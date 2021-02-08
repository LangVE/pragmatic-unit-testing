package iloveyouboss;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ProfileTddTest {
    @Test
    public void matchesNothingWhenProfileEmpty() {
        ProfileTdd profileTdd = new ProfileTdd();
        Question question = new BooleanQuestion(1, "Relocation package");
        Criterion criterion = new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare);

        boolean result = profileTdd.matches(criterion);

        assertFalse(result);
    }
}
