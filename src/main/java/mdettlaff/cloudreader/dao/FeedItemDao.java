package mdettlaff.cloudreader.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mdettlaff.cloudreader.domain.FeedItem;
import mdettlaff.cloudreader.domain.Subscription;

import org.springframework.stereotype.Component;

@Component
public class FeedItemDao {

	private Map<String, FeedItem> database = new HashMap<>();
	
	public void save(List<FeedItem> items) {
		for (FeedItem item : items) {
			if (!database.containsKey(item.getGuid())) {
				database.put(item.getGuid(), item);
			}
		}
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

	public List<Subscription> getSubscriptions() {
		List<Subscription> result = new ArrayList<>();
//		result.add(createSubscription("http://queencorner.ovh.org/rss.xml"));
		result.add(createSubscription(getClass().getResource("qc_rss.xml").toString()));
//		result.add(createSubscription("https://github.com/mdettlaff-mb/CloudReader/commits/master.atom"));
		result.add(createSubscription(getClass().getResource("github_atom.xml").toString()));
		/*
		result.add(createSubscription("http://blog.primefaces.org/?feed=rss2"));
		result.add(createSubscription("http://www.adam-bien.com/roller/abien/feed/entries/atom"));
		result.add(createSubscription("http://blog.geek-soft.pl/feed/"));
		result.add(createSubscription("http://rss.cnn.com/rss/cnn_topstories.rss"));
		result.add(createSubscription("http://davidbrin.blogspot.com/feeds/posts/default"));
		result.add(createSubscription("http://feeds.feedburner.com/CrackedRSS"));
		result.add(createSubscription("http://www.damninteresting.com/?feed=rss2"));
		result.add(createSubscription("http://del.icio.us/rss/network/wundzun"));
		result.add(createSubscription("http://sigma.ug.edu.pl/~mdettla/rss/demotywatory_rss.xml"));
		result.add(createSubscription("http://www.dzone.com/links/feed/frontpage/rss.xml"));
		result.add(createSubscription("http://www.everythingisterrible.com/feeds/posts/default"));
		result.add(createSubscription("http://feeds.feedburner.com/failblog"));
		result.add(createSubscription("http://www.gametrailers.com/rssgenerate.php?game1id=3000&amp;orderby=newest&amp;limit=20"));
		result.add(createSubscription("http://www.infoq.com/rss/rss.action?token=xNlmgbsJxyBOlm3uvXx7GlQxgAofDw2v"));
		result.add(createSubscription("http://code.google.com/feeds/p/primefaces/issueupdates/basic"));
		result.add(createSubscription("http://www.java-tv.com/feed/"));
		result.add(createSubscription("http://www.joemonster.org/backend.php"));
		result.add(createSubscription("http://www.mininova.com/rss/the+daily+show"));
		result.add(createSubscription("http://www.paleofuture.com/feeds/posts/default"));
		result.add(createSubscription("http://photoshopdisasters.blogspot.com/feeds/posts/default"));
		result.add(createSubscription("http://pitupitu.tivi.pl/rss/kanal.xml"));
		result.add(createSubscription("http://www.reddit.com/r/java+haskell+scheme+forth+groovy+compsci+codeprojects+sysor+semanticweb+software+xmpp+socialsoftware+agile+erlang+grails+lisp+opensource+python+soa+ruby+cobol+vim+emacs+dotnet+csharp+programming+scala+clojure+javascript+css+php+mysql+functional+J2EE/.rss"));
		result.add(createSubscription("http://www.reddit.com/.rss"));
		result.add(createSubscription("http://revealingerrors.com/feed/atom"));
		result.add(createSubscription("http://www.schneier.com/blog/index.rdf"));
		result.add(createSubscription("http://rss.sciam.com/ScientificAmerican-Global"));
		result.add(createSubscription("http://rss.slashdot.org/slashdot/classic"));
		result.add(createSubscription("http://stuffwhitepeoplelike.com/feed/"));
		result.add(createSubscription("http://submachine.pl/feed/atom"));
		result.add(createSubscription("http://syndication.thedailywtf.com/TheDailyWtf"));
		result.add(createSubscription("http://syndication.thedailywtf.com/WTF/PL"));
		result.add(createSubscription("http://googleblog.blogspot.com/feeds/posts/default"));
		result.add(createSubscription("http://feeds.theonion.com/theonion/daily"));
		result.add(createSubscription("http://feeds.feedburner.com/ThisIsNotPorn-RareCelebrityPhotos"));
		result.add(createSubscription("http://feeds.feedburner.com/TotallyLooksLike"));
		result.add(createSubscription("http://www.trzeciakawa.pl/?feed=rss2"));
		result.add(createSubscription("http://twitter.com/statuses/user_timeline/62581962.rss"));
		result.add(createSubscription("http://sigma.ug.edu.pl/~mdettla/tv.cgi"));
		result.add(createSubscription("http://www.wp.pl/rss.xml?id=1"));
		result.add(createSubscription("http://sydneypadua.com/2dgoggles/feed/"));
		result.add(createSubscription("http://cectic.com/rss.xml"));
		result.add(createSubscription("http://feedproxy.google.com/DilbertDailyStrip"));
		result.add(createSubscription("http://feeds.feedburner.com/Explosm"));
		result.add(createSubscription("http://feeds.gocomics.com/uclick/foxtrot"));
		result.add(createSubscription("http://feeds.gocomics.com/uclick/garfield"));
		result.add(createSubscription("http://www.jesusandmo.net/feed/"));
		result.add(createSubscription("http://feeds.penny-arcade.com/pa-mainsite"));
		result.add(createSubscription("http://www.phdcomics.com/gradfeed.php"));
		result.add(createSubscription("http://www.questionablecontent.net/QCRSS.xml"));
		result.add(createSubscription("http://feeds.gocomics.com/uclick/ronaldinhogaucho"));
		result.add(createSubscription("http://www.sgvy.com/rss.xml"));
		result.add(createSubscription("http://www.userfriendly.org/rss/uf.rss"));
		result.add(createSubscription("http://xkcd.com/atom.xml"));
		result.add(createSubscription("http://rss.7thguard.net/7thguard.xml"));
		result.add(createSubscription("http://bash.org.pl/rss"));
		result.add(createSubscription("http://rss.hacking.pl/"));
		result.add(createSubscription("http://www.linux.pl/rss.php"));
		result.add(createSubscription("http://queencorner.ovh.org/rss.xml"));
		result.add(createSubscription("http://queen1991.blog.pl/index.rss"));
		result.add(createSubscription("http://feeds2.feedburner.com/Roflcopter?format=xml"));
		result.add(createSubscription("http://gdata.youtube.com/feeds/base/users/ksdobrodobro/uploads?alt=rss&amp;v=2&amp;client=ytapi-youtube-profile"));
		result.add(createSubscription("http://www.youtube.com/ut_rss?type=username&amp;arg=llewy10"));
		result.add(createSubscription("http://rss.7thguard.net/7thguard.xml"));
		result.add(createSubscription("http://art.gnome.org/backend.php"));
		result.add(createSubscription("http://code.google.com/feeds/p/coolkey/updates/basic"));
		result.add(createSubscription("http://debaday.debian.net/feed/atom/"));
		result.add(createSubscription("http://www.gnomedesktop.org/backend.php"));
		result.add(createSubscription("http://www.gnomefiles.org/files/gnomefiles.rdf"));
		result.add(createSubscription("http://www.groklaw.net/backend/GrokLaw.rdf"));
		result.add(createSubscription("http://rss.hacking.pl/"));
		result.add(createSubscription("http://code.google.com/feeds/p/jreader-ug/updates/basic"));
		result.add(createSubscription("http://feeds.feedburner.com/LinuxHatersBlog"));
		result.add(createSubscription("http://www.linux.com/index.rss"));
		result.add(createSubscription("http://www.linux.pl/rss.php"));
		result.add(createSubscription("http://www.mozillazine.org/contents.rdf"));
		result.add(createSubscription("http://planet.debian.org/rss20.xml"));
		result.add(createSubscription("http://www.userfriendly.org/rss/uf.rss"));
		result.add(createSubscription("http://gdata.youtube.com/feeds/base/users/GoogleDevelopers/uploads?alt=rss&amp;v=2&amp;orderby=published&amp;client=ytapi-youtube-profile"));
		result.add(createSubscription("http://gdata.youtube.com/feeds/base/users/oessu/uploads?alt=rss&amp;v=2&amp;orderby=published&amp;client=ytapi-youtube-profile"));
		result.add(createSubscription("http://gdata.youtube.com/feeds/base/users/RedLetterMedia/uploads?alt=rss&amp;v=2&amp;orderby=published&amp;client=ytapi-youtube-profile"));
		result.add(createSubscription("http://gdata.youtube.com/feeds/base/users/ksdobrodobro/uploads?alt=rss&amp;v=2&amp;client=ytapi-youtube-profile"));
		result.add(createSubscription("http://www.youtube.com/ut_rss?type=username&amp;arg=CapnOAwesome"));
		result.add(createSubscription("http://www.youtube.com/ut_rss?type=username&amp;arg=googletechtalks"));
		result.add(createSubscription("http://www.youtube.com/ut_rss?type=username&amp;arg=JamCamp06"));
		result.add(createSubscription("http://www.youtube.com/ut_rss?type=username&amp;arg=llewy10"));
		result.add(createSubscription("http://www.youtube.com/ut_rss?type=username&amp;arg=rpoland"));
		*/
		return result;
	}

	private Subscription createSubscription(String url) {
		Subscription subscription = new Subscription();
		subscription.setUrl(url);
		return subscription;
	}
}
