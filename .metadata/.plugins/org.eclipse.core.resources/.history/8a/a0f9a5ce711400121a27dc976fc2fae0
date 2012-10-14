package woni.FireFighter;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class FireFighterActivity extends Activity {
	
	final String url = "http://178.188.171.236/rpweb/onlinestatus.aspx?form=EVENT&bez=RA";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        onRefresh();
    }
    
    public void onRefresh(View v){
    	onRefresh();
    }
    
    private void onRefresh(){
        final View progress = findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
    	final View refresh = findViewById(R.id.refresh);
    	refresh.setVisibility(View.GONE);
        final View connectionlost = findViewById(R.id.connectionLost);
        connectionlost.setVisibility(View.GONE);
    	
    	RetreiveMissionsTask task = new RetreiveMissionsTask(this);
    	task.setOnDocumentUpdateListener(new MissionReceivedListener(){

			public void onCompleted(Activity activity, List<Mission> missions) {
		        ListView bookListView =(ListView)findViewById(R.id.bookListView);
		        
		        LitemItemAdapter mcqListAdapter = new LitemItemAdapter(activity,R.layout.row,missions.toArray(new Mission[missions.size()]));
		        bookListView.setAdapter(mcqListAdapter);
		        
		        progress.setVisibility(View.GONE);
		        refresh.setVisibility(View.VISIBLE);
			}

			public void onFailed(Activity activity, Exception exception) {
				connectionlost.setVisibility(View.VISIBLE);
			}
    		
    	});
    		
    	task.execute(url);
    }
}