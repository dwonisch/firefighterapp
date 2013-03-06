package woni.FireFighter;

import java.util.Hashtable;
import java.util.List;

import org.jsoup.nodes.Document;

public interface IConfiguration {
	Hashtable<String, String> getDistricts();
	String getUrl(District district);
	List<Mission> parseMissions(Document document);
}
