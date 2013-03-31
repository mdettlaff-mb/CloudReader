package mdettlaff.cloudreader.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.domain.Subscription;

import org.springframework.stereotype.Component;

@Component
public class FeedItemDao {

	private Map<String, FeedItem> database = new HashMap<>();
	
	public void save(List<FeedItem> items) {
		for (FeedItem item : items) {
			if (!database.containsKey(item.getGuid())) {
				database.put(item.getGuid(), item);
			}
		}
	}
	
	public void markAsRead(String feedItemGuid) {
		FeedItem item = database.get(feedItemGuid);
		item.setRead(true);
	}

	public List<FeedItem> findUnread(int limit, List<String> feedItemsGuidsToExclude) {
		List<FeedItem> result = new ArrayList<>();
		List<FeedItem> feedItems = new ArrayList<>(database.values());
		Collections.sort(feedItems);
		for (FeedItem item : feedItems) {
			boolean shouldExclude = feedItemsGuidsToExclude.contains(item.getGuid());
			if (!item.isRead() && result.size() < limit && !shouldExclude) {
				result.add(item);
			}
		}
		return result;
	}

	public List<Subscription> getSubscriptions() {
		List<Subscription> result = new ArrayList<>();
		Subscription qc = new Subscription();
//		qc.setUrl("http://queencorner.ovh.org/rss.xml");
		qc.setUrl(getClass().getResource("qc_rss.xml").toString());
		result.add(qc);
		Subscription github = new Subscription();
//		github.setUrl("https://github.com/mdettlaff-mb/CloudReader/commits/master.atom");
		github.setUrl(getClass().getResource("github_atom.xml").toString());
		result.add(github);
		return result;
	}
}
