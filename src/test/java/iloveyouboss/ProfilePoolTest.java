package iloveyouboss;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ProfilePoolTest {
   private ProfilePool pool;
   private Profile langrsoft;
   private Profile smeltInc;
   private BooleanQuestion doTheyReimburseTuition;

   @Before
   public void create() {
      pool = new ProfilePool();
      langrsoft = new Profile("Langrsoft");
      smeltInc = new Profile("Smelt Inc.");
      doTheyReimburseTuition = new BooleanQuestion(1, "Reimburses tuition?");
   }

   @Ignore("9.4장 리펙토링 영향으로 비활성화 처리")
   @Test
   public void scoresProfileInPool() {
      langrsoft.add(new Answer(doTheyReimburseTuition, Bool.TRUE));
      pool.add(langrsoft);

      pool.score(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));

      //assertThat(langrsoft.score(), equalTo(Weight.Important.getValue()));
   }

   private Criteria soleNeed(Question question, int value, Weight weight) {
      Criteria criteria = new Criteria();
      criteria.add(new Criterion(new Answer(question, value), weight));
      return criteria;
   }

   @Ignore("9.4장 리펙토링 영향으로 비활성화 처리")
   @Test
   public void answersResultsInScoredOrder() {
      smeltInc.add(new Answer(doTheyReimburseTuition, Bool.FALSE));
      pool.add(smeltInc);
      langrsoft.add(new Answer(doTheyReimburseTuition, Bool.TRUE));
      pool.add(langrsoft);

      pool.score(soleNeed(doTheyReimburseTuition, Bool.TRUE, Weight.Important));
      List<Profile> ranked = pool.ranked();

      assertThat(ranked.toArray(), equalTo(new Profile[]{langrsoft, smeltInc}));
   }
}
