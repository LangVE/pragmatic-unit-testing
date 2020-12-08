package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

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
        boolean matches = profile.matches(criteria);

        // then
        assertFalse(matches);
    }

    @Test
    public void matchAnswerTrueForAnyDontCareCriteria() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertTrue(matches);
    }

    @Test
    public void matches() {
        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.MustMatch));

        // when
        boolean matches = profile.matches(criteria);

        // then
        assertFalse(matches);

        // given
        profile.add(new Answer(question, Bool.FALSE));
        criteria = new Criteria(); // 이전 코드 초기화
        criteria.add(new Criterion(new Answer(question, Bool.TRUE), Weight.DontCare));

        // when
        matches = profile.matches(criteria);

        // then
        assertTrue(matches);
    }
}