package woni.FireFighter.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jsoup.nodes.Document;

import android.content.Context;

public interface IConfiguration {
	LinkedHashMap<String, String> getDistricts();
	String[] getUrl(District district);
	String getLink();
	String getFallbackLink();
	ArrayList<Mission> parseMissions(RetreiveMissionsTask task, BufferedReader reader);
	HashMap<String, ArrayList<String>> parseUnits(RetreiveMissionsTask task, BufferedReader reader);
	void setHintEnabled(Boolean enabled);
	Boolean getHintEnabled();
	void setOnHintChanged(HintChangedListener listener);
}
