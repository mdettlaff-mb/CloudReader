package mdettlaff.cloudreader.domain;

import java.util.List;

public class Feed {

	private String title;
	private String link;
	private List<FeedItem> items;

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getLink() {
		return link;
	}

	public List<FeedItem> getItems() {
		return items;
	}

	public void setItems(List<FeedItem> items) {
		this.items = items;
	}
}
