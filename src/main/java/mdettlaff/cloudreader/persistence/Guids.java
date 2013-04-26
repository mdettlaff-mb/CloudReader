package mdettlaff.cloudreader.persistence;

import java.sql.Clob;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import mdettlaff.cloudreader.domain.FeedItem;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

public class Guids {

	private final EntityManager em;

	public Guids(EntityManager em) {
		this.em = em;
	}

	public void removeDeleted(List<FeedItem> items) {
		Set<String> allGuids = getGuids();
		for (Iterator<FeedItem> it = items.iterator(); it.hasNext();) {
			FeedItem item = it.next();
			boolean found = ((long) em
					.createQuery(
							"SELECT COUNT(i) FROM FeedItem i WHERE guid = :guid")
					.setParameter("guid", item.getGuid()).getSingleResult()) > 0;
			boolean wasDeleted = !found && allGuids.contains(item.getGuid());
			if (wasDeleted) {
				it.remove();
			}
			allGuids.add(item.getGuid());
		}
		saveGuids(allGuids);
	}

	private Set<String> getGuids() {
		String value = getDictionaryValue("guids");
		return Sets.newHashSet(StringUtils.split(value, "\n"));
	}

	private void saveGuids(Set<String> guids) {
		String value = StringUtils.join(guids, "\n");
		updateDictionaryValue("guids", value);
	}

	private String getDictionaryValue(String key) {
		Object result = em
				.createNativeQuery(
						"SELECT value FROM dictionary WHERE key = :key")
				.setParameter("key", key).getSingleResult();
		try {
			String value;
			if (result instanceof String) {
				value = (String) result;
			} else if (result instanceof Clob) {
				Clob clob = (Clob) result;
				value = clob.getSubString(1, (int) clob.length());
			} else {
				throw new RuntimeException("Unknown type of result: " + result);
			}
			return value;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private void updateDictionaryValue(String key, String value) {
		em.createNativeQuery(
				"UPDATE dictionary SET value = :value WHERE key = :key")
				.setParameter("value", value).setParameter("key", key)
				.executeUpdate();
	}
}
