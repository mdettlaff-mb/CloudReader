package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
public class FeedParserService {

	public List<FeedItem> parseFeed(URL feedSource) throws FeedException, IOException {
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed = input.build(new XmlReader(feedSource));
		Feed feed = new Feed();
		feed.setTitle(syndFeed.getTitle());
		feed.setLink(syndFeed.getLink());
		List<FeedItem> items = new ArrayList<>();
		for (Object entry : syndFeed.getEntries()) {
			FeedItem item = createFeedItem((SyndEntry) entry);
			item.setFeed(feed);
			items.add(item);
		}
		return items;
	}

	private FeedItem createFeedItem(SyndEntry entry) {
		FeedItem item = new FeedItem();
		item.setTitle(entry.getTitle());
		item.setLink(entry.getLink());
		String description = entry.getDescription().getValue();
		item.setDescription(description == null ? null : description.trim());
		item.setPublicationDate(entry.getPublishedDate());
		item.setAuthor(entry.getAuthor());
		return item;
	}
}
