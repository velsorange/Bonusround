package com.velsorange.bonusround;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Karbantartas extends FragmentActivity {
	Intent intent = getIntent();
	String nev = "";
	public boolean oarcbor;
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		oarcbor = true;
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!oarcbor) {
			// kívûrõl érkezünk
			nev = "";
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
		} else {
		}
		oarcbor = false;
		menu();
	}
	private void menu() {
		//dbHelper = new DbAdapter(this);
		//dbHelper.open();
		if (nev.compareTo("")==0){
			this.setTitle("Nincs bejelentkezve");
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
		} else
			setTitle(nev);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		oarcbor = true;
		if (resultCode == RESULT_CANCELED) {
			nev="";
		} else {
			if (requestCode == 3) {
				Bundle MBuddle = data.getExtras();
				nev = MBuddle.getString("nev");
				setTitle(nev);
			}
		}
	}
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	    
	    	intent=getIntent();
	    	intent.putExtra("nev", nev);
			
			
			if (getParent() == null) {
			    setResult(Activity.RESULT_OK, intent);
			} else {
			    getParent().setResult(Activity.RESULT_OK, intent);
			}
			
		    setResult(RESULT_OK,intent);
			//finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_karbantartas);
		Bundle extras = this.getIntent().getExtras();
	//    nev = extras.getString("nev");
	    if (extras!=null){
	    	nev = extras.getString("nev");
	    	setTitle(nev);
	    	oarcbor=true;
		    }
		//nev=savedInstanceState.getBundle(nev).toString();
		//setTitle(nev);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_karbartartas, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			switch(position){
			case 0:
				Fragment fragment = new AruT();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				return fragment;
			default:

				Fragment fragment1 = new DummySectionFragment();
				Bundle args1 = new Bundle();
				args1.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment1.setArguments(args1);
				return fragment1;
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 7;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	/*-	ARUT);
	 - ARU);
	    ELADASIAR);
		AE);
	-SZALLITO);
		BESZERZESIAR);
	-VENDEG);
	-KTG_T);
	-FELHASZNALO);
	-EGYEB_BEVETEL_tipua);
	*/

	public static class AruT extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public AruT() {
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			View V = inflater.inflate(R.layout.karban_arut, container, false);
			ListView listView = (ListView) V.findViewById(R.id.karutlist);
			ImageButton fel = (ImageButton) V.findViewById(R.id.karutfel);
			ImageButton le = (ImageButton) V.findViewById(R.id.karutle);
			ImageButton uj = (ImageButton) V.findViewById(R.id.karutuj);
			
			ImageButton ment = (ImageButton) V.findViewById(R.id.karutment);
			EditText szoveg =(EditText)V.findViewById(R.id.karutszoveg);
			return V;
		}
	}
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}

}
