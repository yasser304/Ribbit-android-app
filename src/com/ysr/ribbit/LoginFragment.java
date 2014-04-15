package com.ysr.ribbit;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginFragment extends Fragment {
	
	private static final String TAG = LoginFragment.class.getSimpleName();
	protected EditText mUsername;
	protected EditText mPassword;
	protected Button mLoginButton;
	
	private TextView mSignUpTextView;
	public LoginFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login,
				container, false);
		mSignUpTextView = (TextView)rootView.findViewById(R.id.sign_up_text);
		mSignUpTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),SignUpActivity.class);
				startActivity(intent);
			}
		});
		
		mUsername = (EditText) rootView.findViewById(R.id.login_username);
		mPassword = (EditText) rootView.findViewById(R.id.login_password);
		mLoginButton = (Button) rootView.findViewById(R.id.login_button);
		
		mLoginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().setProgressBarIndeterminateVisibility(true);
				
				String username = mUsername.getText().toString().trim();
				String password = mPassword.getText().toString().trim();
				
				if(username.isEmpty() || password.isEmpty()){
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(R.string.login_error_message)
							.setTitle(R.string.login_error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}else{
					ParseUser.logInInBackground(username, password, new LogInCallback() {
						
						@Override
						public void done(ParseUser user, ParseException e) {
							getActivity().setProgressBarIndeterminateVisibility(false);
							
							if(user != null){
								Log.i(TAG,user.getUsername());
								Intent intent = new Intent(getActivity(),MainActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}else{
								AlertDialog.Builder builder = new AlertDialog.Builder(
										getActivity());
								builder.setMessage(e.getMessage())
										.setTitle(R.string.login_error_title)
										.setPositiveButton(android.R.string.ok, null);
								AlertDialog dialog = builder.create();
								dialog.show();
							}
							
						}
					});
				}
			}
		});
		return rootView;
	}
}  