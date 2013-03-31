package mdettlaff.cloudreader.service;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import mdettlaff.cloudreader.domain.FeedItem;

import org.junit.Before;
import org.junit.Test;

public class FeedParserServiceTest {

	private FeedParserService service;

	@Before
	public void setUp() {
		service = new FeedParserService();
	}

	@Test
	public void testParseFeed() throws Exception {
		URL feedSource = getClass().getResource("qc_rss.xml");
		List<FeedItem> items = service.parseFeed(feedSource);
		assertEquals(25, items.size());
		FeedItem item = items.get(4);
		assertEquals("Queen Corner online", item.getFeed().getTitle());
		assertEquals("http://queencorner.ovh.org", item.getFeed().getLink());
		assertEquals("Queen w filmie Sucker Punch", item.getTitle());
		assertEquals("http://queencorner.ovh.org/index.php?go=news#25-03-2011", item.getLink());
		assertEquals("W filmie Sucker Punch zosta³ u¿yty mashup dwóch piosenek Queen WWRY i I want it all autorstwa rapera <b>Terror Squad i Armageddon.</b>", item.getDescription());
		assertEquals(new SimpleDateFormat("yyyyMMdd hhmmss").parse("20110325 190434"), item.getPublicationDate());
		assertEquals("fredmiot@poczta.onet.pl (Daga)", item.getAuthor());
		assertEquals(null, item.getGuid());
	}
}
