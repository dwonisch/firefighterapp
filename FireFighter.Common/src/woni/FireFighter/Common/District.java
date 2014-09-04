package woni.FireFighter.Common;

public class District {
	private String shortText;
	private String longText;
	
	public District(String shortText, String longText){
		this.shortText = shortText;
		this.longText = longText;
	}
	
	public String getLongText(){
		return longText;
	}
	
	public String getShortText(){
		return shortText;
	}
}
