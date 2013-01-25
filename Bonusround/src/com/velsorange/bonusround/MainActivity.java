package com.velsorange.bonusround;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
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
	private static String nev = "";
	private static String admin = "";
	private static String vetelezes="";
	private static String standolas="";
	private static String koltseg="";
	private static String bevetel="";
	private static String informaciok="";
	public static DbAdapter dbHelper;
	public static String fejlec = "Üdvözletem:)";
	public String[] values;
	public boolean oarcbor;
	public static final int TABLET_MIN_DP_WEIGHT = 450;

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
			// kívûrõl érkezünk
			nev = "Nincs bejelentkezve";
			admin = "false";
			fejlec = "Üdvözletem:)";
			vetelezes="false";
			standolas="false";
			koltseg="false";
			bevetel="false";
			informaciok="false";
			
		} else {
		}
		oarcbor = false;
		menu();
		Log.d("menu futása", "onResume-ben után");
		dbHelper.fetchFoMenu(dbHelper.getUserId(nev));
		
		
/*		if (admin.compareTo("true") == 0) {
			values = new String[] { "Bónuszkör", "Vételezés", "Standolás",
					"Hitel", "Költség", "Bevétel", "Információk", 
					"Karbantartás",	"Kijelentkezés", "Kilépés" };
		} else if (nev == "Nincs bejelentkezve") {
			values = new String[] { "Bejelenkezés", "Kilépés" };

		} else if (nev == "Nincs felhasználó") {
			values = new String[] { "Új felhasználó", "Kilépés" };

		} else if (admin.compareTo("false") == 0) {
			String s="\"Bónuszkör\"";
			int db=3;
			if (vetelezes=="true"){
				db++;
			}
			if (standolas=="true"){
				db++;
			}
			db++;
			if (koltseg=="true"){
				db++;
			}
			if (bevetel=="true"){
				db++;
			}
			if (informaciok=="true"){
				db++;
			}
			values = new String[db];
			values[0]="Bónuszkör";
			values[db-1]="Kilépés";
			values[db-2]="Kijelentkezés";
			db=0;
			if (vetelezes=="true"){
				db++;
				values[db]="Vételezés";
			}
			if (standolas=="true"){
				db++;
				values[db]="Standolás";
			}
			db++;
			values[db]="Hitel";
			if (koltseg=="true"){
				db++;
				values[db]="Költség";
			}
			if (bevetel=="true"){
				db++;
				values[db]="Bevétel";
			}
			if (informaciok=="true"){
				db++;
				values[db]="Információk";
			}
			
		values = new String[] { "Bónuszkör", "Vételezés", "Standolás",
					"Hitel", "Költség", "Kijelentkezés", "Kilépés" };
		}*/

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
			if (nev == "Nincs felhasználó") {
				Intent intent = new Intent(getBaseContext(),
						UjFelhasznalo.class);
				startActivityForResult(intent, 2);
			} else if (nev == "Nincs bejelentkezve") {
				Intent intent = new Intent(getBaseContext(),
						LoginActivity.class);
				startActivityForResult(intent, 3);
			} else if (values[0].toString().compareTo("Bónuszkör") == 0) {
				//bónuszkör
			}
			break;
		case 1:
			if (values[1].toString().compareTo("Vételezés") == 0) {
			
			}else if (values[1].toString().compareTo("Standolás") == 0) {
				
			} else if (values[1].toString().compareTo("Hitel") == 0) {
					
			}
			break;
		case 2:
			if (values[2].toString().compareTo("Standolás") == 0) {
			}
			break;
		case 3:
			if (values[3].toString().compareTo("Hitel") == 0) {
			}
			break;
		case 4:
			if (values[4].toString().compareTo("Költség") == 0) {
			}
			break;
		case 5:
			if (values[5].toString().compareTo("Bevétel") == 0) {
			} else {
				Intent intent = new Intent(getBaseContext(),
						LoginActivity.class);
				startActivityForResult(intent, 3);
				Toast.makeText(this, "Sikeres kijelentkezés!",
						Toast.LENGTH_LONG).show();
				}
			break;
		case 6:
			if (values[5].toString().compareTo("Információk") == 0) {
			} else {

				finish();
				Toast.makeText(this, "Sikeres kilépés!", Toast.LENGTH_LONG)
						.show();
			}
			break;
		case 7:
			if (values[6].toString().compareTo("Karbantartás") == 0) {
				if (isSmartphoneOrTablet(this)) {
				
					//teló
				} else{
					//tabi
				}
			} else {
			}
			break;
		case 8:
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
			Toast.makeText(this, "Sikeres kijelentkezés!", Toast.LENGTH_LONG)
					.show();

			break;
		case 9:
			finish();
			Toast.makeText(this, "Sikeres kilépés!", Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void menu() {
		dbHelper = new DbAdapter(this);
		dbHelper.open();
		if ((dbHelper.osszFelhasznalok().getCount() > 0)
				&& (fejlec.compareTo("Üdvözletem:)") == 0)
				&& (nev.isEmpty() || nev.compareTo("Nincs bejelentkezve") == 0)) {
			this.setTitle("Nincs bejelentkezve");
			Intent intent = new Intent(getBaseContext(), LoginActivity.class);
			startActivityForResult(intent, 3);
		} else if ((dbHelper.osszFelhasznalok().getCount() == 0)) {
			Log.d("mainactivity", "újfelhasznaló.class indít");
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
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
