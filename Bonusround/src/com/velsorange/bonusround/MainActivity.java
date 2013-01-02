package com.velsorange.bonusround;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	private static String nev = "";
	private static String admin = "";
	public static DbAdapter dbHelper;
	public static String fejlec = "�dv�zletem:)";
	public String[] values;
	public boolean oarcbor;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);

	    oarcbor=true;
	    // Checks the orientation of the screen
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	        Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	        Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
	    }
	}
	protected void onResume() {
		super.onResume();
		if (!oarcbor){
		//k�v�r�l �rkez�nk
			nev="Nincs bejelentkezve";
			admin="false";
			fejlec="�dv�zletem:)";
			
			
		} else {}
		oarcbor=false;
		menu();
		Log.d("menu fut�sa", "onResume-ben ut�n");
		if (admin.compareTo("true") == 0) {
			values = new String[] { "B�nuszk�r", "V�telez�s", "Standol�s",
					"Inform�ci�k", "Karbantart�s" };
		} else if (nev == "Nincs bejelentkezve") {
			values = new String[] { "Bejelenkez�s", "Kil�p�s" };

		} else if (nev == "Nincs felhaszn�l�") {
			values = new String[] { "�j felhaszn�l�", "Kil�p�s" };

		} else if (admin.compareTo("false")==0){
			values = new String[] { "B�nuszk�r", "V�telez�s", "Standol�s"};
		}

		if (values == null) {

		} else {

			setListAdapter(new ArrayAdapter<String>(this,
					R.layout.activity_main, values));

			ListView listView = getListView();
			listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long i) {
					menuValaszt(position);
				}
			});
		}
	}

	public void menuValaszt(int i) {
		switch (i) {
		case 0:
			if (nev == "Nincs felhaszn�l�") {
				Intent intent = new Intent(getBaseContext(),
						UjFelhasznalo.class);
				startActivityForResult(intent, 2);
			} else if (nev == "Nincs bejelentkezve") {
				Intent intent = new Intent(getBaseContext(),
						LoginActivity.class);
				startActivityForResult(intent, 3);
			}
			break;
		case 1:
			finish();
			onDestroy();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		// String fejlec ="nincs";
		
	}

	
	@Override
    protected void onDestroy() {
		oarcbor=true;
        super.onDestroy();
        
    } 
	
	int j = 10;

	private void menu() {

		dbHelper = new DbAdapter(this);
		dbHelper.open();
		if ((dbHelper.osszFelhasznalok().getCount() > 0)
				&& (fejlec.compareTo("�dv�zletem:)") == 0) && (nev.isEmpty() ||
				nev.compareTo("Nincs bejelentkezve")==0)) {
			this.setTitle("Nincs bejelentkezve");
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
		} else if ((dbHelper.osszFelhasznalok().getCount() == 0)
				) {
			Log.d("mainactivity", "�jfelhasznal�.class ind�t");
			Intent intent = new Intent(getBaseContext(), UjFelhasznalo.class);

			startActivityForResult(intent, 2);

		}  else	setTitle(nev);
	

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		oarcbor=true;
		if (resultCode == RESULT_CANCELED) {

		} else {
			if (requestCode == 2) {
				Bundle MBuddle = data.getExtras();
				nev = MBuddle.getString("nev");
				admin = MBuddle.getString("admin");
				fejlec = "";

			}
			if (requestCode == 3) {
				Bundle MBuddle = data.getExtras();

				nev = MBuddle.getString("nev");
				admin = MBuddle.getString("admin");
				fejlec = "";

			}
		}

		
	}

	public void beUj() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
