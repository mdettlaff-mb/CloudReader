package mdettlaff.cloudreader.web.request;

import java.util.List;

public class UpdateRequest {

	private List<String> unreadFeedItemsGuids;
	private List<String> readPendingFeedItemsGuids;

	public List<String> getUnreadFeedItemsGuids() {
		return unreadFeedItemsGuids;
	}
	
	public void setUnreadFeedItemsGuids(List<String> unreadFeedItemsGuids) {
		this.unreadFeedItemsGuids = unreadFeedItemsGuids;
	}
	
	public List<String> getReadPendingFeedItemsGuids() {
		return readPendingFeedItemsGuids;
	}
	
	public void setReadPendingFeedItemsGuids(List<String> readPendingFeedItemsGuids) {
		this.readPendingFeedItemsGuids = readPendingFeedItemsGuids;
	}
}
