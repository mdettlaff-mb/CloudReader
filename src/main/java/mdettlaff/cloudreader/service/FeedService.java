package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;

import org.springframework.stereotype.Service;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

@Service
public class FeedService {

	public List<Feed> getFeeds() {
		// TODO implement
		Feed feed = new Feed();
		feed.setTitle("test");
		feed.setLink("http://test.com");
		return Arrays.asList(feed);
	}

	public Feed parseFeed(InputStream feedSource) throws FeedException,
			IOException {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed = input.build(new XmlReader(feedSource));
		return convertToFeed(syndFeed);
	}

	private Feed convertToFeed(SyndFeed syndFeed) {
		Feed result = new Feed();
		result.setTitle(syndFeed.getTitle());
		result.setLink(syndFeed.getLink());
		List<FeedItem> items = new ArrayList<>();
		for (Object entry : syndFeed.getEntries()) {
			FeedItem item = convertToFeedItem((SyndEntry) entry);
			items.add(item);
		}
		result.setItems(items);
		return result;
	}

	private FeedItem convertToFeedItem(SyndEntry entry) {
		FeedItem item = new FeedItem();
		item.setTitle(entry.getTitle());
		String description = entry.getDescription().getValue();
		item.setDescription(description == null ? null : description.trim());
		return item;
	}
}
