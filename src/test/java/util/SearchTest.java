package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import static org.junit.Assert.*;
import static util.ContainsMatches.containsMatches;

public class SearchTest {
    private static final String A_TITLE = "1";
    private InputStream stream;

    @Before
    public void turnOffLogging() {
        Search.LOGGER.setLevel(Level.OFF);
    }

    @After
    public void closeResources() throws IOException {
        stream.close();
    }

    @Test
    public void returnsMatchesShowingContextWhenSearchStringInContent() throws IOException {
        stream = streamOn("rest of text here"
                + "1234567890search term1234567890"
                + "more rest of text");
        Search search = new Search(stream, "search term", A_TITLE);
        search.setSurroundingCharacterCount(10);

        search.execute();

        assertFalse(search.errored());
        assertThat(search.getMatches(), containsMatches(new Match[]{
                new Match(A_TITLE, "search term",
                        "1234567890search term1234567890")}));
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() {
        stream = streamOn("any text");
        Search search = new Search(stream, "text that doesn't match", A_TITLE);

        search.execute();

        assertTrue(search.getMatches().isEmpty());
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}
