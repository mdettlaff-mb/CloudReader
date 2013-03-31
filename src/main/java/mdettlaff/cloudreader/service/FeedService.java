package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.syndication.io.FeedException;

@Service
public class FeedService {

	private static final int INITIAL_SIZE = 14;
	private static final int BUFFER_SIZE = 4;

	private final FeedItemDao dao;

	@Autowired
	public FeedService(FeedItemDao dao) {
		this.dao = dao;
	}

	public List<FeedItem> getFeedItems() throws FeedException, IOException {
		return dao.findUnread(INITIAL_SIZE, new ArrayList<String>());
	}

	public List<FeedItem> getFeedItems(List<String> unreadFeedItemsGuids) throws FeedException, IOException {
		return dao.findUnread(BUFFER_SIZE, unreadFeedItemsGuids);
	}

	public void markItemAsRead(String feedItemGuid) {
		dao.markItemAsRead(feedItemGuid);
	}
}
