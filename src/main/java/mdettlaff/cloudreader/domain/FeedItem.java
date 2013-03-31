package mdettlaff.cloudreader.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FeedItem implements Comparable<FeedItem> {

	private String guid;
	private Feed feed;
	private String title;
	private String link;
	private String description;
	private Date publicationDate;
	private Date downloadDate;
	private String author;
	private boolean read;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public int compareTo(FeedItem other) {
		if (publicationDate != null && other.publicationDate != null) {
			return publicationDate.compareTo(other.publicationDate);
		} else {
			return downloadDate.compareTo(other.downloadDate);
		}
	}

	@JsonIgnore
	public String getHashBase() {
		StringBuilder builder = new StringBuilder();
		builder.append(feed.getSubscription().getUrl());
		builder.append(link);
		builder.append(title);
		builder.append(publicationDate);
		return builder.toString();
	}
}
