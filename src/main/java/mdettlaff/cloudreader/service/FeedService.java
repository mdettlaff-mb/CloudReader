package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mdettlaff.cloudreader.dao.FeedItemDao;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

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

	public List<FeedItem> getFeedItems(List<String> unreadFeedItemsIds) throws MalformedURLException, IOException, FeedException {
		if (dao.findUnread(BUFFER_SIZE, unreadFeedItemsIds).isEmpty()) {
			List<FeedItem> downloaded = downloadFeedItems();
			dao.save(downloaded);
		}
		return dao.findUnread(BUFFER_SIZE, unreadFeedItemsIds);
	}

	public void markAsRead(String feedItemId) {
		dao.markAsRead(feedItemId);
	}

	private List<FeedItem> downloadFeedItems() throws MalformedURLException,
			IOException, FeedException {
		URL url = new URL("http://queencorner.ovh.org/rss.xml");
		InputStream stream = url.openStream();
		try {
			return parseFeed(stream);
		} finally {
			stream.close();
		}
	}

	List<FeedItem> parseFeed(InputStream feedSource)
			throws FeedException, IOException {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed = input.build(new XmlReader(feedSource));
		List<FeedItem> result = new ArrayList<>();
		for (Object entry : syndFeed.getEntries()) {
			FeedItem item = createFeedItem((SyndEntry) entry, syndFeed.getTitle());
			result.add(item);
		}
		return result;
	}

	private FeedItem createFeedItem(SyndEntry entry, String feedTitle) {
		FeedItem item = new FeedItem();
		item.setId(createGuid(entry));
		item.setFeedTitle(feedTitle);
		item.setTitle(entry.getTitle());
		item.setLink(entry.getLink());
		String description = entry.getDescription().getValue();
		item.setDescription(description == null ? null : description.trim());
		item.setPublicationDate(entry.getPublishedDate());
		return item;
	}

	private String createGuid(SyndEntry entry) {
		return entry.getTitle().replaceAll("[^A-Za-z0-9]", "").toLowerCase();
	}
}
