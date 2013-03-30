package mdettlaff.cloudreader.service;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import mdettlaff.cloudreader.domain.FeedItem;

import org.junit.Before;
import org.junit.Test;

public class FeedServiceTest {

	private FeedService service;

	@Before
	public void setUp() {
		service = new FeedService(null);
	}

	@Test
	public void testParseFeed() throws Exception {
		InputStream feedSource = getClass().getResourceAsStream("qc_rss.xml");
		List<FeedItem> result = service.parseFeed(feedSource);
		assertEquals(25, result.size());
		FeedItem item = result.get(4);
		assertEquals("Queen Corner online", item.getFeedTitle());
		assertEquals("Queen w filmie Sucker Punch", item.getTitle());
		assertEquals("http://queencorner.ovh.org/index.php?go=news#25-03-2011", item.getLink());
		assertEquals("W filmie Sucker Punch zosta³ u¿yty mashup dwóch piosenek Queen WWRY i I want it all autorstwa rapera <b>Terror Squad i Armageddon.</b>", item.getDescription());
		assertEquals(new SimpleDateFormat("yyyyMMdd hhmmss").parse("20110325 190434"), item.getPublicationDate());
		assertEquals("queenwfilmiesuckerpunch", item.getId());
	}
}
