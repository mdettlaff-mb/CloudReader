package mdettlaff.cloudreader.web;

import java.util.HashMap;
import java.util.Map;

import mdettlaff.cloudreader.service.FeedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "")
public class HomeController {

	private final FeedService service;

	@Autowired
	public HomeController(FeedService service) {
		this.service = service;
	}

	@RequestMapping
	public ModelAndView home() {
		Map<String, Object> model = new HashMap<>();
		model.put("feedItems", service.getFeedItems());
		return new ModelAndView("index", model);
	}
}
