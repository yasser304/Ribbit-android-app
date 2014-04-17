package com.ysr.ribbit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class EditFriendsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_edit_friends);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new EditFriendsFragment()).commit();
		}
	}

}
