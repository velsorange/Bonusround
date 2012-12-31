package com.velsorange.bonusround;



import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class UjFelhasznalo extends Activity {
	Intent intent = getIntent();
	String nev = "nev";
	String nevertek="Nincs bejelentkezve";
	String r="r";
	String rertek="mégsem";
	String admin="admin";
	String adminertek="false";
	
	int result=0;
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    	nevertek="Nincs bejelentkezve";
	    	adminertek="false";
	    	rertek="mégsem";
	    	
	    }
	    return super.onKeyDown(keyCode, event);
	}
	public static int db=0;
	public static Boolean jo=false;

	private static final String[] DUMMY_CREDENTIALS = new String[] {
		"foo@example.com:hello", "bar@example.com:world" };

/**
 * The default email to populate the email field with.
 */
public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";
public static Boolean nincsfelhasznalo=true;
/**
 * Keep track of the login task to ensure we can cancel it if requested.
 */
private UserLoginTask mAuthTask = null;

// Values for email and password at the time of the new user attempt.
private String mEmail;
private String mPassword;
private String mPassword2;

// UI references.
private EditText mEmailView;
private EditText mPasswordView;
private EditText mPasswordView2;
private View mLoginFormView;
private View mLoginStatusView;
private TextView mLoginStatusMessageView;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	db++;
	Log.d("újfelhasz", "db="+db);
	setContentView(R.layout.activity_uj_felhasznalo);

	// Set up the login form.
	//mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
	Log.d("ufelhasznaló", "mEmailview létrehozás elõtt");
	mEmailView = (EditText) findViewById(R.id.email);
	//mEmailView.setText("asszem email");

	mPasswordView = (EditText) findViewById(R.id.jelszo);
	mPasswordView2 = (EditText) findViewById(R.id.jelszo2);
		/*mPasswordView
			.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView textView, int id,
						KeyEvent keyEvent) {
					if (id == R.id.login || id == EditorInfo.IME_NULL) {
						attemptLogin();
						return true;
					}
					return false;
				}
			});*/

		mPasswordView2
		.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int id,
					KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
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
	getMenuInflater().inflate(R.menu.activity_uj_felhasznalo, menu);
	return true;
}

/**
 * Attempts to sign in or register the account specified by the login form.
 * If there are form errors (invalid email, missing fields, etc.), the
 * errors are presented and no actual login attempt is made.
 */
public void attemptLogin() {
		Log.d("ufelhasznaló", "mEmailview seterror elõtt");
		mEmailView.setError(null);
	
	Log.d("ufelhasznaló", "mEmailview seterror után");
	mPasswordView.setError(null);
	mPasswordView2.setError(null);

	// Store values at the time of the login attempt.
	mEmail = mEmailView.getText().toString();
	mPassword = mPasswordView.getText().toString();
	mPassword2 = mPasswordView2.getText().toString();
	boolean cancel = false;
	View focusView = null;

	// Check for a valid password.
	if (TextUtils.isEmpty(mPassword)) {
		mPasswordView.setError(getString(R.string.error_field_required));
		focusView = mPasswordView;
		cancel = true;
	} else if (TextUtils.isEmpty(mPassword2)) {
		mPasswordView2.setError(getString(R.string.error_field_required));
		focusView = mPasswordView2;
		cancel = true;
	} else  if (mPassword.length() < 4) {
		mPasswordView.setError(getString(R.string.error_invalid_password));
		focusView = mPasswordView;
		cancel = true;
	}else if (!mPassword.equals(mPassword2)) {
		mPasswordView2.setError(getString(R.string.error_jelszo_nem_egyezik));
		focusView = mPasswordView2;
		cancel = true;
	}
	Toast.makeText(getApplicationContext(), "p1:"+mPassword+" p2:"+mPassword2, Toast.LENGTH_LONG);
	
	// Check for a valid email address.
	if (TextUtils.isEmpty(mEmail)) {
		mEmailView.setError(getString(R.string.error_field_required));
		focusView = mEmailView;
		cancel = true;
	} 
	else{
	Cursor c = MainActivity.dbHelper.osszFelhasznalok();
	
	if (c!=null){
		
		if (c.moveToFirst()) {
			nincsfelhasznalo=false;
	        do {
	            if (c.getString(c.getColumnIndex("nev"))==mEmail.toString() ){
	            	cancel = true;
	            	mEmailView.setError(getString(R.string.error_van_mar));
	            	focusView = mEmailView;
	        		
	            }
	            
	        } while (c.moveToNext());
		}else{nincsfelhasznalo=true;} 
	}
	}
	if (cancel) {
		// There was an error; don't attempt login and focus the first
		// form field with an error.
		focusView.requestFocus();
	} else {
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
		showProgress(true);
	//annak a felhasználónak idje kell aki létrehozza az új felhasználót!!!!!!!!!!!!!  
		int i=1;
		if (nincsfelhasznalo) {i=1;}
		if (UserCreate(nincsfelhasznalo.toString(),i)){
			Toast.makeText(getApplicationContext(), "Létrejött a(z) "+mEmail+" nevû felhasználó!",
					Toast.LENGTH_LONG).show();
				nevertek=mEmail;	
				//intent.putExtra("nev", mEmail);
				if (nincsfelhasznalo==true){
					adminertek="true";
					//intent.putExtra("admin", "true");
				}else {
					adminertek="false";
					//intent.putExtra("admin", "false");
				}
				
				//setResult(RESULT_OK, intent);
				onPause();
			 
			    
		
			
		}
		//mAuthTask = new UserLoginTask();
		//mAuthTask.execute((Void) null);
	}
}
@Override
protected void onPause() {
    intent=getIntent();
    intent.putExtra(nev, nevertek);
    intent.putExtra(admin, adminertek);
  
    setResult(-1,intent);
    showProgress(false);
    super.onPause();
    finish();
}
@Override
protected void onDestroy() {
    
    super.onDestroy();
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
public Boolean UserCreate(String s,int i){
	MainActivity.dbHelper.createFelhasznalo(mEmail, mPassword, s, i);
	return true;
}
public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO: attempt authentication against a network service.

		try {
			// Simulate network access.
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			return false;
		}

		for (String credential : DUMMY_CREDENTIALS) {
			String[] pieces = credential.split(":");
			if (pieces[0].equals(mEmail)) {
				// Account exists, return true if the password matches.
				return pieces[1].equals(mPassword);
			}
		}

		// TODO: register the new account here.
		return true;
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		mAuthTask = null;
		showProgress(false);

		if (success) {
			finish();
		} else {
			mPasswordView
					.setError(getString(R.string.error_incorrect_password));
			mPasswordView.requestFocus();
		}
	}

	@Override
	protected void onCancelled() {
		//mAuthTask = null;
		showProgress(false);
	}

}
}
