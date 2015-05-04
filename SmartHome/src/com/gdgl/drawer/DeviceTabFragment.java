package com.gdgl.drawer;

import java.util.ArrayList;

import com.gdgl.activity.VideoFragment;
import com.gdgl.smarthome.R;
import com.gdgl.tabstrip.PagerSlidingTabStrip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DeviceTabFragment extends Fragment {
	View mView;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	private ArrayList<Fragment> mfragments;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.device_tab_fragment, null);
		
		pager = (ViewPager)mView.findViewById(R.id.pager);

		tabs = (PagerSlidingTabStrip)mView.findViewById(R.id.tabs);
		tabs.setShouldExpand(true);
		tabs.setUnderlineUpside(true);
		tabs.setIndicatorColorResource(R.color.blue_default);
		
		mfragments = new ArrayList<Fragment>();
		mfragments.add(new TestFragment());
		mfragments.add(new VideoFragment());
		adapter = new MyPagerAdapter(getChildFragmentManager(), mfragments);
		
		pager.setAdapter(adapter);
		tabs.setViewPager(pager);
		// TODO Auto-generated method stub
		return mView;
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { "传感器", "摄像头" };

		private ArrayList<Fragment> fragments;
		
	    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {  
	        super(fm);  
	        this.fragments = fragments;  
	    }
	    @Override  
	    public Fragment getItem(int pos) {  
	        return fragments.get(pos);  
	    }  
	  
		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}


	}
}
