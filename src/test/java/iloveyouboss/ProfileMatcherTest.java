package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ProfileMatcherTest {
   private BooleanQuestion question;
   private Criteria criteria;
   private ProfileMatcher matcher;
   private Profile matchingProfile;
   private Profile nonMatchingProfile;
   private MatchListener listener;

   @Before
   public void create() {
      question = new BooleanQuestion(1, "");
      criteria = new Criteria();
      criteria.add(new Criterion(matchingAnswer(), Weight.MustMatch));
      matchingProfile = createMatchingProfile("matching");
      nonMatchingProfile = createNonMatchingProfile("nonMatching");
   }

   private Profile createMatchingProfile(String name) {
      Profile profile = new Profile(name);
      profile.add(matchingAnswer());
      return profile;
   }

   private Profile createNonMatchingProfile(String name) {
      Profile profile = new Profile(name);
      profile.add(nonMatchingAnswer());
      return profile;
   }

   @Before
   public void createMatcher() {
      matcher = new ProfileMatcher();
   }

   @Test
   public void collectsMatchSets() {
      matcher.add(matchingProfile);
      matcher.add(nonMatchingProfile);

      List<MatchSet> sets = matcher.collectMatchSets(criteria);

      assertThat(sets.stream().map(set -> set.getProfileId()).collect(Collectors.toSet()),
              equalTo(new HashSet<>(Arrays.asList(matchingProfile.getId(), nonMatchingProfile.getId()))));
   }

   @Before
   public void createMatchListener() {
      listener = mock(MatchListener.class); // (1)
   }

   @Test
   public void processNotifiesListenerOnMatch() {
      matcher.add(matchingProfile);  // (2)
      MatchSet set = matchingProfile.getMatchSet(criteria); // (3)

      matcher.process(listener, set); // (4)

      verify(listener).foundMatch(matchingProfile, set); // (5)
   }

   @Test
   public void processDoesNotNotifyListenerWhenNoMatch() {
      matcher.add(nonMatchingProfile);
      MatchSet set = nonMatchingProfile.getMatchSet(criteria);

      matcher.process(listener, set);

      verify(listener, never()).foundMatch(nonMatchingProfile, set);
   }

   private Answer matchingAnswer() {
      return new Answer(question, Bool.TRUE);
   }

   private Answer nonMatchingAnswer() {
      return new Answer(question, Bool.FALSE);
   }
}
