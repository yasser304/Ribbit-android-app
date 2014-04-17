package com.ysr.ribbit;

import java.util.List;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditFriendsFragment extends ListFragment {

	public static final String TAG = EditFriendsFragment.class.getSimpleName();

	protected List<ParseUser> mUsers;
	protected ParseUser mCurrentUser;
	protected ParseRelation<ParseUser> mFriendsRelation;

	public EditFriendsFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container,
				savedInstanceState);
		// View rootView = inflater.inflate(R.layout.fragment_edit_friends,
		// container, false);
		ListView listView = (ListView) rootView.findViewById(android.R.id.list);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		mCurrentUser = ParseUser.getCurrentUser();
		mFriendsRelation = mCurrentUser
				.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

		getActivity().setProgressBarIndeterminateVisibility(true);
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.orderByAscending(ParseConstants.KEY_USERNAME);
		query.setLimit(1000);
		query.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> users, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				if (e == null) {
					// Success
					mUsers = users;
					String[] usernames = new String[mUsers.size()];
					int i = 0;

					for (ParseUser user : mUsers) {
						usernames[i] = user.getUsername();
						i++;
					}

					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getListView().getContext(),
							android.R.layout.simple_list_item_checked,
							usernames);
					setListAdapter(adapter);

					addFriendCheckmarks();

				} else {
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle(R.string.error_title).setPositiveButton(
							android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (getListView().isItemChecked(position)) {
			// add friend
			mFriendsRelation.add(mUsers.get(position));
		} else {
			// remove friend
			mFriendsRelation.remove(mUsers.get(position));
		}
		mCurrentUser.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException e) {
				if (e != null) {
					Log.e(TAG, e.getMessage());
				}
			}
		});
	}

	private void addFriendCheckmarks() {
		mFriendsRelation.getQuery().findInBackground(
				new FindCallback<ParseUser>() {

					@Override
					public void done(List<ParseUser> friends, ParseException e) {
						if (e == null) {
							for (int i = 0; i < mUsers.size(); i++) {
								ParseUser user = mUsers.get(i);
								for (ParseUser friend : friends) {
									if (friend.getObjectId().equals(
											user.getObjectId())) {
										getListView().setItemChecked(i, true);
									}
								}
							}
						} else {
							Log.e(TAG, e.getMessage());
						}
					}
				});
	}
}