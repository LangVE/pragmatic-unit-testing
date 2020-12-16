package iloveyouboss.controller;

import iloveyouboss.domain.BooleanQuestion;
import iloveyouboss.domain.Question;

import java.time.Clock;

// 예제의 하이버네이트 부분은 덜어내고 동작만 가능하게 변경하였다.
public class QuestionController {
   private Clock clock = Clock.systemUTC();

   public Question find(Integer id) {
      Question question = new BooleanQuestion();
      question.setId(id);
      question.setCreateTimestamp(clock.instant());

      return question;
   }

   public int addBooleanQuestion(String text) {
      return 1;
   }

   void setClock(Clock clock) {
      this.clock = clock;
   }
}
