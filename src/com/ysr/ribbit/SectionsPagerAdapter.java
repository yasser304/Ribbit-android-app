package com.ysr.ribbit;

import java.util.Locale;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	
	protected Context mContext;
	
	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
//		Fragment fragment = new InboxFragment();
//		Bundle args = new Bundle();
//		args.putInt(InboxFragment.ARG_SECTION_NUMBER, position + 1);
//		fragment.setArguments(args);
		
		switch(position){
			case 0:
				return new InboxFragment();
				
			case 1:
				return new FriendsFragment();
				
		}
		return null;
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return mContext.getString(R.string.inbox_tab_title).toUpperCase(l);
		case 1:
			return mContext.getString(R.string.friends_tab_title).toUpperCase(l);
		}
		return null;
	}
}
