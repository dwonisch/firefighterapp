package com.woni.firefighter.noe;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import woni.FireFighter.Common.District;
import woni.FireFighter.Common.IConfiguration;
import woni.FireFighter.Common.Mission;

public class Configuration implements IConfiguration {

	public LinkedHashMap<String, String> getDistricts() {
		LinkedHashMap<String, String> districts = new LinkedHashMap<String,String>();
		
		districts.put("01", "Bezirk Amstetten");
		districts.put("02", "Bezirk Baden");
		districts.put("03", "Bezirk Bruck/Leitha");
		districts.put("04", "Bezirk Gänserndorf");
		districts.put("05", "Bezirk Gmünd");
		districts.put("061", "Abschnitt Klosterneuburg");
		districts.put("062", "Abschnitt Purkersdorf");
		districts.put("063", "Abschnitt Schwechat");
		districts.put("07", "Bezirk Hollabrunn");
		districts.put("08", "Bezirk Horn");
		districts.put("09", "Bezirk Stockerau");
		districts.put("10", "Bezirk Krems/Donau");
		districts.put("11", "Bezirk Lilienfeld");
		districts.put("12", "Bezirk Melk");
		districts.put("13", "Bezirk Mistelbach");
		districts.put("14", "Bezirk Mödling");
		districts.put("15", "Bezirk Neunkirchen");
		districts.put("17", "Bezirk St. Pölten");
		districts.put("18", "Bezirk Scheibs");
		districts.put("19", "Bezirk Tulln");
		districts.put("20", "Bezirk Waidhofen/Thaya");
		districts.put("21", "Bezirk Wr. Neustadt");
		districts.put("22", "Bezirk Zwettl");
		
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
