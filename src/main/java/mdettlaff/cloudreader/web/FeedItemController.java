package mdettlaff.cloudreader.web;

import java.util.List;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.service.FeedDownloadService;
import mdettlaff.cloudreader.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/items")
public class FeedItemController {

	private final FeedService service;
	private final FeedDownloadService downloadService;

	@Autowired
	public FeedItemController(FeedService service, FeedDownloadService downloadService) {
		this.service = service;
		this.downloadService = downloadService;
	}

	@RequestMapping(value = "{id}/read", method = RequestMethod.POST)
	public @ResponseBody String markItemAsRead(@PathVariable("id") String id) {
		service.markItemAsRead(id);
		return "";
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody List<FeedItem> fetchMoreItems(@RequestBody List<String> excludedItemsGuids) {
		return service.getFeedItems(excludedItemsGuids);
	}

	@RequestMapping(value = "unread/count", method = RequestMethod.GET)
	public @ResponseBody long countUnreadItems() {
		return service.countUnreadItems();
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public @ResponseBody int updateFeeds() {
		return downloadService.updateFeeds();
	}
}
