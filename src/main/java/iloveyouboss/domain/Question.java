/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
 ***/
package iloveyouboss.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public abstract class Question implements Serializable {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String text;
   private Instant instant;

   public Question() {
   }

   public Question(String text) {
      this.text = text;
   }

   abstract public List<String> getAnswerChoices();

   abstract public boolean match(int expected, int actual);

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getText() {
      return text;
   }

   public void setText(String text) {
      this.text = text;
   }

   @Override
   public String toString() {
      StringBuilder s = new StringBuilder("Question #" + getId() + ": " + getText());
      getAnswerChoices().stream().forEach((choice) -> s.append("\t" + choice));
      return s.toString();
   }

   public boolean match(Answer answer) {
      return false;
   }

   public String getAnswerChoice(int i) {
      return getAnswerChoices().get(i);
   }

   public int indexOf(String matchingAnswerChoice) {
      for (int i = 0; i < getAnswerChoices().size(); i++)
         if (getAnswerChoice(i).equals(matchingAnswerChoice))
            return i;
      return -1;
   }

   public Instant getCreateTimestamp() {
      return instant;
   }

   public void setCreateTimestamp(Instant instant) {
      this.instant = instant;
   }
}
