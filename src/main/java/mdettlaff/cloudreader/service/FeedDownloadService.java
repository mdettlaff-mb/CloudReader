package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import mdettlaff.cloudreader.dao.FeedDao;
import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Hashing;
import com.sun.syndication.io.FeedException;

@Service
public class FeedDownloadService {

	private static final Logger log = Logger.getLogger(FeedDownloadService.class.getName());

	private final FeedDao feedDao;
	private final FeedParserService feedParserService;

	@Autowired
	public FeedDownloadService(FeedParserService feedParserService, FeedDao feedDao) {
		this.feedParserService = feedParserService;
		this.feedDao = feedDao;
	}

	@Transactional
	@Scheduled(cron = "0 0 */3 * * *")
	public void updateFeedsPeriodically() {
		updateFeeds();
	}

	@Transactional
	public int updateFeeds() {
		log.info("update feeds - start");
		List<Feed> feeds = feedDao.find();
		int totalInsertedItemsCount = 0;
		for (Feed feed : feeds) {
			try {
				int insertedItemsCount = downloadFeedItems(new URL(feed.getUrl()));
				totalInsertedItemsCount += insertedItemsCount;
			} catch (FeedException | IOException e) {
				log.warning("cannot download feed " + feed.getUrl() + ", cause: " + e);
			} catch (Exception e) {
				log.severe("error while downloading feed " + feed.getUrl() + ", cause: " + e);
				throw e;
			}
		}
		log.info("update feeds - end (" + totalInsertedItemsCount + " new items)");
		return totalInsertedItemsCount;
	}

	private int downloadFeedItems(URL feedUrl) throws FeedException, IOException {
		Feed feed = feedParserService.parseFeed(feedUrl);
		for (FeedItem item : feed.getItems()) {
			item.setDownloadDate(new Date());
			item.setGuid(createGuid(item));
		}
		int insertedItemsCount = feedDao.save(feed);
		log.info("downloaded " + insertedItemsCount + " new items from " + feedUrl);
		return insertedItemsCount;
	}

	private String createGuid(FeedItem item) {
		return "item-" + Hashing.sha1().newHasher().putString(item.getHashBase()).hash().toString();
	}
}
