package mdettlaff.cloudreader.service;

import java.util.Arrays;
import java.util.List;

import mdettlaff.comics.domain.Feed;

import org.springframework.stereotype.Service;

@Service
public class FeedService {

	public List<Feed> getFeeds() {
		// TODO implement
		return Arrays.asList(new Feed("test", "http://test.com"));
	}
}
