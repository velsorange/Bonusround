package com.velsorange.bonusround;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.DisplayMetrics;
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
	
	private static Cursor c = null;
	private static String nev = "";
	private static String admin = "";
	private static String vetelezes = "";
	private static String standolas = "";
	private static String koltseg = "";
	private static String bevetel = "";
	private static String informaciok = "";
	public static DbAdapter dbHelper;
	
	public static String fejlec = "�dv�zletem:)";
	public String[] values;
	public boolean oarcbor;
	public static final int TABLET_MIN_DP_WEIGHT = 450;
	private SimpleCursorAdapter dataAdapter;

	protected static boolean isSmartphoneOrTablet(Activity act) {
		DisplayMetrics metrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int dpi = 0;
		if (metrics.widthPixels < metrics.heightPixels) {
			dpi = (int) (metrics.widthPixels / metrics.density);
		} else {
			dpi = (int) (metrics.heightPixels / metrics.density);
		}

		if (dpi < TABLET_MIN_DP_WEIGHT)
			return true;
		else
			return false;
	}

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

	protected void onResume() {
		super.onResume();

		if (!oarcbor) {
			// k�v�r�l �rkez�nk
			nev = "Nincs bejelentkezve";
			admin = "false";
			fejlec = "�dv�zletem:)";
			vetelezes = "false";
			standolas = "false";
			koltseg = "false";
			bevetel = "false";
			informaciok = "false";

		} else {
		}
		oarcbor = false;
		menu();
		Log.d("menu fut�sa", "onResume-ben ut�n");

		c = null;
		if (dbHelper.getUserId(nev) == 0) {
		} else {
			c = dbHelper.fetchFoMenu(dbHelper.getUserId(nev));
		}

		/*
		 * if (admin.compareTo("true") == 0) { values = new String[] {
		 * "B�nuszk�r", "V�telez�s", "Standol�s", "Hitel", "K�lts�g", "Bev�tel",
		 * "Inform�ci�k", "Karbantart�s", "Kijelentkez�s", "Kil�p�s" }; } else
		 * if (nev == "Nincs bejelentkezve") { values = new String[] {
		 * "Bejelenkez�s", "Kil�p�s" };
		 * 
		 * } else if (nev == "Nincs felhaszn�l�") { values = new String[] {
		 * "�j felhaszn�l�", "Kil�p�s" };
		 * 
		 * } else if (admin.compareTo("false") == 0) { String s="\"B�nuszk�r\"";
		 * int db=3; if (vetelezes=="true"){ db++; } if (standolas=="true"){
		 * db++; } db++; if (koltseg=="true"){ db++; } if (bevetel=="true"){
		 * db++; } if (informaciok=="true"){ db++; } values = new String[db];
		 * values[0]="B�nuszk�r"; values[db-1]="Kil�p�s";
		 * values[db-2]="Kijelentkez�s"; db=0; if (vetelezes=="true"){ db++;
		 * values[db]="V�telez�s"; } if (standolas=="true"){ db++;
		 * values[db]="Standol�s"; } db++; values[db]="Hitel"; if
		 * (koltseg=="true"){ db++; values[db]="K�lts�g"; } if
		 * (bevetel=="true"){ db++; values[db]="Bev�tel"; } if
		 * (informaciok=="true"){ db++; values[db]="Inform�ci�k"; }
		 * 
		 * values = new String[] { "B�nuszk�r", "V�telez�s", "Standol�s",
		 * "Hitel", "K�lts�g", "Kijelentkez�s", "Kil�p�s" }; }
		 */

		if (c == null) {

		} else {

			String[] columns = new String[2];
			columns[0] = dbHelper.MENU_KEY_ROWID;
			columns[1] = dbHelper.MENU_KEY_MENU;

			// the XML defined views which the data will be bound to
			int[] to = new int[2];
			to[0] = R.id.tablaid;
			to[1] = R.id.fomenu;

			// create the adapter using the cursor pointing to the desired data
			// as well as the layout information
			setContentView(R.layout.activity_main);
			dataAdapter = new SimpleCursorAdapter(this, R.layout.fomenusor, c,
					columns, to, 0);

			ListView listView = (ListView) findViewById(android.R.id.list);
			// Assign adapter to ListView
			listView.setAdapter(dataAdapter);
			/*
			 * setListAdapter(new ArrayAdapter<String>(this,
			 * R.layout.activity_main, values));
			 * 
			 * ListView listView = getListView();
			 */
			// listView.setTextFilterEnabled(true);
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long i) {
					menuValaszt(position);
				}
			});
		}
	}

	public void menuValaszt(int i) {
		c.moveToPosition(i);
		String szoveg = c.getString(1);
		if (szoveg.compareTo("B�nuszk�r") == 0) {

		} else if (szoveg.compareTo("V�telez�s") == 0) {
		} else if (szoveg.compareTo("Standol�s") == 0) {
		} else if (szoveg.compareTo("Hitel") == 0) {
		} else if (szoveg.compareTo("K�lts�g") == 0) {
		} else if (szoveg.compareTo("Bev�tel") == 0) {
		} else if (szoveg.compareTo("Inform�ci�k") == 0) {
		} else if (szoveg.compareTo("Karbantart�s") == 0) {
			Intent intent = new Intent(getBaseContext(), Karbantartas.class);
			Bundle b = new Bundle();
			b.putString("nev", nev);
			intent.putExtras(b);
			startActivityForResult(intent, 4);
			Toast.makeText(this, "Karbantart�s", Toast.LENGTH_LONG)
					.show();
		} else if (szoveg.compareTo("Kijelentkez�s") == 0) {
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
			Toast.makeText(this, "Sikeres kijelentkez�s!", Toast.LENGTH_LONG)
					.show();
		} else if (szoveg.compareTo("Kil�p�s") == 0) {
			finish();
			Toast.makeText(this, "Sikeres kil�p�s!", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = new DbAdapter(this);
		dbHelper.open();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void menu() {
		//dbHelper = new DbAdapter(this);
		//dbHelper.open();
		if ((dbHelper.osszFelhasznalok().getCount() > 0)
				&& (fejlec.compareTo("�dv�zletem:)") == 0)
				&& (nev.isEmpty() || nev.compareTo("Nincs bejelentkezve") == 0)) {
			this.setTitle("Nincs bejelentkezve");
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
		} else if ((dbHelper.osszFelhasznalok().getCount() == 0)) {
			Log.d("mainactivity", "�jfelhasznal�.class ind�t");
			Intent intent = new Intent(getBaseContext(), UjFelhasznalo.class);
			startActivityForResult(intent, 2);
		} else
			setTitle(nev);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		oarcbor = true;
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
			if (requestCode == 4) {
				Bundle MBuddle = data.getExtras();
				nev = MBuddle.getString("nev");
				fejlec = "";
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
