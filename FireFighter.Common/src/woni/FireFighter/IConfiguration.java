package woni.FireFighter;

import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;

public interface IConfiguration {
	LinkedHashMap<String, String> getDistricts();
	String getUrl(District district);
	List<Mission> parseMissions(Document document);
}
