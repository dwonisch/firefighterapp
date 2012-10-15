package woni.FireFighter;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.os.AsyncTask;

class RetreiveMissionsTask extends AsyncTask<String, Void, List<Mission>> {

	private Exception exception;
	private Context activity;
	private ArrayList<MissionReceivedListener> listeners = new ArrayList<MissionReceivedListener>();

	public RetreiveMissionsTask(Context activity) {
		this.activity = activity;
	}

	protected List<Mission> doInBackground(String... url) {
		try {
			Document document = Jsoup.connect(url[0]).get();

			Element centerElement = document.getElementsByTag("center").first();
			Element table = centerElement.getElementsByTag("table").first();
			Elements rows = table.getElementsByTag("tr");
			List<Mission> missions = new ArrayList<Mission>();

			for (Element row : rows) {
				String[] fieldValues = new String[8];
				Elements fields = row.getElementsByTag("td");
				if (fields.size() > 0) {
					int iterator = 0;

					for (Element td : fields) {
						fieldValues[iterator] = td.text();
						iterator++;
					}
					missions.add(new Mission(fieldValues));
				}
			}

			return missions;
		} catch (Exception e) {
			this.exception = e;
			return new ArrayList<Mission>();
		}
	}

	protected void onPostExecute(List<Mission> missions) {
		for (MissionReceivedListener listener : listeners) {
			if (exception != null) {
				listener.onFailed(activity, exception);
			}
			listener.onCompleted(activity, missions);
		}
	}

	public void setOnDocumentUpdateListener(MissionReceivedListener listener) {
		this.listeners.add(listener);
	}
}
