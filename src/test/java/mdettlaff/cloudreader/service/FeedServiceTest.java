package mdettlaff.cloudreader.service;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;

import org.junit.Before;
import org.junit.Test;

public class FeedServiceTest {

	private FeedService service;

	@Before
	public void setUp() {
		service = new FeedService();
	}

	@Test
	public void testGetFeeds() throws Exception {
		assertEquals(1, service.getFeeds().size());
	}

	@Test
	public void testParseFeed() throws Exception {
		InputStream feedSource = getClass().getResourceAsStream("qc_rss.xml");
		Feed feed = service.parseFeed(feedSource);
		assertEquals("Queen Corner online", feed.getTitle());
		assertEquals("http://queencorner.ovh.org", feed.getLink());
		assertEquals(25, feed.getItems().size());
		FeedItem item = feed.getItems().get(4);
		assertEquals("Queen w filmie Sucker Punch", item.getTitle());
		assertEquals("W filmie Sucker Punch zosta³ u¿yty mashup dwóch piosenek Queen WWRY i I want it all autorstwa rapera <b>Terror Squad i Armageddon.</b>", item.getDescription());
	}
}
