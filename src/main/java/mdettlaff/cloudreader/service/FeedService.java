package mdettlaff.cloudreader.service;

import java.util.List;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.persistence.FeedItemDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedService {

	private final FeedItemDao feedItemDao;

	@Autowired
	public FeedService(FeedItemDao feedItemDao) {
		this.feedItemDao = feedItemDao;
	}

	public List<FeedItem> getUnreadFeedItems(int count, List<String> excludedItemsGuids) {
		return feedItemDao.find(FeedItem.Status.UNREAD, count, excludedItemsGuids);
	}

	@Transactional
	public void markItemsAsRead(List<String> itemsGuids) {
		for (String itemGuid : itemsGuids) {
			feedItemDao.updateStatus(itemGuid, FeedItem.Status.READ);
		}
	}

	public long countUnreadItems() {
		return feedItemDao.count(FeedItem.Status.UNREAD);
	}
}
