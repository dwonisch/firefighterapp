package woni.FireFighter.Common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.woni.firefighter.common.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private LinkedHashMap<String,String> data;
	private List<String> keys;
	private List<String> values;
	private IConfiguration configuration;
	private Context context;
	
    public SectionsPagerAdapter(Context context, FragmentManager manager, LinkedHashMap<String,String> data, IConfiguration configuration) {
        super(manager);
        this.data = data;
        this.configuration = configuration;
        values = new ArrayList<String>(data.values());
        keys = new ArrayList<String>(data.keySet());
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceholderFragment.newInstance(context, position +1, keys.get(position), values.get(position), configuration);
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