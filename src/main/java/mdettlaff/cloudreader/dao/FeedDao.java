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
		return em.createQuery("FROM Feed f ORDER BY f.url").getResultList();
	}

	public int save(Feed feed) {
		List<FeedItem> newItems = filterNewItems(feed);
		feed.setItems(newItems);
		em.find(Feed.class, feed.getUrl());
		em.merge(feed);
		em.flush();
		return newItems.size();
	}

	private List<FeedItem> filterNewItems(Feed feed) {
		List<FeedItem> result = new ArrayList<>();
		for (FeedItem item : feed.getItems()) {
			if (em.find(FeedItem.class, item.getGuid()) == null && !result.contains(item)) {
				result.add(item);
			}
		}
		return result;
	}
}
