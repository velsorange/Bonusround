package com.velsorange.bonusround;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import com.velsorange.bonusround.EditNameDialog.EditNameDialogListener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends FragmentActivity implements
		EditNameDialogListener {
	Intent intent = getIntent();
	String nev = "nev";
	String nevertek = "Nincs bejelentkezve";
	String r = "r";
	String rertek = "mégsem";
	String admin = "admin";
	String adminertek = "";
	int result;
	private String s;
	private String key;

	private void showEditDialog() {
		FragmentManager fm = getSupportFragmentManager();
		EditNameDialog editNameDialog = new EditNameDialog();
		editNameDialog.show(fm, "fragment_edit_name");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {

		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		showEditDialog();
	}

	@Override
	public void onFinishEditDialog(String inputText)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IOException {
		if (inputText.length() == 0) {
			Toast.makeText(this, "nincs", Toast.LENGTH_LONG).show();
		} else {
			key = inputText;
			// s a filepath itt kell encrpytálni
			
			Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	// private static final String[] DUMMY_CREDENTIALS = new String[] {
	// "foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	// public static final String EXTRA_EMAIL =
	// "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			nevertek = "Nincs bejelentkezve";
			adminertek = "false";
			rertek = "mégsem";
			intent = getIntent();
			intent.putExtra(nev, nevertek);
			intent.putExtra(admin, adminertek);

			if (getParent() == null) {
				setResult(Activity.RESULT_CANCELED, intent);
			} else {
				getParent().setResult(Activity.RESULT_CANCELED, intent);
			}

			setResult(RESULT_CANCELED, intent);
			// finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {

		showProgress(false);
		super.onPause();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		Log.d("login", "setcontentview után");
		// Set up the login form.
		// mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.fnev);
		mEmailView.setText(mEmail);

		mPasswordView = (EditText) findViewById(R.id.loginj);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.fnev || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			Cursor c = MainActivity.dbHelper.osszFelhasznalok();

			if (c != null) {
				Boolean b = false;
				if (c.moveToFirst()) {

					do {
						if (mEmail.compareTo(c.getString(c
								.getColumnIndex("nev"))) == 0) {
							b = true;
							if (mPassword.compareTo(c.getString(c
									.getColumnIndex("p"))) == 0) {
								// jó a pass így belép

								nevertek = mEmail;
								// intent.putExtra("nev", mEmail);
								if (c.getString(c.getColumnIndex("admin"))
										.compareTo("true") == 0) {
									adminertek = "true";
									// intent.putExtra("admin", "true");
								} else {
									adminertek = "false";
									// intent.putExtra("admin", "false");
								}
								// rertek="siker";
								// setResult(RESULT_OK, intent);
								intent = getIntent();
								intent.putExtra(nev, nevertek);
								intent.putExtra(admin, adminertek);

								if (getParent() == null) {
									setResult(Activity.RESULT_OK, intent);
								} else {
									getParent().setResult(Activity.RESULT_OK,
											intent);
								}

								setResult(RESULT_OK, intent);
								finish();

							} else {
								Toast.makeText(
										this,
										"A felhasználónév és/vagy a jelszó nem egyezik",
										Toast.LENGTH_LONG).show();
								mEmailView.setText("");
								mPasswordView.setText("");
								mEmailView.requestFocus();

							}
							mEmailView
									.setError(getString(R.string.error_login));
							focusView = mEmailView;
							cancel = true;
						}

					} while (c.moveToNext());
					if (!b) {

						Toast.makeText(
								this,
								"A felhasználónév és/vagy a jelszó nem egyezik",
								Toast.LENGTH_LONG).show();
						mEmailView.setText("");
						mPasswordView.setText("");
						mEmailView.requestFocus();

					}
				} else {
				}
			}
			showProgress(false);

		}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	/*
	 * public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
	 * 
	 * @Override protected Boolean doInBackground(Void... params) { // TODO:
	 * attempt authentication against a network service.
	 * 
	 * try { // Simulate network access. Thread.sleep(2000); } catch
	 * (InterruptedException e) { return false; }
	 * 
	 * for (String credential : DUMMY_CREDENTIALS) { String[] pieces =
	 * credential.split(":"); if (pieces[0].equals(mEmail)) { // Account exists,
	 * return true if the password matches. return pieces[1].equals(mPassword);
	 * } }
	 * 
	 * // TODO: register the new account here. return true; }
	 * 
	 * @Override protected void onPostExecute(final Boolean success) {
	 * 
	 * showProgress(false);
	 * 
	 * if (success) { finish(); } else { mPasswordView
	 * .setError(getString(R.string.error_incorrect_password));
	 * mPasswordView.requestFocus(); } }
	 * 
	 * @Override protected void onCancelled() {
	 * 
	 * showProgress(false); } }
	 */
}
