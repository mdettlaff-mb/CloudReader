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
		return convertItems(feedItemDao.find(FeedItem.Status.UNREAD, count, excludedItemsGuids));
	}

	private List<FeedItem> convertItems(List<FeedItem> items) {
		for (FeedItem item : items) {
			String link = item.getLink();
			if (link != null && link.startsWith("http://www.reddit.com/r/")) {
				item.setLink(link + ".compact");
			}
		}
		return items;
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
