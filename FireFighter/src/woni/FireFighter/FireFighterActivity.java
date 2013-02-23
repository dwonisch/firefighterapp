package woni.FireFighter;

import java.util.Hashtable;

import com.egoclean.android.widget.flinger.ViewFlinger;
import com.egoclean.android.widget.flinger.ViewFlinger.OnScreenChangeListener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class FireFighterActivity extends Activity {
	private ViewFlinger flinger;
	private static String SettingsFile = "FireFighter.settings";
	private static String LastSelectedDistrictKey = "LastSelectedDistrict";
    private Hashtable<String, View> viewCache = new Hashtable<String, View>();
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        flinger = (ViewFlinger)findViewById(R.id.views);
        flinger.setOnScreenChangeListener(new OnScreenChangeListener() {
			
			public void onScreenChanging(View newScreen, int newScreenIndex) {
				// TODO Auto-generated method stub
				
			}
			
			public void onScreenChanged(View newScreen, int newScreenIndex) {
				// TODO Auto-generated method stub
				if(newScreen != null){
					AlarmView alarms = (AlarmView)newScreen;
					if(!alarms.getIsLoaded())
						onRefresh();
					
					SharedPreferences settings = getSharedPreferences(SettingsFile, 0);
					Editor editor = settings.edit();
					editor.putString(LastSelectedDistrictKey, alarms.getDistrict());
					editor.commit();
				}
			}
		});
        
        flinger.addView(createView("RA", "Bereich Radkersburg"));
        flinger.addView(createView("VO", "Bereich Voitsberg"));
        flinger.addView(createView("WZ", "Bereich Weiz"));
        flinger.addView(createView("BM", "Bereich Bruck an der Mur"));
        flinger.addView(createView("DL", "Bereich Deutschlandsberg"));
        flinger.addView(createView("FB", "Bereich Feldbach"));
        flinger.addView(createView("FF", "Bereich Fürstenfeld"));
        flinger.addView(createView("GU", "Bereich Graz Umgebung"));
        flinger.addView(createView("HB", "Bereich Hartberg"));
        flinger.addView(createView("JU", "Bereich Judenburg"));
        flinger.addView(createView("KF", "Bereich Knittelfeld"));
        flinger.addView(createView("LB", "Bereich Leibnitz"));
        flinger.addView(createView("LE", "Bereich Leoben"));
        flinger.addView(createView("LI", "Bereich Liezen"));
        flinger.addView(createView("MU", "Bereich Murau"));
        flinger.addView(createView("MZ", "Bereich Mürzzuschlag"));
        
        SharedPreferences settings = getSharedPreferences(SettingsFile, 0);

        String lastDistrict = settings.getString(LastSelectedDistrictKey, "RA");
        
        flinger.setCurrentScreen(viewCache.get(lastDistrict));
        
        onRefresh();
    }
    
    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    private View createView(String shortText, String longText){
    	if(!viewCache.containsKey(shortText)){
	    	AlarmView view = new AlarmView(this,new District(shortText, longText));
	    	view.setLoadedListener(new AlarmViewLoadedListener() {
				
				public void onLoaded() {
					// TODO Auto-generated method stub
					View progress = findViewById(R.id.progress);
					progress.setVisibility(View.GONE);
					View refresh = findViewById(R.id.refresh);
					refresh.setVisibility(View.VISIBLE);
				}
			});
	    	
	    	viewCache.put(shortText,  view);
    	}
    	return viewCache.get(shortText);
    }
    
	public void onRefresh(){
		onRefresh((AlarmView)flinger.getCurrentScreen());
	}

	public void onRefresh(View v){
		onRefresh();
    }
	
	private void onRefresh(AlarmView view){
		View progress = findViewById(R.id.progress);
		progress.setVisibility(View.VISIBLE);
		View refresh = findViewById(R.id.refresh);
		refresh.setVisibility(View.GONE);
		
    	AlarmView v = view;
    	v.onRefresh();
	}
	
	public void onSettingsClick(View view){
		
	}
}