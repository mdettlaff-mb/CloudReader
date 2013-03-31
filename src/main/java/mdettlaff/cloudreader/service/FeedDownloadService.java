package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.domain.Subscription;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.sun.syndication.io.FeedException;

@Service
public class FeedDownloadService implements InitializingBean {

	private final FeedItemDao dao;
	private final FeedParserService feedParserService;

	@Autowired
	public FeedDownloadService(FeedParserService feedParserService, FeedItemDao dao) {
		this.feedParserService = feedParserService;
		this.dao = dao;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		refreshSubscriptions();
	}

	private void refreshSubscriptions() throws FeedException, IOException {
		List<Subscription> subscriptions = dao.getSubscriptions();
		for (Subscription subscription : subscriptions) {
			downloadFeedItems(subscription);
		}
	}

	private void downloadFeedItems(Subscription subscription) throws FeedException, IOException {
		List<FeedItem> feedItems = feedParserService.parseFeed(new URL(subscription.getUrl()));
		for (FeedItem item : feedItems) {
			item.setDownloadDate(new Date());
			item.getFeed().setSubscription(subscription);
			item.setGuid(createGuid(item));
		}
		dao.save(feedItems);
	}

	private String createGuid(FeedItem item) {
		return "item-" + Hashing.sha1().newHasher().putString(item.getHashBase()).hash().toString();
	}
}
