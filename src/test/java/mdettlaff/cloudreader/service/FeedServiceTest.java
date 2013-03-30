package mdettlaff.cloudreader.service;

import static org.junit.Assert.assertEquals;

import mdettlaff.cloudreader.service.FeedService;

import org.junit.Before;
import org.junit.Test;

public class FeedServiceTest {

	private FeedService service;

	@Before
	public void setUp() {
		service = new FeedService();
	}

	@Test
	public void testGetFeeds() throws Exception {
		assertEquals(1, service.getFeeds().size());
	}
}
