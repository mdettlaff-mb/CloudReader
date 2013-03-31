package mdettlaff.cloudreader.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	private final FeedService service;

	@Autowired
	public HomeController(FeedService service) {
		this.service = service;
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView home() throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("feedItems", service.getFeedItems());
		return new ModelAndView("index", model);
	}

	@RequestMapping(value = "/items/{id}/read", method = RequestMethod.POST)
	public @ResponseBody String markItemAsRead(@PathVariable("id") String id) {
		service.markItemAsRead(id);
		return "";
	}

	@RequestMapping(value = "/items", method = RequestMethod.POST)
	public @ResponseBody List<FeedItem> fetchMoreItems(@RequestBody List<String> unreadFeedItemsGuids) throws Exception {
		return service.getFeedItems(unreadFeedItemsGuids);
	}
}
