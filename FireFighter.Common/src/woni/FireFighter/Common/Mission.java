package woni.FireFighter.Common;

public class Mission{

	String image;
	String date;
	String time;
	String kind;
	String alarm;
	String station;
	String count;
	String state;
	
	public Mission(String[] fields){
		this.image = fields[0];
		this.date = fields[1];
		this.time = fields[2];
		this.kind = fields[3];
		this.alarm = fields[4];
		this.station = fields[5];
		this.count = fields[6];
		this.state = fields[7];
	}
	
	public Mission(String date, String time, String alarm, String station) {
		this.date = date;
		this.time = time;
		this.alarm = alarm;
		this.station = station;
	}

	public String getImage(){
		return image;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getTime(){
		return time;
	}
	
	public String getKind(){
		return kind;
	}
	
	public String getAlarm(){
		return alarm;
	}
	
	public String getStation(){
		return station;
	}
	
	public String getCount(){
		return count;
	}
	
	public String getState(){
		return state;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof Mission))
			return false;
		if(o == this)
			return true;
		
		Mission other = (Mission)o;
		
		return station.equals(other.station) && date.equals(other.date) && time.equals(other.time);
	}
	
	@Override
	public int hashCode() {
	      return 17 * station.hashCode() * date.hashCode() * time.hashCode();
	}
}