package mdettlaff.cloudreader.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mdettlaff.cloudreader.domain.Feed;
import mdettlaff.cloudreader.domain.FeedItem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.sun.syndication.feed.synd.SyndContent;
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
		Feed feed = new Feed(feedSource.toString());
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
		item.setTitle(StringUtils.trim(entry.getTitle()));
		item.setLink(entry.getLink());
		item.setDescription(StringUtils.trim(getDescription(entry)));
		item.setDate(getDate(entry));
		item.setAuthor(entry.getAuthor());
		item.setUri(entry.getUri());
		return item;
	}

	private Date getDate(SyndEntry entry) {
		if (entry.getPublishedDate() != null) {
			return entry.getPublishedDate();
		} else {
			return entry.getUpdatedDate();
		}
	}

	@SuppressWarnings("unchecked")
	private String getDescription(SyndEntry entry) {
		SyndContent content = entry.getDescription();
		if (content == null) {
			List<SyndContent> contents = entry.getContents();
			if (!contents.isEmpty()) {
				content = contents.get(0);
			}
		}
		return content.getValue();
	}
}
