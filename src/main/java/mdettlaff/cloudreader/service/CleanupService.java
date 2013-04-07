package mdettlaff.cloudreader.service;

import java.util.logging.Logger;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CleanupService {

	private static final Logger log = Logger.getLogger(CleanupService.class.getName());

	private static final int THRESHOLD = 8500;

	private final FeedItemDao feedItemDao;

	@Autowired
	public CleanupService(FeedItemDao feedItemDao) {
		this.feedItemDao = feedItemDao;
	}

	@Transactional
	@Scheduled(cron = "0 30 0 * * *")
	public void deleteOldFeedItems() {
		log.info("delete old feed items - start");
		long allItemsCount = feedItemDao.count();
		long itemsToDeleteCount = Math.max(0, allItemsCount - THRESHOLD);
		long readItemsCount = feedItemDao.count(FeedItem.Status.READ);
		if (itemsToDeleteCount > readItemsCount) {
			log.warning((itemsToDeleteCount - readItemsCount) + " unread items will be deleted");
		}
		if (itemsToDeleteCount > 0) {
			feedItemDao.delete(itemsToDeleteCount);
		}
		log.info("delete old feed items - end (" + itemsToDeleteCount + " items deleted)");
	}
}
