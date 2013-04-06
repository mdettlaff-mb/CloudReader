package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// no-arg constructor to make CGLIB happy
	public FeedService() {
		this(null);
	}

	public List<FeedItem> getFeedItems() throws FeedException, IOException {
		return dao.find(false, INITIAL_SIZE, new ArrayList<String>());
	}

	public List<FeedItem> getFeedItems(List<String> excludedItemsGuids) throws FeedException, IOException {
		return dao.find(false, BUFFER_SIZE, excludedItemsGuids);
	}

	@Transactional
	public void markItemAsRead(String feedItemGuid) {
		dao.updateRead(feedItemGuid, true);
	}

	public long countUnreadItems() {
		return dao.count(false);
	}
}
