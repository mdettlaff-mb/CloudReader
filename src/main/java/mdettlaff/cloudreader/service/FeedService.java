package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.syndication.io.FeedException;

@Service
public class FeedService {

	private static final int BUFFER_SIZE = 5;
	
	private final FeedItemDao dao;

	@Autowired
	public FeedService(FeedItemDao dao) {
		this.dao = dao;
	}
	
	public List<FeedItem> getFeedItems() throws URISyntaxException, FeedException, IOException {
		return getFeedItems(new ArrayList<String>());
	}

	public List<FeedItem> getFeedItems(List<String> unreadFeedItemsGuids) throws MalformedURLException, IOException, FeedException {
		return dao.findUnread(BUFFER_SIZE, unreadFeedItemsGuids);
	}

	public void markAsRead(String feedItemGuid) {
		dao.markAsRead(feedItemGuid);
	}
}
