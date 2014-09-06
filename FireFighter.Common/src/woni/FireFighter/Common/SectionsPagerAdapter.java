package woni.FireFighter.Common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
	private LinkedHashMap<String,String> data;
	private List<String> keys;
	private List<String> values;
	private IConfiguration configuration;
	private FragmentManager manager;
	private Fragment[] fragments;
	
    public SectionsPagerAdapter(FragmentManager manager, LinkedHashMap<String,String> data, IConfiguration configuration) {
        super(manager);
        this.data = data;
        this.configuration = configuration;
        values = new ArrayList<String>(data.values());
        keys = new ArrayList<String>(data.keySet());
        fragments = new Fragment[data.size()];
    }

    @Override
    public Fragment getItem(int position) {
    	Fragment fragment = new PlaceholderFragment(position+1, new District(keys.get(position), values.get(position)));
    	fragments[position] = fragment;
    	return fragment;
    }
    
    public Fragment get(int position){
    	return fragments[position];
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
    	return values.get(position);
    }
}