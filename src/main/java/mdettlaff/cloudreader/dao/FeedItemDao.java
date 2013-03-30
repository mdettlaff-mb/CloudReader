package mdettlaff.cloudreader.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mdettlaff.cloudreader.domain.FeedItem;

@Component
public class FeedItemDao {

	private List<FeedItem> feedItems = new ArrayList<>();
	
	public List<FeedItem> findUnread(int limit) {
		return findUnread(new ArrayList<String>(), limit);
	}
	
	public void save(List<FeedItem> feedItems) {
		this.feedItems = feedItems;
	}
	
	public void markAsRead(String feedItemId) {
		for (FeedItem item : feedItems) {
			if (item.getId().equals(feedItemId)) {
				item.setRead(true);
			}
		}
	}

	public List<FeedItem> findUnread(List<String> unreadFeedItemsIds, int limit) {
		List<FeedItem> result = new ArrayList<>();
		for (FeedItem item : feedItems) {
			if (!item.isRead() && result.size() < limit && !unreadFeedItemsIds.contains(item.getId())) {
				result.add(item);
			}
		}
		return result;
	}
}
