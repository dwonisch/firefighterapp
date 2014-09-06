package woni.FireFighter.Common;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.woni.firefighter.common.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private IConfiguration configuration;
	private AlarmView view;
	private District district;
	private int sectionnumber;

	public PlaceholderFragment(int sectionnumber, District district) {
		this.district = district;
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionnumber);
		setArguments(args);
		this.sectionnumber = sectionnumber;
	}

	public PlaceholderFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			if (savedInstanceState == null)
				view = new AlarmView(getActivity(), district);
			else {
				district = new District(
						savedInstanceState.getString("district.ShortText"),
						savedInstanceState.getString("district.LongText"));
				view = new AlarmView(getActivity(), district);
			}
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		FireFighterActivity activity = (FireFighterActivity) getActivity();
		configuration = activity.configuration;

		if (view != null){
			view.Initialize(getActivity(), configuration);
			view.updateData();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(ARG_SECTION_NUMBER, sectionnumber);
		outState.putString("district.ShortText", district.getShortText());
		outState.putString("district.LongText", district.getLongText());
		super.onSaveInstanceState(outState);
	}

	public void updateData() {
		if(view != null)
			view.updateData();
	}
	
	public void cancelLoad(){
		if(view != null)
			view.cancel();
	}
	
	public String getName(){
		return district.getLongText().replace("Bereich ", "");
	}
}