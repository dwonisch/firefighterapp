package woni.FireFighter.Common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;

import com.egoclean.android.widget.flinger.ViewFlinger;
import com.egoclean.android.widget.flinger.ViewFlinger.OnScreenChangeListener;
import com.woni.firefighter.common.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FireFighterActivity extends FragmentActivity {

	private static String SettingsFile = "FireFighter.settings";
	private static String LastSelectedDistrictKey = "LastSelectedDistrict";
	private static String LastSelectedPageKey = "LastSelectedPage";
	private static String HintEnabledKey = "HintEnabled";
	protected IConfiguration configuration;
	private ViewPager pager;
	private SectionsPagerAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final SharedPreferences settings = getSharedPreferences(SettingsFile, 0);
		int lastDistrict = settings.getInt(LastSelectedPageKey, 0);
		Boolean hintEnabled = settings.getBoolean(HintEnabledKey, true);

		configuration.setHintEnabled(hintEnabled);

		configuration.setOnHintChanged(new HintChangedListener() {
			public void onHintChanged(Boolean value) {
				Editor editor = settings.edit();
				editor.putBoolean(HintEnabledKey, value);
				editor.commit();
			}
		});
		adapter = new SectionsPagerAdapter(getSupportFragmentManager(),
				configuration.getDistricts(), configuration);

		// Set up the ViewPager with the sections adapter.
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int position) {
				SharedPreferences settings = getSharedPreferences(SettingsFile,0);

				PlaceholderFragment oldView = getView(settings.getInt(LastSelectedPageKey, 0));
						if(oldView != null)
							oldView.cancelLoad();
				
				Editor editor = settings.edit();
				editor.putInt(LastSelectedPageKey, position);
				editor.commit();
			}

			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// Code goes here
			}

			public void onPageScrollStateChanged(int state) {
				// Code goes here
			}
		});

		pager.setCurrentItem(lastDistrict);
	}

	public void onSettingsClick(View view) {

	}

	private PlaceholderFragment getCurrentView() {
		return getView(pager.getCurrentItem());
	}
	
	private PlaceholderFragment getView(int view) {
		return (PlaceholderFragment) adapter.get(view);
	}

	public void onRefresh(View view) {
		PlaceholderFragment fragment = getCurrentView();
		fragment.updateData();
	}
	
	public void openLink(View view){
		String url = configuration.getFallbackLink();
		try{
		PlaceholderFragment fragment = getCurrentView();
		url = String.format(configuration.getLink(), fragment.getName());
		} catch(Exception ex){}
		
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}
}
