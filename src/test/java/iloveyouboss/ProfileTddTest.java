package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class ProfileTddTest {

    private ProfileTdd profileTdd;
    private Question question;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNotRelocation;
    private BooleanQuestion questionReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;
    private Answer answerReimbursesTuition;
    private Criteria criteria;

    @Before
    public void createProfile() {
        profileTdd = new ProfileTdd();
    }

    @Before
    public void createQuestionAndAnswer() {
        question = new BooleanQuestion(1, "Relocation package");
        answerThereIsRelocation = new Answer(question, Bool.TRUE);
        answerThereIsNotRelocation = new Answer(question, Bool.FALSE);
        questionReimbursesTuition = new BooleanQuestion(1, "Reimburses tuition?");
        answerDoesNotReimburseTuition =
                new Answer(questionReimbursesTuition, Bool.FALSE);
        answerReimbursesTuition =
                new Answer(questionReimbursesTuition, Bool.TRUE);
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

    @Test
    public void doesNotMatchWhenNoMatchingAnswer() {
        profileTdd.add(answerThereIsNotRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);

        boolean result = profileTdd.matches(criterion);

        assertFalse(result);
    }

    @Test
    public void matchesWhenContainsMultipleAnswer() {
        profileTdd.add(answerThereIsRelocation);
        profileTdd.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);

        boolean result = profileTdd.matches(criterion);

        assertTrue(result);
    }

    @Before
    public void createCriteria() {
        criteria = new Criteria();
    }

    @Test
    public void doesNotMatchWhenNoneOfMultipleCriteriaMatch() {
        profileTdd.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        assertFalse(profileTdd.matches(criteria));
    }

    @Test
    public void matchesWhenAnyOfMultipleCriteriaMatch() {
        profileTdd.add(answerThereIsRelocation);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        assertTrue(profileTdd.matches(criteria));
    }

    @Test
    public void matchesWhenCriterionIsDontCare() {
        profileTdd.add(answerDoesNotReimburseTuition);
        Criterion criterion =
                new Criterion(answerReimbursesTuition, Weight.DontCare);

        assertTrue(profileTdd.matches(criterion));
    }

    @Test
    public void scoreIsZeroWhenThereAreNoMatches() {
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        ProfileMatch match = profileTdd.match(criteria);

        assertThat(match.getScore(), equalTo(0));
    }
}
