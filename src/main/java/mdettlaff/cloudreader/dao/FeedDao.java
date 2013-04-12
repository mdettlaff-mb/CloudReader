package mdettlaff.cloudreader.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.stereotype.Repository;

@Repository
public class FeedDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Feed> find() {
		return em.createQuery("FROM Feed ORDER BY url").getResultList();
	}

	public int save(Feed feed) {
		List<FeedItem> filteredItems = filterItems(feed.getItems());
		feed.setItems(filteredItems);
		em.find(Feed.class, feed.getUrl());
		em.merge(feed);
		em.flush();
		return filteredItems.size();
	}

	private List<FeedItem> filterItems(List<FeedItem> items) {
		List<FeedItem> result = new ArrayList<>();
		for (FeedItem item : items) {
			if (em.find(FeedItem.class, item.getGuid()) == null && !result.contains(item)) {
				result.add(item);
			}
		}
		return result;
	}
}
