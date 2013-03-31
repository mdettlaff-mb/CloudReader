package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.domain.Feed;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.sun.syndication.io.FeedException;

@Service
public class FeedDownloadService implements InitializingBean {

	private static final Logger log = Logger.getLogger(FeedDownloadService.class.getName());

	private final FeedItemDao dao;
	private final FeedParserService feedParserService;

	@Autowired
	public FeedDownloadService(FeedParserService feedParserService, FeedItemDao dao) {
		this.feedParserService = feedParserService;
		this.dao = dao;
	}

	@Override
	public void afterPropertiesSet() {
		refreshFeeds();
	}

	private void refreshFeeds() {
		List<Feed> feeds = dao.getFeeds();
		for (Feed feed : feeds) {
			try {
				downloadFeedItems(new URL(feed.getUrl()));
			} catch (FeedException | IOException e) {
				log.warning("cannot download feed " + feed.getUrl() + ", cause: " + e);
			}
		}
	}

	private void downloadFeedItems(URL feedUrl) throws FeedException, IOException {
		log.info("downloading " + feedUrl);
		List<FeedItem> feedItems = feedParserService.parseFeed(feedUrl);
		for (FeedItem item : feedItems) {
			item.setDownloadDate(new Date());
			item.setGuid(createGuid(item));
		}
		dao.save(feedItems);
		log.info("feed " + feedUrl + " saved");
	}

	private String createGuid(FeedItem item) {
		return "item-" + Hashing.sha1().newHasher().putString(item.getHashBase()).hash().toString();
	}
}
