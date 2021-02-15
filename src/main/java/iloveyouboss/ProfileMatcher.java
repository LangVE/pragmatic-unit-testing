package iloveyouboss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ProfileMatcher {
   private static final int DEFAULT_POOL_SIZE = 4;
   private Map<String, Profile> profiles = new HashMap<>();

   public void add(Profile profile) {
      profiles.put(profile.getId(), profile);
   }


   public void findMatchingProfiles(
           Criteria criteria, MatchListener listener) {
      ExecutorService executor =
              Executors.newFixedThreadPool(DEFAULT_POOL_SIZE);

      for (MatchSet set : collectMatchSets(criteria)) {
         Runnable runnable = () -> {
            process(listener, set);
         };
         executor.execute(runnable);
      }
      executor.shutdown();
   }

   void process(MatchListener listener, MatchSet set) {
      if (set.matches())
         listener.foundMatch(profiles.get(set.getProfileId()), set);
   }

   List<MatchSet> collectMatchSets(Criteria criteria) {
      List<MatchSet> matchSets = profiles.values().stream()
              .map(profile -> profile.getMatchSet(criteria))
              .collect(Collectors.toList());
      return matchSets;
   }
}
