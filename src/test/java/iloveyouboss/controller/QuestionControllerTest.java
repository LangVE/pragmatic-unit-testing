/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package iloveyouboss.controller;

import iloveyouboss.domain.Question;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class QuestionControllerTest {

   private QuestionController controller;

   @Test
   public void questionAnswersDateAdded() {
      // given
      controller = new QuestionController();
      Instant now = new Date().toInstant();
      controller.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
      int id = controller.addBooleanQuestion("text");

      // when
      Question question = controller.find(id);

      // then
      assertThat(question.getCreateTimestamp(), equalTo(now));
   }
}
