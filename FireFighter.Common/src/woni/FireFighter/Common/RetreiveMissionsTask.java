package woni.FireFighter.Common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
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
		BufferedReader reader = null;
		try {
			URL page = new URL(url[0]);
			System.out.println("Reading " + url[0]);
			reader = new BufferedReader(new InputStreamReader(page.openStream()));
			configuration.parseMissions(this, reader);
		} catch (IOException ioEx){
			this.exception = ioEx;
		} catch (Exception e) {
			this.exception = e;
		} finally{
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
		}
		return null;
	}
	
	protected void onProgressUpdate(Mission... values){
		System.out.println(String.format("Mission %s parsed at %s", values[0].station, new Date()));
		listener.onItemLoaded(activity, values[0]);
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
	protected void onCancelled(){
		if(listener != null){
			if (exception != null) {
				listener.onFailed(activity, exception);
			}
			listener.onItemsCompleted(activity);
		}
	}
	public void setOnDocumentUpdateListener(MissionReceivedListener listener) {
		this.listener = listener;
	}

	public void setItem(Mission mission) {
		publishProgress(mission);
	}
}
