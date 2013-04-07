package mdettlaff.cloudreader.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.test.AbstractPersistenceTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class FeedItemDaoTest extends AbstractPersistenceTest {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private FeedItemDao dao;

	@Test
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

	@Test
	public void testFind_EmptyList() {
		// prepare data
		List<String> guidsToExclude = new ArrayList<>();
		// exercise
		List<FeedItem> results = dao.find(true, 1, guidsToExclude);
		// verify
		assertEquals(1, results.size());
		FeedItem result = results.get(0);
		assertEquals("item-0007", result.getGuid());
		assertEquals(true, result.isRead());
		assertEquals("My item 7", result.getTitle());
		assertEquals("url2", result.getFeed().getUrl());
		assertEquals("My feed 2", result.getFeed().getTitle());
	}

	@Test
	public void testCount() {
		// exercise
		long result = dao.count(false);
		// verify
		assertEquals(6, result);
	}

	@Test
	public void testUpdateRead() {
		// exercise
		dao.updateRead("item-0001", true);
		// verify
		FeedItem result = em.find(FeedItem.class, "item-0001");
		assertEquals(true, result.isRead());
	}
}
