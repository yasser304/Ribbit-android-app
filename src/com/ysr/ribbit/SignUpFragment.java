package com.ysr.ribbit;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpFragment extends Fragment {

	protected EditText mUsername;
	protected EditText mPassword;
	protected EditText mEmail;
	protected Button mSignUpButton;

	public SignUpFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_sign_up, container,
				false);

		mUsername = (EditText) rootView.findViewById(R.id.sign_up_username);
		mPassword = (EditText) rootView.findViewById(R.id.sign_up_password);
		mEmail = (EditText) rootView.findViewById(R.id.sign_up_email);
		mSignUpButton = (Button) rootView.findViewById(R.id.sign_up_button);
		
				
		mSignUpButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setProgressBarIndeterminateVisibility(true);
				
				String username = mUsername.getText().toString().trim();
				String password = mPassword.getText().toString().trim();
				String email = mEmail.getText().toString().trim();

				if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
					// Handle error
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(R.string.sign_up_error_message)
							.setTitle(R.string.sign_up_error_title)
							.setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				} else {
					// Sign em Up!
					ParseUser user = new ParseUser();
					user.setEmail(email);
					user.setPassword(password);
					user.setUsername(username);
					user.signUpInBackground(new SignUpCallback() {
						
						@Override
						public void done(ParseException e) {
							getActivity().setProgressBarIndeterminateVisibility(false);
							if(e == null){
								Intent intent = new Intent(getActivity(),MainActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
								startActivity(intent);
							}else{
								AlertDialog.Builder builder = new AlertDialog.Builder(
										getActivity());
								builder.setMessage(e.getMessage())
										.setTitle(R.string.sign_up_error_title)
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