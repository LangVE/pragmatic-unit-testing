package scratch;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AssertHamcrestTest {

    @Test
    public void compare_thenFail() {
        try {
            // given

            // when

            // then
            assertThat(2.32 * 3, equalTo(6.96));
        } catch (AssertionError e) {
            System.out.println("의도된 AssertError 발생");
            e.printStackTrace();
        }
    }

    @Test
    public void compare_thenPass() {
        // given

        // when

        // then
        assertTrue(((2.32 * 3) - 6.96) < 0.0005); // 가독성이 떨어진다
        assertThat(2.32 * 3, closeTo(6.96, 0.0005));
    }
}
