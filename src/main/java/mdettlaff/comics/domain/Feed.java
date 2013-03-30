package mdettlaff.comics.domain;

public class Feed {

	private final String name;
	private final String url;

	public Feed(String name, String url) {
		this.name = name;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
}
