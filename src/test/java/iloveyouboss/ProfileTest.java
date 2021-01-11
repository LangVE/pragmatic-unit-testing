package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    private Question questionReimbursesTuition;
    private Answer answerReimbursesTuition;
    private Answer answerDoesNotReimburseTuition;

    private Question questionIsThereRelocation;
    private Answer answerThereIsRelocation;
    private Answer answerThereIsNoRelocation;

    private Question questionOnsiteDaycare;
    private Answer answerNoOnsiteDaycare;
    private Answer answerHasOnsiteDaycare;

    @Before
    public void createProfile() {
        profile = new Profile("Bull Hockey, Inc.");
    }

    @Before
    public void createCriteria() {
        criteria = new Criteria();
    }

    @Before
    public void createQuestionsAndAnswers() {
        questionIsThereRelocation =
                new BooleanQuestion(1, "Relocation package?");
        answerThereIsRelocation =
                new Answer(questionIsThereRelocation, Bool.TRUE);
        answerThereIsNoRelocation =
                new Answer(questionIsThereRelocation, Bool.FALSE);

        questionReimbursesTuition =
                new BooleanQuestion(1, "Reimburses tuition?");
        answerReimbursesTuition =
                new Answer(questionReimbursesTuition, Bool.TRUE);
        answerDoesNotReimburseTuition =
                new Answer(questionReimbursesTuition, Bool.FALSE);

        questionOnsiteDaycare =
                new BooleanQuestion(1, "Onsite daycare?");
        answerHasOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.TRUE);
        answerNoOnsiteDaycare =
                new Answer(questionOnsiteDaycare, Bool.FALSE);
    }

    int[] ids(Collection<Answer> answers) {
        return answers.stream()
                .mapToInt(a -> a.getQuestion().getId()).toArray();
    }

    @Test
    public void findsAnswersBasedOnPredicate() {
        // given
        profile.add(new Answer(new BooleanQuestion(1, "1"), Bool.FALSE));
        profile.add(new Answer(new PercentileQuestion(2, "2", new String[]{}), 0));
        profile.add(new Answer(new PercentileQuestion(3, "3", new String[]{}), 0));

        // when
        List<Answer> answers = profile.find(a -> a.getQuestion().getClass() == PercentileQuestion.class);

        // then
        assertThat(ids(answers), equalTo(new int[]{2, 3}));

        // given
        List<Answer> answersComplement =
                profile.find(a -> a.getQuestion().getClass() != PercentileQuestion.class);

        // when
        List<Answer> allAnswers = new ArrayList<Answer>();
        allAnswers.addAll(answersComplement);
        allAnswers.addAll(answers);

        // then
        assertThat(ids(allAnswers), equalTo(new int[]{1, 2, 3}));
    }

    private long run(int times, Runnable func) {
        long start = System.nanoTime();
        for (int i = 0; i < times; i++)
            func.run();
        long stop = System.nanoTime();
        return (stop - start) / 1000000;
    }

    @Test
    public void findAnswers() {
        int dataSize = 5000;
        for (int i = 0; i < dataSize; i++) {
            profile.add(new Answer(new BooleanQuestion(i, String.valueOf(i)), Bool.FALSE));
        }
        profile.add(new Answer(
                new PercentileQuestion(
                        dataSize, String.valueOf(dataSize), new String[]{}), 0));

        int numberOfTimes = 1000;
        long elapsedMs = run(numberOfTimes,
                () -> profile.find(
                        a -> a.getQuestion().getClass() == PercentileQuestion.class));

        assertTrue(elapsedMs < 1000);
    }

    @Before
    public void create() {
        profile = new Profile("Bull Hockey, Inc.");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    @Test
    public void matchAnswerFalseWhenMustMatchCriteriaNotMet() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        // when
        boolean matches = profile.getMatchSet(criteria).matches();

        // then
        assertFalse(matches);
    }

    @Test
    public void matchAnswerTrueForAnyDontCareCriteria() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        // when
        boolean matches = profile.getMatchSet(criteria).matches();

        // then
        assertTrue(matches);
    }

    @Test
    public void matchAnswersTrueWhenAnyOfMultipleCriteriaMatch() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimbursesTuition, Weight.Important));

        boolean matches = profile.getMatchSet(criteria).matches();

        assertTrue(matches);
    }

    @Test
    public void matches() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        // when
        boolean matches = profile.getMatchSet(criteria).matches();

        // then
        assertFalse(matches);

        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria = new Criteria(); // 이전 코드 초기화
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        // when
        matches = profile.getMatchSet(criteria).matches();

        // then
        assertTrue(matches);
    }
}