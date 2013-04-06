package mdettlaff.cloudreader.domain;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Feed {

	@Id
	private String url;
	private String title;
	private String link;
	@OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
	private List<FeedItem> items;

	public Feed() {
	}

	public Feed(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@JsonIgnore
	public List<FeedItem> getItems() {
		return items;
	}

	public void setItems(List<FeedItem> items) {
		this.items = items;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Feed other = (Feed) obj;
		return Objects.equals(url, other.url);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(url);
	}
}
