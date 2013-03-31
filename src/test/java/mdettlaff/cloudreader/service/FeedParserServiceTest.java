package mdettlaff.cloudreader.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import mdettlaff.cloudreader.domain.Feed;
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
	public void testParseFeed_RSS() throws Exception {
		URL feedSource = getClass().getResource("qc_rss.xml");
		Feed feed = service.parseFeed(feedSource);
		List<FeedItem> items = feed.getItems();
		assertEquals(25, items.size());
		FeedItem item = items.get(4);
		assertSame(feed, item.getFeed());
		assertTrue(feed.getUrl().matches("file:.*qc_rss.xml"));
		assertEquals("Queen Corner online", feed.getTitle());
		assertEquals("http://queencorner.ovh.org", feed.getLink());
		assertEquals("Queen w filmie Sucker Punch", item.getTitle());
		assertEquals("http://queencorner.ovh.org/index.php?go=news#25-03-2011", item.getLink());
		assertEquals("W filmie Sucker Punch został użyty mashup dwóch piosenek Queen WWRY i I want it all autorstwa rapera <b>Terror Squad i Armageddon.</b>", item.getDescription());
		assertEquals(new SimpleDateFormat("yyyyMMdd hhmmss").parse("20110325 190434"), item.getDate());
		assertEquals("fredmiot@poczta.onet.pl (Daga)", item.getAuthor());
		assertEquals("http://queencorner.ovh.org/index.php?go=news#25-03-2011", item.getUri());
		assertEquals(null, item.getGuid());
	}

	@Test
	public void testParseFeed_Atom() throws Exception {
		URL feedSource = getClass().getResource("github_atom.xml");
		Feed feed = service.parseFeed(feedSource);
		List<FeedItem> items = feed.getItems();
		assertEquals(7, items.size());
		FeedItem item = items.get(1);
		assertSame(feed, item.getFeed());
		assertTrue(feed.getUrl().matches("file:.*github_atom.xml"));
		assertEquals("Recent Commits to CloudReader:master", feed.getTitle());
		assertEquals("https://github.com/mdettlaff-mb/CloudReader/commits/master", feed.getLink());
		assertEquals("return JSON objects", item.getTitle());
		assertEquals("https://github.com/mdettlaff-mb/CloudReader/commit/5d6ba2fe380ced65eaa0ed32c409f24fbf00ce16", item.getLink());
		assertEquals("<pre>m pom.xml\n" + 
				"m src/main/java/mdettlaff/cloudreader/controller/HomeController.java\n" + 
				"m src/main/java/mdettlaff/cloudreader/dao/FeedItemDao.java\n" + 
				"m src/main/java/mdettlaff/cloudreader/service/FeedService.java\n" + 
				"</pre>\n" + 
				"      <pre style='white-space:pre-wrap;width:81ex'>return JSON objects</pre>", item.getDescription());
		assertEquals(new SimpleDateFormat("yyyyMMdd hhmmss").parse("20130331 011204"), item.getDate());
		assertEquals("", item.getAuthor());
		assertEquals("tag:github.com,2008:Grit::Commit/5d6ba2fe380ced65eaa0ed32c409f24fbf00ce16", item.getUri());
		assertEquals(null, item.getGuid());
	}
}
