package woni.FireFighter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class AlarmView extends LinearLayout {
	Context context;
	final String url;
	private ArrayList<AlarmViewLoadedListener> listeners = new ArrayList<AlarmViewLoadedListener>();

	public AlarmView(Context context, String url) {
		super(context);
		this.context = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.alarmview, this);
		this.url = String
				.format("http://178.188.171.236/rpweb/onlinestatus.aspx?form=EVENT&bez=%s",
						url);

		TextView view = (TextView) findViewById(R.id.title);
		view.setText(url);
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.alarmview, this);
		this.url = "http://178.188.171.236/rpweb/onlinestatus.aspx?form=EVENT&bez=RA";
		
		TextView view = (TextView) findViewById(R.id.title);
		view.setText(url);
	}

	public void onRefresh() {
		final View connectionlost = findViewById(R.id.connectionLost);
		connectionlost.setVisibility(View.GONE);

		RetreiveMissionsTask task = new RetreiveMissionsTask(context);
		task.setOnDocumentUpdateListener(new MissionReceivedListener() {

			public void onCompleted(Context activity, List<Mission> missions) {
				ListView bookListView = (ListView) findViewById(R.id.bookListView);

				LitemItemAdapter mcqListAdapter = new LitemItemAdapter(
						activity, R.layout.row, missions
								.toArray(new Mission[missions.size()]));
				bookListView.setAdapter(mcqListAdapter);
				onLoaded();
			}

			public void onFailed(Context activity, Exception exception) {
				connectionlost.setVisibility(View.VISIBLE);
				onLoaded();
			}

		});

		task.execute(url);
	}
	
	public void setLoadedListener(AlarmViewLoadedListener listener){
		listeners.add(listener);
	}
	
	private void onLoaded(){
		for(AlarmViewLoadedListener listener : listeners){
			listener.onLoaded();
		}
		
	}
}
