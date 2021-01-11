package scratch;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static scratch.ConstrainsSidesTo.constrainsSidesTo;

public class RectangleTest {
   private Rectangle rectangle;

   @After
   public void ensureInvariant() {
      assertThat(rectangle, constrainsSidesTo(100));
   }

   @Test
   public void answersArea() {
      rectangle = new Rectangle(new Point(5, 5), new Point(15, 10));
      assertThat(rectangle.area(), equalTo(50));
   }

   @Ignore
   @Test
   public void allowsDynamicallyChangingSize() {
      rectangle = new Rectangle(new Point(5, 5));
      rectangle.setOppositeCorner(new Point(130, 130));
      assertThat(rectangle.area(), equalTo(15625));
   }
}
