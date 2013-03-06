package com.woni.firefighter.noe;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import woni.FireFighter.District;
import woni.FireFighter.IConfiguration;
import woni.FireFighter.Mission;

public class Configuration implements IConfiguration {

	public Hashtable<String, String> getDistricts() {
		Hashtable<String, String> districts = new Hashtable<String,String>();
		
		districts.put("01", "Bezirk Amstetten");
		
		return districts;
	}

	public String getUrl(District district) {
		return String.format("http://www.feuerwehr-krems.at/CodePages/Wastl/WastlMain/Land_EinsatzHistorie.asp?bezirk=%s", district.getShortText());
	}

	public List<Mission> parseMissions(Document document) {
		Element centerElement = document.getElementsByTag("body").first();
		Element table = centerElement.getElementsByTag("table").first();
		Elements rows = table.getElementsByTag("tr");
		List<Mission> missions = new ArrayList<Mission>();

		for (Element row : rows) {
			String[] fieldValues = new String[5];
			Elements fields = row.getElementsByTag("td");
			if (fields.size() > 0) {
				int iterator = 0;

				for (Element td : fields) {
					fieldValues[iterator] = td.text();
					iterator++;
				}
				
				String[] dateTime = fieldValues[4].split(" ");
				missions.add(new Mission(dateTime[0], dateTime[1], fieldValues[3], fieldValues[2]));
			}
		}

		return missions;
	}

}
