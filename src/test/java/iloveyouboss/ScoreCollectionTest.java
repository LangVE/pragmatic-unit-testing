package iloveyouboss;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ScoreCollectionTest {
    ScoreCollection collection;

    @Before
    public void setUp() {
        collection = new ScoreCollection();
    }

    @Test
    public void answerArithmeticMeanOfTwoNumbers() {
        // given
        collection.add(() -> 5);
        collection.add(() -> 7);

        // when
        int actual = collection.arithmeticMean();

        // then
        assertThat(actual, equalTo(6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionWhenAddingNull() {
        // given
        Scoreable nullable = null;

        // when
        collection.add(nullable);

        // then
        // throw
    }

    @Test
    public void answersZeroWhenNoElementsAdded() {
        // given
        // nothing

        // when
        int actual = collection.arithmeticMean();

        // then
        assertThat(actual, equalTo(0));
    }

    @Test
    public void dealsWithIntegerOverflow() {
        // given
        collection.add(() -> Integer.MAX_VALUE);
        collection.add(() -> 1);

        // when
        int actual = collection.arithmeticMean();

        // then
        assertThat(actual, equalTo(1073741824));
    }
}
