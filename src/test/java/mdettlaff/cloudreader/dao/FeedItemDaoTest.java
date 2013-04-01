package mdettlaff.cloudreader.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.test.AbstractPersistenceTestContext;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class FeedItemDaoTest extends AbstractPersistenceTestContext {

	@Autowired
	private FeedItemDao dao;

	@Test
	@Transactional
	public void testFindAllFeedItems() {
		// exercise
		List<Feed> results = dao.findFeeds();
		// verify
		assertEquals(2, results.size());
		assertEquals("http://queencorner.ovh.org/rss.xml", results.get(0).getUrl());
		assertTrue(results.get(0).getItems().isEmpty());
		assertEquals("https://github.com/mdettlaff-mb/CloudReader/commits/master.atom", results.get(1).getUrl());
		assertTrue(results.get(1).getItems().isEmpty());
	}
}
