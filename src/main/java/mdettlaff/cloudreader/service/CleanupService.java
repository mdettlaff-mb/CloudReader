package mdettlaff.cloudreader.service;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.persistence.FeedItemDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CleanupService {

	private static final int THRESHOLD = 7000;

	private final Logger log = LoggerFactory.getLogger(CleanupService.class);

	private final FeedItemDao feedItemDao;

	@Autowired
	public CleanupService(FeedItemDao feedItemDao) {
		this.feedItemDao = feedItemDao;
	}

	@Transactional
	@Scheduled(cron = "0 55 * * * *")
	public void deleteOldFeedItems() {
		log.info("delete old feed items - start");
		long allItemsCount = feedItemDao.count();
		long itemsToDeleteCount = Math.max(0, allItemsCount - THRESHOLD);
		long readItemsCount = feedItemDao.count(FeedItem.Status.READ);
		if (itemsToDeleteCount > readItemsCount) {
			log.warn("{} unread items will be deleted", itemsToDeleteCount - readItemsCount);
		}
		if (itemsToDeleteCount > 0) {
			feedItemDao.delete(itemsToDeleteCount);
		}
		log.info("delete old feed items - end ({} items deleted)", itemsToDeleteCount);
	}
}
