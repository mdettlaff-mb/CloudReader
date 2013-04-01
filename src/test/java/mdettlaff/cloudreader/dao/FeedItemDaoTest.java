package mdettlaff.cloudreader.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.test.AbstractPersistenceTestContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class FeedItemDaoTest extends AbstractPersistenceTestContext {

	@Autowired
	private FeedItemDao dao;

	@Test
	@Transactional
	public void testFindFeeds() {
		// exercise
		List<Feed> results = dao.findFeeds();
		// verify
		assertEquals(4, results.size());
		assertEquals("http://queencorner.ovh.org/rss.xml", results.get(0).getUrl());
		assertTrue(results.get(0).getItems().isEmpty());
		assertEquals("https://github.com/mdettlaff-mb/CloudReader/commits/master.atom", results.get(1).getUrl());
		assertTrue(results.get(1).getItems().isEmpty());
	}

	@Test
	@Transactional
	public void testFind() {
		// exercise
		List<FeedItem> results = dao.find(false, 3, Arrays.asList("item-0002", "item-0004"));
		// verify
		assertEquals(3, results.size());
		FeedItem result1 = results.get(0);
		assertEquals("item-0001", result1.getGuid());
		assertEquals(false, result1.isRead());
		assertEquals("My item 1", result1.getTitle());
		assertEquals("url1", result1.getFeed().getUrl());
		assertEquals("My feed 1", result1.getFeed().getTitle());
		assertEquals("item-0003", results.get(1).getGuid());
		assertEquals("item-0005", results.get(2).getGuid());
	}
}
