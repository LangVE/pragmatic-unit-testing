package scratch;

import org.junit.Test;

import static java.lang.Math.abs;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

public class NewtonTest {
    static class Newton {
        private static final double TOLERANCE = 1E-16;

        public static double squareRoot(double n) {
            double approx = n;
            while (abs(approx - n / approx) > TOLERANCE * approx)
                approx = (n / approx + approx) / 2.0;
            return approx;
        }
    }

    @Test
    public void squareRoot() {
        // given
        double value = 250.0;

        // when
        double result = Newton.squareRoot(value);

        // then
        assertThat(result * result, closeTo(value, Newton.TOLERANCE));
        assertThat(result, closeTo(Math.sqrt(value), Newton.TOLERANCE));
    }
}
