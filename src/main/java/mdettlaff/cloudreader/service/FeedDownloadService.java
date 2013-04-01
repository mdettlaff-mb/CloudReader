package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.sun.syndication.io.FeedException;

@Service
public class FeedDownloadService {

	private static final Logger log = Logger.getLogger(FeedDownloadService.class.getName());

	private final FeedItemDao dao;
	private final FeedParserService feedParserService;

	@Autowired
	public FeedDownloadService(FeedParserService feedParserService, FeedItemDao dao) {
		this.feedParserService = feedParserService;
		this.dao = dao;
	}

	public int updateFeeds() {
		List<Feed> feeds = dao.getFeeds();
		int totalInsertedItemsCount = 0;
		for (Feed feed : feeds) {
			try {
				int insertedItemsCount = downloadFeedItems(new URL(feed.getUrl()));
				totalInsertedItemsCount += insertedItemsCount;
			} catch (FeedException | IOException e) {
				log.warning("cannot download feed " + feed.getUrl() + ", cause: " + e);
			}
		}
		return totalInsertedItemsCount;
	}

	private int downloadFeedItems(URL feedUrl) throws FeedException, IOException {
		log.info("downloading " + feedUrl);
		Feed feed = feedParserService.parseFeed(feedUrl);
		for (FeedItem item : feed.getItems()) {
			item.setDownloadDate(new Date());
			item.setGuid(createGuid(item));
		}
		int insertedItemsCount = dao.save(feed);
		log.info("feed " + feedUrl + " saved");
		return insertedItemsCount;
	}

	private String createGuid(FeedItem item) {
		return "item-" + Hashing.sha1().newHasher().putString(item.getHashBase()).hash().toString();
	}
}
