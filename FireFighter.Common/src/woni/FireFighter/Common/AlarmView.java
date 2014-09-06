package woni.FireFighter.Common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.woni.firefighter.common.R;

import android.R.color;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class AlarmView extends LinearLayout {
	Context context;
	private String[] url;
	private ArrayList<AlarmViewLoadedListener> listeners = new ArrayList<AlarmViewLoadedListener>();
	private Boolean isLoaded = false;
	private String shortDistrictName;
	private IConfiguration configuration;
	
	private ArrayList<Mission> loadedMissions = new ArrayList<Mission>();
	RetreiveMissionsTask task;
	SwipeRefreshLayout swiper;
	private District district;

	public AlarmView(Context context, District district) {
		super(context);
		this.context = context;
		this.district = district;
	}

	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public void Initialize(Context context, IConfiguration configuration){
		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.alarmview, this);
		
		this.context = context;
		
		this.url = configuration.getUrl(district);
		this.configuration = configuration;
		final IConfiguration config = configuration;
		this.shortDistrictName = district.getShortText();

		if(config.getHintEnabled()){
		config.setOnHintChanged(new HintChangedListener(){

			public void onHintChanged(Boolean value) {
				HideHint();
			}});
		} else {
			HideHint();
		}
		
		TextView view = (TextView) findViewById(R.id.title);
		view.setText(district.getLongText());
		
		swiper = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swiper.setColorSchemeColors(Color.parseColor("#B73D3D"), Color.parseColor("#D75D5D"), Color.parseColor("#F77D7D"), Color.parseColor("#FF9D9D"));
		swiper.setOnRefreshListener(new OnRefreshListener() {
			public void onRefresh() {
				updateData();
				if(config != null)
					config.setHintEnabled(false);
			}
		});
	}
	
	private void HideHint(){
		View view = findViewById(R.id.hint);
		view.setVisibility(View.GONE);
	}
	
	public void cancel(){
		if(task != null)
			task.cancel(true);
	}
	
	private int index;
	
	public void updateData() {
		cancel();
		
		swiper.setRefreshing(true);

		final View connectionlost = findViewById(R.id.connectionLost);
		connectionlost.setVisibility(View.GONE);
		
		task = new RetreiveMissionsTask(context, configuration);

		task.setOnDocumentUpdateListener(new MissionReceivedListener() {
			public void onClear(Context activity) {
				index = 0;
			}
			
			public Boolean onItemLoaded(Context activity, Mission mission){
				System.out.println(String.format("Mission %s displaying at %s", mission.station, new Date()));
				ListView bookListView = (ListView) findViewById(R.id.bookListView);
				ListItemAdapter adapter = (ListItemAdapter)bookListView.getAdapter();
				
				if(index == 0){
					if(adapter != null)
						adapter.clear();
					loadedMissions.clear();
				}
				
				if(loadedMissions.contains(mission)){
					adapter.notifyDataSetChanged();
					return true;  //Item is already there
				}
				
				if(adapter == null){
					ListItemAdapter newAdapter = new ListItemAdapter(activity, R.layout.row);
					bookListView.setAdapter(newAdapter);
					adapter = newAdapter;
				}
				
				adapter.insert(mission, index);
				adapter.notifyDataSetChanged();
				index++;
				
				loadedMissions.add(mission);
				return true;
			}
			
			public void onItemsCompleted(Context activity){
				onLoaded();
			}

			public void onFailed(Context activity, Exception exception) {
				connectionlost.setVisibility(View.VISIBLE);
				onLoaded();
			}

		});

		task.execute(url);
	}
	
	public Boolean getIsLoaded(){
		return isLoaded;
	}

	public void setLoadedListener(AlarmViewLoadedListener listener) {
		listeners.add(listener);
	}

	private void onLoaded() {
		isLoaded = true;
		for (AlarmViewLoadedListener listener : listeners) {
			listener.onLoaded();
		}
		if(swiper != null)
			swiper.setRefreshing(false);
		task = null;
	}
	
	public String getDistrict(){
		return shortDistrictName;	
	}
}
