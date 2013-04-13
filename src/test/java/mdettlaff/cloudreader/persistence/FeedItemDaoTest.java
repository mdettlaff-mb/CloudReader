package mdettlaff.cloudreader.persistence;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.persistence.FeedItemDao;
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
		List<FeedItem> results = dao.find(FeedItem.Status.UNREAD, 3, Arrays.asList("item-0002", "item-0004"));
		// verify
		assertEquals(3, results.size());
		FeedItem result1 = results.get(0);
		assertEquals("item-0001", result1.getGuid());
		assertEquals(FeedItem.Status.UNREAD, result1.getStatus());
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
		List<FeedItem> results = dao.find(FeedItem.Status.READ, 1, guidsToExclude);
		// verify
		assertEquals(1, results.size());
		FeedItem result = results.get(0);
		assertEquals("item-0007", result.getGuid());
		assertEquals(FeedItem.Status.READ, result.getStatus());
		assertEquals("My item 7", result.getTitle());
		assertEquals("url2", result.getFeed().getUrl());
		assertEquals("My feed 2", result.getFeed().getTitle());
	}

	@Test
	public void testCount_ByStatus() {
		// exercise
		long result = dao.count(FeedItem.Status.UNREAD);
		// verify
		assertEquals(6, result);
	}

	@Test
	public void testCount() {
		// exercise
		long result = dao.count();
		// verify
		assertEquals(7, result);
	}

	@Test
	public void testUpdateStatus() {
		// exercise
		dao.updateStatus("item-0001", FeedItem.Status.READ);
		// verify
		FeedItem result = em.find(FeedItem.class, "item-0001");
		assertEquals(FeedItem.Status.READ, result.getStatus());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testDelete() {
		// exercise
		dao.delete(5);
		// verify
		List<FeedItem> results = em.createQuery(
				"SELECT i FROM FeedItem i ORDER BY downloadDate")
				.getResultList();
		assertEquals(2, results.size());
		assertEquals("item-0005", results.get(0).getGuid());
		assertEquals("item-0006", results.get(1).getGuid());
	}
}
