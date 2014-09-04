package woni.FireFighter.Common;

import java.util.HashMap;
import java.util.Map;

public class AlarmCategories {
	public AlarmCategories(){
		
	}
	
	private static Map<String, AlarmCategory> categories;
	
	public static AlarmCategory getCategoryByKey(String key){
		if(categories == null){
			categories = new HashMap<String, AlarmCategory>();
			
		    categories.put("B01", new AlarmCategory("Brandverdacht-Rauchentwicklung"));
		    categories.put("B02", new AlarmCategory("Müllkübel / Containerbrand"));
		    categories.put("B03", new AlarmCategory("Rauchfangbrand"));
		    categories.put("B04", new AlarmCategory("Elektrische Anlagen"));
		    categories.put("B05", new AlarmCategory("Zimmerbrand"));
		    categories.put("B06", new AlarmCategory("Brandmeldealarm"));
		    categories.put("B07", new AlarmCategory("Gasbrand, Gasaustritt, Gasunfall"));
		    categories.put("B08", new AlarmCategory("Fahrzeugbrand"));
		    categories.put("B09", new AlarmCategory("Hecken-, Wiesen-, Wald- und Böschungsbrand"));
		    categories.put("B10", new AlarmCategory("Keller- und Tiefgarangenbrand"));
		    categories.put("B11", new AlarmCategory("Brand Schienenfahrzeug"));
		    categories.put("B12", new AlarmCategory("Wohnhausbrand"));
		    categories.put("B13", new AlarmCategory("Wirtschaftsgebäudebrand"));
		    categories.put("B14", new AlarmCategory("Schulen, Kindergärten, Beherbergung, Veranstaltungshalle, Altenheim"));
		    categories.put("B15", new AlarmCategory("Industrie-, Werkstätten und Sägewerksbrand, Tankestelle, Heizhaus"));
		    categories.put("B16", new AlarmCategory("Tunnelbrand"));
		    categories.put("B17", new AlarmCategory("Hochhausbrand"));
		    categories.put("T01", new AlarmCategory("sonstige Hilfeleistung / Gerätebereitstellung"));
		    categories.put("T02", new AlarmCategory("Türöffnung, Personenrettung aus Lift"));
		    categories.put("T03", new AlarmCategory("Verkehrsunfall, Fahrzeugbergung, binden von Betriebsmittel"));
		    categories.put("T04", new AlarmCategory("Auspumparbeiten"));
		    categories.put("T05", new AlarmCategory("Insektenbekämpfung"));
		    categories.put("T06", new AlarmCategory("Suchaktion"));
		    categories.put("T07", new AlarmCategory("Unwettereinsatz, Elementare Ereignisse"));
		    categories.put("T08", new AlarmCategory("Tierrettung"));
		    categories.put("T09", new AlarmCategory("Wasserdienst"));
		    categories.put("T10", new AlarmCategory("Verkehrsunfall mit eingeklemmter Person"));
		    categories.put("T11", new AlarmCategory("Menschenrettung"));
		    categories.put("T12", new AlarmCategory("Busunfall"));
		    categories.put("T13", new AlarmCategory("Unfall Schienenfahrzeug"));
		    categories.put("T14", new AlarmCategory("Verkehrsunfall Autobahn"));
		    categories.put("T15", new AlarmCategory("Verkehrsunfall Tunnel"));
		    categories.put("T16", new AlarmCategory("Flugunfall"));
		    categories.put("T17", new AlarmCategory("Schadstoffeinsatz (Öl, Gefährliche Stoffe)"));
		}
		if(key.length() > 2) {
			String identifier = key.substring(0, 3);
			if(categories.containsKey(identifier))
				return categories.get(identifier);
		}
		return new AlarmCategory(key);
	}
}
