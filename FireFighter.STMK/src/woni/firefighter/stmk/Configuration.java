package woni.FireFighter.stmk;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import woni.FireFighter.Common.HintChangedListener;
import woni.FireFighter.Common.IConfiguration;
import woni.FireFighter.Common.District;
import woni.FireFighter.Common.Mission;
import woni.FireFighter.Common.RetreiveMissionsTask;

public class Configuration implements IConfiguration {
	Pattern pattern = Pattern
			.compile("<td>(([&;\\.0-9A-Za-z :/ÄÖÜäöüß-]*)|(<img src='[A-Za-z\\.0-9/]*'>))</td>");
	private Boolean hintEnabled = true;
	ArrayList<HintChangedListener> listener = new ArrayList<HintChangedListener>();

	public LinkedHashMap<String, String> getDistricts() {
		LinkedHashMap<String, String> districts = new LinkedHashMap<String, String>();

		districts.put("BM", "Bereich Bruck an der Mur");
		districts.put("DL", "Bereich Deutschlandsberg");
		districts.put("FB", "Bereich Feldbach");
		districts.put("FF", "Bereich Fürstenfeld");
		districts.put("GU", "Bereich Graz Umgebung");
		districts.put("HB", "Bereich Hartberg");
		districts.put("JU", "Bereich Judenburg");
		districts.put("KF", "Bereich Knittelfeld");
		districts.put("LB", "Bereich Leibnitz");
		districts.put("LE", "Bereich Leoben");
		districts.put("LI", "Bereich Liezen");
		districts.put("MU", "Bereich Murau");
		districts.put("MZ", "Bereich Mürzzuschlag");
		districts.put("RA", "Bereich Radkersburg");
		districts.put("VO", "Bereich Voitsberg");
		districts.put("WZ", "Bereich Weiz");

		return districts;
	}

	public String[] getUrl(District district) {
		String[] urls = new String[2];
		urls[0] = String
				.format("http://178.188.171.236/rpweb/onlinestatus.aspx?form=EVENT&bez=%s",
						district.getShortText());
		urls[1] = String
				.format("http://178.188.171.236/rpweb/onlinestatus.aspx?form=UNIT&bez=%s",
						district.getShortText());
		return urls;
	}

	public ArrayList<Mission> parseMissions(RetreiveMissionsTask task, BufferedReader reader) {
		ArrayList<Mission> activeMissions = new ArrayList();
		
		char[] buffer = new char[128];
		StringBuilder builder = new StringBuilder(1024);
		int length;

		int parsePosition = 0;
		String[] resultBuffer = new String[8];
		int resultCounter = 0;

		try {
			while ((length = reader.read(buffer)) > 0) {
				builder.append(buffer);

				Matcher matcher = pattern.matcher(builder);
				while (matcher.find(parsePosition)) {
					resultBuffer[resultCounter] = matcher.group(1);
					resultCounter++;
					if (resultCounter == 8) {
						resultCounter = 0;
						
						Mission mission = new Mission(resultBuffer);
						if(mission.getOnDuty())
							activeMissions.add(mission);

						task.setItem(mission);
					}
					parsePosition = matcher.end();
				}

				builder.delete(0, parsePosition);
				parsePosition = 0;

				if (task.isCancelled()) {
					break;
				}
			}
		} catch (IOException e) {
		} 
		
		return activeMissions;
	}

	public HashMap<String, ArrayList<String>> parseUnits(
			RetreiveMissionsTask task, BufferedReader reader) {
		HashMap<String, ArrayList<String>> units = new HashMap<String, ArrayList<String>>();

		char[] buffer = new char[128];
		StringBuilder builder = new StringBuilder(1024);
		int length;

		int parsePosition = 0;
		String[] resultBuffer = new String[7];
		int resultCounter = -6; // skip first 6 matches

		try {
			while ((length = reader.read(buffer)) > 0) {
				builder.append(buffer);

				Matcher matcher = pattern.matcher(builder);
				while (matcher.find(parsePosition)) {
					if (resultCounter < 0)
						resultCounter++;
					else {
						resultBuffer[resultCounter] = matcher.group(1);
						resultCounter++;
						if (resultCounter == 7) {
							String key = resultBuffer[6].replace("&nbsp;", "");
							if (!key.equals("")) {
								if (!units.containsKey(key)) {
									units.put(key, new ArrayList<String>());
								}
								units.get(key).add(resultBuffer[2]);
							}
							resultCounter = 0;
						}
					}
					parsePosition = matcher.end();
				}

				builder.delete(0, parsePosition);
				parsePosition = 0;

				if (task.isCancelled())
					return units;
			}
		} catch (IOException e) {
		}

		return units;
	}

	public void setHintEnabled(Boolean enabled) {
		hintEnabled = enabled;
		
		for(HintChangedListener l : listener)
			l.onHintChanged(enabled);
	}

	public Boolean getHintEnabled() {
		return hintEnabled;
	}

	public void setOnHintChanged(HintChangedListener listener) {
		this.listener.add(listener);
	}

	public String getLink() {
		return "http://www.lfv.steiermark.at/Home/Landesleitzentrale/LLZ-Einsatzuebersicht/%s.aspx";
	}

	public String getFallbackLink() {
		return "http://www.lfv.steiermark.at/Home/Landesleitzentrale/LLZ-Einsatzuebersicht/Einsatzuebersicht-Steiermark.aspx";
	}
	
	
}
