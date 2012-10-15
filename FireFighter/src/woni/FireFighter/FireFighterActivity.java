package woni.FireFighter;

import com.egoclean.android.widget.flinger.ViewFlinger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class FireFighterActivity extends Activity {
	ViewFlinger flinger;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        flinger = (ViewFlinger)findViewById(R.id.views);
        flinger.addView(createView("RA"));
        flinger.addView(createView("LB"));
        
        onRefresh(null);
    }
    
    private View createView(String area){
    	AlarmView view = new AlarmView(this,area);
    	view.setLoadedListener(new AlarmViewLoadedListener() {
			
			public void onLoaded() {
				// TODO Auto-generated method stub
				View progress = findViewById(R.id.progress);
				progress.setVisibility(View.GONE);
				View refresh = findViewById(R.id.refresh);
				refresh.setVisibility(View.VISIBLE);
			}
		});
    	return view;
    }
    

	public void onRefresh(View v){
		View progress = findViewById(R.id.progress);
		progress.setVisibility(View.VISIBLE);
		View refresh = findViewById(R.id.refresh);
		refresh.setVisibility(View.GONE);
		
    	AlarmView view = (AlarmView)flinger.getCurrentScreen();
    	view.onRefresh();
    }
}