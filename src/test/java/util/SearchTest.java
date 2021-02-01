package util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
        stream = streamOn("There are certain queer times and occasions "
                + "in this strange mixed affair we call life when a man "
                + "takes this whole universe for a vast practical joke, "
                + "though the wit thereof he but dimly discerns, and more "
                + "than suspects that the joke is at nobody's expense but "
                + "his own.");

        Search search = new Search(stream, "practical joke", A_TITLE);
        search.setSurroundingCharacterCount(10);
        search.execute();
        assertFalse(search.errored());
        assertThat(search.getMatches(), containsMatches(new Match[]{
                new Match(A_TITLE, "practical joke",
                        "or a vast practical joke, though t")
        }));
    }

    @Test
    public void noMatchesReturnedWhenSearchStringNotInContent() throws IOException {
        URLConnection connection =
                new URL("http://bit.ly/15sYPA7").openConnection();
        stream = connection.getInputStream();
        Search search = new Search(stream, "smelt", A_TITLE);
        search.execute();
        assertTrue(search.getMatches().isEmpty());
    }

    private InputStream streamOn(String pageContent) {
        return new ByteArrayInputStream(pageContent.getBytes());
    }
}
