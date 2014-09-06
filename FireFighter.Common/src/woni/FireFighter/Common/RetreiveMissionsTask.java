package woni.FireFighter.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.os.AsyncTask;

public class RetreiveMissionsTask extends AsyncTask<String, Mission, Void> {

	private Exception exception;
	private Context activity;
	private MissionReceivedListener listener;
	private IConfiguration configuration;

	public RetreiveMissionsTask(Context activity, IConfiguration configuration) {
		this.activity = activity;
		this.configuration = configuration;
	}

	protected Void doInBackground(String... url) {
		BufferedReader missionReader = null;
		BufferedReader unitReader = null;
		try {
			listener.onClear(activity);
			
			missionReader = CreateReader(url[0]);
			ArrayList<Mission> activeMissions = configuration.parseMissions(this, missionReader);
			
			if(activeMissions.size() > 0){
				unitReader = CreateReader(url[1]);
				HashMap<String, ArrayList<String>> units = configuration.parseUnits(this, unitReader);
				
				for(Mission mission : activeMissions){
					if(units.containsKey(mission.getStation())){
						mission.setUnits(units.get(mission.getStation()));
						publishProgress(mission);
					}
				}
			}
		} catch (IOException ioEx){
			this.exception = ioEx;
		} catch (Exception e) {
			this.exception = e;
		} finally{
			if(missionReader != null)
				try {
					missionReader.close();
				} catch (IOException e) {
				}
			if(unitReader != null)
				try {
					unitReader.close();
				} catch (IOException e) {
				}
		}
		return null;
	}

	private BufferedReader CreateReader(String url)
			throws MalformedURLException, IOException {
		BufferedReader reader;
		URL page = new URL(url);
		System.out.println("Reading " + url);
		reader = new BufferedReader(new InputStreamReader(page.openStream()));
		return reader;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}
	
	protected void onProgressUpdate(Mission... values){
		if(!listener.onItemLoaded(activity, values[0])){
			cancel(true); //Item already exists, stop loading of other items
		}
	}

	@Override
	protected void onPostExecute(Void object) {
		if(listener != null){
			if (exception != null) {
				listener.onFailed(activity, exception);
			}
			listener.onItemsCompleted(activity);
		}
	}
	
	@Override
	protected void onCancelled() {
		if(listener != null)
			listener.onItemsCompleted(activity);
	}
	
	public void setOnDocumentUpdateListener(MissionReceivedListener listener) {
		this.listener = listener;
	}

	public void setItem(Mission mission) {
		publishProgress(mission);
	}
}
