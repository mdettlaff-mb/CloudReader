package mdettlaff.cloudreader.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.domain.Feed;

import org.springframework.stereotype.Component;

@Component
public class FeedItemDao {

	private Map<String, FeedItem> database = new HashMap<>();
	
	public int save(Feed feed) {
		int insertedItemsCount = 0;
		for (FeedItem item : feed.getItems()) {
			if (!database.containsKey(item.getGuid())) {
				database.put(item.getGuid(), item);
				insertedItemsCount++;
			}
		}
		return insertedItemsCount;
	}
	
	public void markItemAsRead(String feedItemGuid) {
		FeedItem item = database.get(feedItemGuid);
		item.setRead(true);
	}

	public List<FeedItem> findUnread(int limit, List<String> feedItemsGuidsToExclude) {
		List<FeedItem> result = new ArrayList<>();
		List<FeedItem> feedItems = new ArrayList<>(database.values());
		Collections.sort(feedItems);
		for (FeedItem item : feedItems) {
			boolean shouldExclude = feedItemsGuidsToExclude.contains(item.getGuid());
			if (!item.isRead() && result.size() < limit && !shouldExclude) {
				result.add(item);
			}
		}
		return result;
	}

	public int countUnread() {
		int sum = 0;
		for (FeedItem item : database.values()) {
			if (!item.isRead()) {
				sum++;
			}
		}
		return sum;
	}

	public List<Feed> getFeeds() {
		List<Feed> result = new ArrayList<>();
//		result.add(new Feed("http://queencorner.ovh.org/rss.xml"));
		result.add(new Feed(getClass().getResource("qc_rss.xml").toString()));
//		result.add(new Feed("https://github.com/mdettlaff-mb/CloudReader/commits/master.atom"));
		result.add(new Feed(getClass().getResource("github_atom.xml").toString()));
		/*
		result.add(new Feed("http://blog.primefaces.org/?feed=rss2"));
		result.add(new Feed("http://www.adam-bien.com/roller/abien/feed/entries/atom"));
		result.add(new Feed("http://blog.geek-soft.pl/feed/"));
		result.add(new Feed("http://rss.cnn.com/rss/cnn_topstories.rss"));
		result.add(new Feed("http://davidbrin.blogspot.com/feeds/posts/default"));
		result.add(new Feed("http://feeds.feedburner.com/CrackedRSS"));
		result.add(new Feed("http://www.damninteresting.com/?feed=rss2"));
		result.add(new Feed("http://del.icio.us/rss/network/wundzun"));
		result.add(new Feed("http://sigma.ug.edu.pl/~mdettla/rss/demotywatory_rss.xml"));
		result.add(new Feed("http://www.dzone.com/links/feed/frontpage/rss.xml"));
		result.add(new Feed("http://www.everythingisterrible.com/feeds/posts/default"));
		result.add(new Feed("http://feeds.feedburner.com/failblog"));
		result.add(new Feed("http://www.gametrailers.com/rssgenerate.php?game1id=3000&amp;orderby=newest&amp;limit=20"));
		result.add(new Feed("http://www.infoq.com/rss/rss.action?token=xNlmgbsJxyBOlm3uvXx7GlQxgAofDw2v"));
		result.add(new Feed("http://code.google.com/feeds/p/primefaces/issueupdates/basic"));
		result.add(new Feed("http://www.java-tv.com/feed/"));
		result.add(new Feed("http://www.joemonster.org/backend.php"));
		result.add(new Feed("http://www.mininova.com/rss/the+daily+show"));
		result.add(new Feed("http://www.paleofuture.com/feeds/posts/default"));
		result.add(new Feed("http://photoshopdisasters.blogspot.com/feeds/posts/default"));
		result.add(new Feed("http://pitupitu.tivi.pl/rss/kanal.xml"));
		result.add(new Feed("http://www.reddit.com/r/java+haskell+scheme+forth+groovy+compsci+codeprojects+sysor+semanticweb+software+xmpp+socialsoftware+agile+erlang+grails+lisp+opensource+python+soa+ruby+cobol+vim+emacs+dotnet+csharp+programming+scala+clojure+javascript+css+php+mysql+functional+J2EE/.rss"));
		result.add(new Feed("http://www.reddit.com/.rss"));
		result.add(new Feed("http://revealingerrors.com/feed/atom"));
		result.add(new Feed("http://www.schneier.com/blog/index.rdf"));
		result.add(new Feed("http://rss.sciam.com/ScientificAmerican-Global"));
		result.add(new Feed("http://rss.slashdot.org/slashdot/classic"));
		result.add(new Feed("http://stuffwhitepeoplelike.com/feed/"));
		result.add(new Feed("http://submachine.pl/feed/atom"));
		result.add(new Feed("http://syndication.thedailywtf.com/TheDailyWtf"));
		result.add(new Feed("http://syndication.thedailywtf.com/WTF/PL"));
		result.add(new Feed("http://googleblog.blogspot.com/feeds/posts/default"));
		result.add(new Feed("http://feeds.theonion.com/theonion/daily"));
		result.add(new Feed("http://feeds.feedburner.com/ThisIsNotPorn-RareCelebrityPhotos"));
		result.add(new Feed("http://feeds.feedburner.com/TotallyLooksLike"));
		result.add(new Feed("http://www.trzeciakawa.pl/?feed=rss2"));
		result.add(new Feed("http://twitter.com/statuses/user_timeline/62581962.rss"));
		result.add(new Feed("http://sigma.ug.edu.pl/~mdettla/tv.cgi"));
		result.add(new Feed("http://www.wp.pl/rss.xml?id=1"));
		result.add(new Feed("http://sydneypadua.com/2dgoggles/feed/"));
		result.add(new Feed("http://cectic.com/rss.xml"));
		result.add(new Feed("http://feedproxy.google.com/DilbertDailyStrip"));
		result.add(new Feed("http://feeds.feedburner.com/Explosm"));
		result.add(new Feed("http://feeds.gocomics.com/uclick/foxtrot"));
		result.add(new Feed("http://feeds.gocomics.com/uclick/garfield"));
		result.add(new Feed("http://www.jesusandmo.net/feed/"));
		result.add(new Feed("http://feeds.penny-arcade.com/pa-mainsite"));
		result.add(new Feed("http://www.phdcomics.com/gradfeed.php"));
		result.add(new Feed("http://www.questionablecontent.net/QCRSS.xml"));
		result.add(new Feed("http://feeds.gocomics.com/uclick/ronaldinhogaucho"));
		result.add(new Feed("http://www.sgvy.com/rss.xml"));
		result.add(new Feed("http://www.userfriendly.org/rss/uf.rss"));
		result.add(new Feed("http://xkcd.com/atom.xml"));
		result.add(new Feed("http://rss.7thguard.net/7thguard.xml"));
		result.add(new Feed("http://bash.org.pl/rss"));
		result.add(new Feed("http://rss.hacking.pl/"));
		result.add(new Feed("http://www.linux.pl/rss.php"));
		result.add(new Feed("http://queencorner.ovh.org/rss.xml"));
		result.add(new Feed("http://queen1991.blog.pl/index.rss"));
		result.add(new Feed("http://feeds2.feedburner.com/Roflcopter?format=xml"));
		result.add(new Feed("http://gdata.youtube.com/feeds/base/users/ksdobrodobro/uploads?alt=rss&amp;v=2&amp;client=ytapi-youtube-profile"));
		result.add(new Feed("http://www.youtube.com/ut_rss?type=username&amp;arg=llewy10"));
		result.add(new Feed("http://rss.7thguard.net/7thguard.xml"));
		result.add(new Feed("http://art.gnome.org/backend.php"));
		result.add(new Feed("http://code.google.com/feeds/p/coolkey/updates/basic"));
		result.add(new Feed("http://debaday.debian.net/feed/atom/"));
		result.add(new Feed("http://www.gnomedesktop.org/backend.php"));
		result.add(new Feed("http://www.gnomefiles.org/files/gnomefiles.rdf"));
		result.add(new Feed("http://www.groklaw.net/backend/GrokLaw.rdf"));
		result.add(new Feed("http://rss.hacking.pl/"));
		result.add(new Feed("http://code.google.com/feeds/p/jreader-ug/updates/basic"));
		result.add(new Feed("http://feeds.feedburner.com/LinuxHatersBlog"));
		result.add(new Feed("http://www.linux.com/index.rss"));
		result.add(new Feed("http://www.linux.pl/rss.php"));
		result.add(new Feed("http://www.mozillazine.org/contents.rdf"));
		result.add(new Feed("http://planet.debian.org/rss20.xml"));
		result.add(new Feed("http://www.userfriendly.org/rss/uf.rss"));
		result.add(new Feed("http://gdata.youtube.com/feeds/base/users/GoogleDevelopers/uploads?alt=rss&amp;v=2&amp;orderby=published&amp;client=ytapi-youtube-profile"));
		result.add(new Feed("http://gdata.youtube.com/feeds/base/users/oessu/uploads?alt=rss&amp;v=2&amp;orderby=published&amp;client=ytapi-youtube-profile"));
		result.add(new Feed("http://gdata.youtube.com/feeds/base/users/RedLetterMedia/uploads?alt=rss&amp;v=2&amp;orderby=published&amp;client=ytapi-youtube-profile"));
		result.add(new Feed("http://gdata.youtube.com/feeds/base/users/ksdobrodobro/uploads?alt=rss&amp;v=2&amp;client=ytapi-youtube-profile"));
		result.add(new Feed("http://www.youtube.com/ut_rss?type=username&amp;arg=CapnOAwesome"));
		result.add(new Feed("http://www.youtube.com/ut_rss?type=username&amp;arg=googletechtalks"));
		result.add(new Feed("http://www.youtube.com/ut_rss?type=username&amp;arg=JamCamp06"));
		result.add(new Feed("http://www.youtube.com/ut_rss?type=username&amp;arg=llewy10"));
		result.add(new Feed("http://www.youtube.com/ut_rss?type=username&amp;arg=rpoland"));
		*/
		return result;
	}
}
