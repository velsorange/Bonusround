package com.velsorange.bonusround;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

@SuppressLint("SimpleDateFormat")
public class DbAdapter {
	public static final String DATABASE_NAME = "bonusround";
	public static final int DATABASE_VERSION = 1;
	// ************Áru tipusok******************//
	public static final String TABLA_ARUT = "aru_tipus";
	public static final String ARUT_KEY_ROWID = "_id";
	public static final String ARUT_KEY_MEGNEVEZES = "megnevezes";
	public static final String ARUT_KEY_LATHATO = "lathato";
	public static final String ARUT_KEY_KISZERELES = "kiszereles";
	public static final String ARUT_KEY_USER_ID = "user_id";
	public static final String ARUT_KEY_SORREND = "sorrend";
	public static final String ARUT_WHERE_LATHATO_TRUE= ARUT_KEY_LATHATO + " like 'igen'";
	public static final String ARUT_WHERE_LATHATO_FALSE= ARUT_KEY_LATHATO + " like 'nem'";
	// ******************Áru tábla konstansok ********************//
	public static final String TABLA_ARU = "aru";
	public static final String ARU_KEY_ROWID = "_id";
	public static final String ARU_KEY_ARUT_ID = "arut_id";
	public static final String ARU_KEY_MEGNEVEZES = "megnevezes";
	public static final String ARU_KEY_LATHATO = "lathato";
	public static final String ARU_KEY_USER_ID = "user_id";
	// ******************Eladási ár tábla*************************//
	public static final String TABLA_ELADASIAR = "eladasiar";
	public static final String ELADASIAR_KEY_ROWID = "_id";
	public static final String ELADASIAR_KEY_ARU_ID = "aru_id";
	public static final String ELADASIAR_KEY_NE = "ne";
	public static final String ELADASIAR_KEY_BR = "br";
	public static final String ELADASIAR_KEY_AKTUALIS = "aktualis";
	public static final String ELADASIAR_KEY_TOL = "tol";
	public static final String ELADASIAR_KEY_USER_ID = "user_id";

	// *****************Beszerzési ár tábla *******************//
	public static final String TABLA_BESZERZESIAR = "beszerzesiar";
	public static final String BESZERZESIAR_KEY_ROWID = "_id";
	public static final String BESZERZESIAR_KEY_ARU_ID = "aru_id";
	public static final String BESZERZESIAR_KEY_NE = "ne";
	public static final String BESZERZESIAR_KEY_BR = "br";
	public static final String BESZERZESIAR_KEY_AKTUALIS = "aktualis";
	public static final String BESZERZESIAR_KEY_TOL = "tol";
	public static final String BESZERZESIAR_KEY_SZALLITO = "szallito_id";
	public static final String BESZERZESIAR_KEY_USER_ID = "user_id";
	// ******************Vételezés tábla************************//
	public static final String TABLA_VETELEZES = "vetelezes";
	public static final String VETELEZES_KEY_ROWID = "_id";//crypt
	public static final String VETELEZES_KEY_BEAR_ID = "bear_id";//crypt
	public static final String VETELEZES_KEY_DATUM = "datum";//crypt
	public static final String VETELEZES_KEY_SZAMLA_ID = "szamla_id";//crypt
	public static final String VETELEZES_KEY_MENNYISEG = "mennyiseg";//crypt
	public static final String VETELEZES_KEY_USER_ID = "user_id";//crypt
	public static final String VETELEZES_KEY_SS_ID = "ss_id";//crypt
	// ******************Szamla tabla***************************//
	public static final String TABLA_SZAMLA = "szamla";
	public static final String SZAMLA_KEY_ROWID = "_id";//crypt
	public static final String SZAMLA_KEY_SZALLITO_ID = "szallito_id";//crypt
	public static final String SZAMLA_KEY_DATUM = "datum";//crypt
	public static final String SZAMLA_KEY_M = "m";//crypt
	public static final String SZAMLA_KEY_NETTO_OSSZEG = "netto_osszeg";//crypt
	public static final String SZAMLA_KEY_BRUTTO_OSSZEG = "brutto_osszeg";//crypt
	public static final String SZAMLA_KEY_USER_ID = "user_id";//crypt
	// ******************Szallito tábla*************************//
	public static final String TABLA_SZALLITO = "szallito";
	public static final String SZALLITO_KEY_ROWID = "_id";
	public static final String SZALLITO_KEY_MEGNEVEZES = "megnevezes";
	public static final String SZALLITO_KEY_NEV = "nev";
	public static final String SZALLITO_KEY_CIM = "cim";
	public static final String SZALLITO_KEY_TEL1 = "tel1";
	public static final String SZALLITO_KEY_TEL2 = "tel2";
	public static final String SZALLITO_KEY_TEL3 = "tel3";
	public static final String SZALLITO_KEY_FAX = "fax";
	public static final String SZALLITO_KEY_EMAIL = "email";
	public static final String SZALLITO_KEY_WEB = "web";
	public static final String SZALLITO_KEY_USER_ID = "user_id";
	// ******************Áru eladás tábla*************************//
	public static final String TABLA_AE = "ae";
	public static final String AE_KEY_ROWID = "_id";
	public static final String AE_KEY_ARU_ID = "aru_id";
	public static final String AE_KEY_ELADASI_MENNYISEG = "em";
	public static final String AE_KEY_B = "brutto";
	public static final String AE_KEY_N = "netto";
	public static final String AE_KEY_AKTUALIS = "aktuális";
	public static final String AE_KEY_TOL = "date";
	public static final String AE_KEY_USER_ID = "user_id";
	// ******************Eladások tábla*****************************//
	public static final String TABLA_ELADASOK = "eladasok";
	public static final String ELADASOK_KEY_ROWID = "_id";
	public static final String ELADASOK_KEY_AE_ID = "ae_id";
	public static final String ELADASOK_KEY_MENNYISEG = "mennyiseg";
	public static final String ELADASOK_KEY_B = "brutto";
	public static final String ELADASOK_KEY_N = "netto";
	public static final String ELADASOK_KEY_GRATIS = "gratis";
	public static final String ELADASOK_KEY_ELADAS_ID = "eladas_id";
	public static final String ELADASOK_KEY_HITEL = "hitel";
	public static final String ELADASOK_KEY_HITEL_OSSZEG = "hitel_osszeg";
	public static final String ELADASOK_KEY_U = "u";
	public static final String ELADASOK_KEY_USER_ID = "user_id";
	public static final String ELADASOK_KEY_ARUKIADAS = "arukiadas";
	public static final String ELADASOK_KEY_SS_ID = "ss_id";
	public static final String ELADASOK_KEY_KINEK_USER_ID = "kinek_user_id";
	// ******************Eladás tábla*********************************//
	public static final String TABLA_ELADAS = "eladas";
	public static final String ELADAS_KEY_ROWID = "_id";
	public static final String ELADAS_KEY_DATUM = "datum";
	public static final String ELADAS_KEY_LEZARVA = "lezarva";
	public static final String ELADAS_KEY_VENDEG_ID = "vendeg_id";
	public static final String ELADAS_KEY_USER_ID = "user_id";

	// ******************Hitel tábla*********************************//
	// *újraírva, nincs a dbadapterben átírva
	public static final String TABLA_HITEL = "hitel";
	public static final String HITEL_KEY_ROWID = "_id";
	public static final String HITEL_KEY_DATUM = "datum";
	public static final String HITEL_KEY_OSSZEG = "osszeg";
	public static final String HITEL_KEY_VENDEG_ID = "vendeg_id";
	public static final String HITEL_KEY_KIAD_USER_ID = "kiad_user_id";
	public static final String HITEL_KEY_FIZET_USER_ID = "user_id";
	public static final String HITEL_KEY_FIZETVE = "fizetve";
	// ******************Vendég tábla********************************//
	public static final String TABLA_VENDEG = "vendeg";
	public static final String VENDEG_KEY_ROWID = "_id";
	public static final String VENDEG_KEY_NEV = "nev";
	public static final String VENDEG_KEY_CIM = "cim";
	public static final String VENDEG_KEY_TEL = "tel";
	public static final String VENDEG_KEY_EMAIL = "email";
	public static final String VENDEG_KEY_USER_ID = "user_id";
	// ******************Ktg tábla*************************************//
	public static final String TABLA_KTG = "ktg";
	public static final String KTG_KEY_ROWID = "_id";
	public static final String KTG_KEY_MEGNEVEZES = "megnevezes";
	public static final String KTG_KEY_KTGTIPUS_ID = "tipus_id";
	public static final String KTG_KEY_NE = "netto";
	public static final String KTG_KEY_BR = "brutto";
	public static final String KTG_KEY_DATUM = "datum";
	public static final String KTG_KEY_TAG = "tag";
	public static final String KTG_KEY_USER_ID = "user_id";
	// ******************Ktg tipus tábla*******************************//
	public static final String TABLA_KTG_T = "ktg_t";
	public static final String KTG_T_KEY_ROWID = "_id";
	public static final String KTG_T_KEY_MEGNEVEZES = "megnevezes";
	public static final String KTG_T_KEY_TIPUS = "tipus";
	public static final String KTG_T_KEY_KAPCS_ID = "kapcs_id";//???
	public static final String KTG_T_KEY_NE = "netto";
	public static final String KTG_T_KEY_BR = "brutto";
	public static final String KTG_T_KEY_TAG = "tag";//számolós?
	public static final String KTG_T_KEY_USER_ID = "user_id";
	// ******************Felhasználó tábla*******************************//
	public static final String TABLA_FELHASZNALO = "felhasznalo";
	public static final String FELHASZNALO_KEY_ROWID = "_id";
	public static final String FELHASZNALO_KEY_NEV = "nev";
	public static final String FELHASZNALO_KEY_P = "p";
	public static final String FELHASZNALO_KEY_ADMIN = "admin";
	public static final String FELHASZNALO_KEY_USER_ID = "user_id";
	public static final String FELHASZNALO_KEY_VETELEZES = "vetelezes";
	public static final String FELHASZNALO_KEY_STANDOLAS = "standolas";
	public static final String FELHASZNALO_KEY_KOLTSEG = "koltseg";
	public static final String FELHASZNALO_KEY_BEVETEL = "bevetel";
	public static final String FELHASZNALO_KEY_INFORMACIOK = "információk";
	// public static final String FELHASZNALO_WHERE = MENUK_KEY_TIPUS
	// + " like 'fomenu' AND " + MENUK_KEY_LATHATO + " like 'igen'";
	// ******************Standsor tábla*******************************//
	public static final String TABLA_SS = "ss";
	public static final String SS_KEY_ROWID = "_id";
	public static final String SS_KEY_ARU_ID = "aru_id";
	public static final String SS_KEY_NYITOKESZLET = "nyitokeszlet";
	public static final String SS_KEY_S_ID = "s_id";
	public static final String SS_KEY_OSSZESEN = "osszesen";
	public static final String SS_KEY_FOGYAS = "fogyas";
	public static final String SS_KEY_NE = "netto";
	public static final String SS_KEY_BR = "brutto";
	// ******************Stand tábla*******************************//
	public static final String TABLA_S = "s";
	public static final String S_KEY_ROWID = "_id";
	public static final String S_KEY_DATUM = "datum";
	public static final String S_KEY_USER_ID = "user_id";

	public static final String S_KEY_NE = "netto";
	public static final String S_KEY_BR = "brutto";
	// ******************Stand AKIÉ Felhasználó
	// tábla*******************************//
	public static final String TABLA_SF = "sf";
	public static final String SF_KEY_ROWID = "_id";
	public static final String SF_KEY_S_ID = "s_id";
	public static final String SF_KEY_USER_ID = "user_id";

	// ******************Maradvány tábla*******************************//
	public static final String TABLA_MARADVANY = "maradvany";
	public static final String MARADVANY_KEY_ROWID = "_id";
	public static final String MARADVANY_KEY_ARU_ID = "aru_id";
	public static final String MARADVANY_KEY_SS_ID = "ss_id";
	public static final String MARADVANY_KEY_MENNYISEG = "mennyiseg";
	// ******************Egyéb Bevétel tábla nincs kész tip id-t bele kell tenni*******************************//
	public static final String TABLA_EGYEB_BEVETEL = "egyeb_bevetel";
	public static final String EGYEB_BEVETEL_KEY_ROWID = "_id";
	public static final String EGYEB_BEVETEL_KEY_MEGNEVEZES = "megnevezes";
	public static final String EGYEB_BEVETEL_KEY_NE = "ne";
	public static final String EGYEB_BEVETEL_KEY_BR = "br";
	public static final String EGYEB_BEVETEL_KEY_DATUM = "datum";
	public static final String EGYEB_BEVETEL_KEY_USER_ID = "user_id";
	public static final String EGYEB_BEVETEL_KEY_SZAMOL = "szamol";
	// ******************Egyéb Bevétel  tips tábla nincs kész semmije*******************************//
		public static final String TABLA_EGYEB_BEVETEL_T = "egyeb_bevetel_t";
		public static final String EGYEB_BEVETEL_T_KEY_ROWID = "_id";
		
	// ******************Menü tábla*******************************//
	public static final String TABLA_MENU = "menu";
	public static final String MENU_KEY_ROWID = "_id";
	public static final String MENU_KEY_MENUT = "menut";
	public static final String MENU_KEY_MENU = "menu";
	public static final String MENU_KEY_ERTEK = "ertek";
	public static final String MENU_KEY_USER_ID = "user_id";

	public static final String FOMENU_WHERE = MENU_KEY_MENUT
			+ " like 'fomenu' AND " + MENU_KEY_USER_ID + " like '";

	public static final String TAG = "DbaAdapter";
	private DatabaseHelper mDbHelp;
	private SQLiteDatabase mDb;

	private Context mctx;

	public static final String DATABASE_CREATE_ARUT = "CREATE TABLE if not exists "
			+ TABLA_ARUT
			+ " ("
			+ ARUT_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ ARUT_KEY_MEGNEVEZES
			+ " text, "
			+ ARUT_KEY_LATHATO
			+ " text, "
			+ ARUT_KEY_KISZERELES
			+ " text, " + ARUT_KEY_USER_ID + " integer, "
			+ ARUT_KEY_SORREND + " integer);";
	public static final String DATABASE_CREATE_ARU = "CREATE TABLE if not exists "
			+ TABLA_ARU
			+ " ("
			+ ARU_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ ARU_KEY_ARUT_ID
			+ " integer, "
			+ ARU_KEY_MEGNEVEZES
			+ " text, "
			+ ARU_KEY_LATHATO
			+ " text, " + ARU_KEY_USER_ID + " integer);";
	public static final String DATABASE_CREATE_BESZERZESIAR = "CREATE TABLE if not exists "
			+ TABLA_BESZERZESIAR
			+ " ("
			+ BESZERZESIAR_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ BESZERZESIAR_KEY_ARU_ID
			+ " integer, "
			+ BESZERZESIAR_KEY_NE
			+ " text, "
			+ BESZERZESIAR_KEY_BR
			+ " text, "
			+ BESZERZESIAR_KEY_AKTUALIS
			+ " text, "
			+ BESZERZESIAR_KEY_TOL
			+ " datetime, "
			+ BESZERZESIAR_KEY_SZALLITO
			+ "integer, "
			+ BESZERZESIAR_KEY_USER_ID + " integer);";
	public static final String DATABASE_CREATE_ELADASIAR = "CREATE TABLE if not exists "
			+ TABLA_ELADASIAR
			+ " ("
			+ ELADASIAR_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ ELADASIAR_KEY_ARU_ID
			+ " integer, "
			+ ELADASIAR_KEY_NE
			+ " text, "
			+ ELADASIAR_KEY_BR
			+ " text, "
			+ ELADASIAR_KEY_AKTUALIS
			+ " text, "
			+ ELADASIAR_KEY_TOL
			+ " datetime, "
			+ ELADASIAR_KEY_USER_ID
			+ " integer);";
	public static final String DATABASE_CREATE_VETELEZES = "CREATE TABLE if not exists "
			+ TABLA_VETELEZES
			+ " ("
			+ VETELEZES_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ VETELEZES_KEY_BEAR_ID
			+ " integer, "
			+ VETELEZES_KEY_DATUM
			+ " datetime, "
			+ VETELEZES_KEY_SZAMLA_ID
			+ " integer, "
			+ VETELEZES_KEY_MENNYISEG
			+ " text, "
			+ VETELEZES_KEY_USER_ID
			+ " integer, "
			+ VETELEZES_KEY_SS_ID + " integer);";
	public static final String DATABASE_CREATE_SZAMLA = "CREATE TABLE if not exists "
			+ TABLA_SZAMLA
			+ " ("
			+ SZAMLA_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ SZAMLA_KEY_SZALLITO_ID
			+ " integer, "
			+ SZAMLA_KEY_DATUM
			+ " datetime, "
			+ SZAMLA_KEY_M
			+ " text, "
			+ SZAMLA_KEY_BRUTTO_OSSZEG
			+ " text, "
			+ SZAMLA_KEY_NETTO_OSSZEG
			+ " text, "
			+ SZAMLA_KEY_USER_ID
			+ " integer);";
	public static final String DATABASE_CREATE_SZALLITO = "CREATE TABLE if not exists "
			+ TABLA_SZALLITO
			+ " ("
			+ SZALLITO_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ SZALLITO_KEY_MEGNEVEZES
			+ " text, "
			+ SZALLITO_KEY_NEV
			+ " text, "
			+ SZALLITO_KEY_CIM
			+ " text, "
			+ SZALLITO_KEY_TEL1
			+ " text, "
			+ SZALLITO_KEY_TEL2
			+ " text, "
			+ SZALLITO_KEY_TEL3
			+ " text, "
			+ SZALLITO_KEY_FAX
			+ " text, "
			+ SZALLITO_KEY_EMAIL
			+ " text, "
			+ SZALLITO_KEY_WEB
			+ " text, " + SZALLITO_KEY_USER_ID + " integer);";
	public static final String DATABASE_CREATE_AE = "CREATE TABLE if not exists "
			+ TABLA_AE
			+ " ("
			+ AE_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ AE_KEY_ARU_ID
			+ " integer, "
			+ AE_KEY_ELADASI_MENNYISEG
			+ " text, "
			+ AE_KEY_B
			+ " text, "
			+ AE_KEY_N
			+ " text, "
			+ AE_KEY_AKTUALIS
			+ " text, "
			+ AE_KEY_TOL + " datetime, " + AE_KEY_USER_ID + " integer);";
	public static final String DATABASE_CREATE_ELADASOK = "CREATE TABLE if not exists "
			+ TABLA_ELADASOK
			+ " ("
			+ ELADASOK_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ ELADASOK_KEY_AE_ID
			+ " integer, "
			+ ELADASOK_KEY_MENNYISEG
			+ " text, "
			+ ELADASOK_KEY_B
			+ " text, "
			+ ELADASOK_KEY_N
			+ " text, "
			+ ELADASOK_KEY_GRATIS
			+ " text, "
			+ ELADASOK_KEY_ELADAS_ID
			+ " integer, "
			+ ELADASOK_KEY_HITEL
			+ " text, "
			+ ELADASOK_KEY_HITEL_OSSZEG
			+ " text, "
			+ ELADASOK_KEY_U
			+ " text, "
			+ ELADASOK_KEY_USER_ID
			+ " integer, "
			+ ELADASOK_KEY_ARUKIADAS
			+ " text, "
			+ ELADASOK_KEY_SS_ID
			+ " integer, " + ELADASOK_KEY_KINEK_USER_ID + " integer);";
	public static final String DATABASE_CREATE_ELADAS = "CREATE TABLE if not exists "
			+ TABLA_ELADAS
			+ " ("
			+ ELADAS_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ ELADAS_KEY_DATUM
			+ " datetime, "
			+ ELADAS_KEY_LEZARVA
			+ " text, "
			+ ELADAS_KEY_VENDEG_ID
			+ " integer, "
			+ ELADAS_KEY_USER_ID
			+ " integer);";
	public static final String DATABASE_CREATE_HITEL = "CREATE TABLE if not exists "
			+ TABLA_HITEL
			+ " ("
			+ HITEL_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ HITEL_KEY_DATUM
			+ " datetime, "
			+ HITEL_KEY_OSSZEG
			+ " text, "
			+ HITEL_KEY_VENDEG_ID
			+ " integer, "
			+ HITEL_KEY_KIAD_USER_ID
			+ " integer, "
			+ HITEL_KEY_FIZET_USER_ID
			+ " integer, "
			+ HITEL_KEY_FIZETVE + " text);";
	public static final String DATABASE_CREATE_VENDEG = "CREATE TABLE if not exists "
			+ TABLA_VENDEG
			+ " ("
			+ VENDEG_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ VENDEG_KEY_NEV
			+ " text, "
			+ VENDEG_KEY_CIM
			+ " text, "
			+ VENDEG_KEY_TEL
			+ " text, "
			+ VENDEG_KEY_EMAIL
			+ " text, "
			+ VENDEG_KEY_USER_ID
			+ " integer);";
	public static final String DATABASE_CREATE_KTG = "CREATE TABLE if not exists "
			+ TABLA_KTG
			+ " ("
			+ KTG_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ KTG_KEY_MEGNEVEZES
			+ " text, "
			+ KTG_KEY_KTGTIPUS_ID
			+ " integer, "
			+ KTG_KEY_NE
			+ " text, "
			+ KTG_KEY_BR
			+ " text, "
			+ KTG_KEY_DATUM
			+ " datetime, "
			+ KTG_KEY_TAG
			+ " text, "
			+ KTG_KEY_USER_ID
			+ " integer);";
	public static final String DATABASE_CREATE_KTG_T = "CREATE TABLE if not exists "
			+ TABLA_KTG_T
			+ " ("
			+ KTG_T_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ KTG_T_KEY_MEGNEVEZES
			+ " text, "
			+ KTG_T_KEY_TIPUS
			+ " text, "
			+ KTG_T_KEY_KAPCS_ID
			+ " integer, "
			+ KTG_T_KEY_NE
			+ " text, "
			+ KTG_T_KEY_BR
			+ " text, "
			+ KTG_T_KEY_TAG
			+ " text, "
			+ KTG_T_KEY_USER_ID
			+ " integer);";
	public static final String DATABASE_CREATE_FELHASZNALO = "CREATE TABLE if not exists "
			+ TABLA_FELHASZNALO
			+ " ("
			+ FELHASZNALO_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ FELHASZNALO_KEY_NEV
			+ " text, "
			+ FELHASZNALO_KEY_P
			+ " text, "
			+ FELHASZNALO_KEY_ADMIN
			+ " text, "
			+ FELHASZNALO_KEY_USER_ID
			+ " integer, "
			+ FELHASZNALO_KEY_VETELEZES
			+ " text, "
			+ FELHASZNALO_KEY_STANDOLAS
			+ " text, "
			+ FELHASZNALO_KEY_KOLTSEG
			+ " text, "
			+ FELHASZNALO_KEY_BEVETEL
			+ " text, "
			+ FELHASZNALO_KEY_INFORMACIOK
			+ " text);";
	public static final String DATABASE_CREATE_SS = "CREATE TABLE if not exists "
			+ TABLA_SS
			+ " ("
			+ SS_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ SS_KEY_ARU_ID
			+ " integer, "
			+ SS_KEY_NYITOKESZLET
			+ " text, "
			+ SS_KEY_S_ID
			+ " integer, "
			+ SS_KEY_OSSZESEN
			+ " text, "
			+ SS_KEY_FOGYAS
			+ " text, " + SS_KEY_NE + " text, " + SS_KEY_BR + " text);";
	public static final String DATABASE_CREATE_S = "CREATE TABLE if not exists "
			+ TABLA_S
			+ " ("
			+ S_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ S_KEY_DATUM
			+ " datetime, "
			+ S_KEY_USER_ID
			+ " integer, "
			+ S_KEY_NE
			+ " text, " + S_KEY_BR + " text);";
	public static final String DATABASE_CREATE_SF = "CREATE TABLE if not exists "
			+ TABLA_SF
			+ " ("
			+ S_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ SF_KEY_S_ID
			+ " integer, " + SF_KEY_USER_ID + " integer);";
	public static final String DATABASE_CREATE_MARADVANY = "CREATE TABLE if not exists "
			+ TABLA_MARADVANY
			+ " ("
			+ MARADVANY_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ MARADVANY_KEY_ARU_ID
			+ " integer, "
			+ MARADVANY_KEY_SS_ID
			+ " integer, "
			+ MARADVANY_KEY_MENNYISEG + " text);";
	public static final String DATABASE_CREATE_EGYEB_BEVETEL = "CREATE TABLE if not exists "
			+ TABLA_EGYEB_BEVETEL
			+ " ("
			+ EGYEB_BEVETEL_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ EGYEB_BEVETEL_KEY_MEGNEVEZES
			+ " text, "
			+ EGYEB_BEVETEL_KEY_NE
			+ " text, "
			+ EGYEB_BEVETEL_KEY_BR
			+ " text, "
			+ EGYEB_BEVETEL_KEY_DATUM
			+ " datetime, "
			+ EGYEB_BEVETEL_KEY_USER_ID
			+ " integer, "
			+ EGYEB_BEVETEL_KEY_SZAMOL + " text);";
	public static final String DATABASE_CREATE_MENU = "CREATE TABLE if not exists "
			+ TABLA_MENU
			+ " ("
			+ MENU_KEY_ROWID
			+ " integer PRIMARY KEY autoincrement, "
			+ MENU_KEY_MENUT
			+ " text, "
			+ MENU_KEY_MENU
			+ " text, "
			+ MENU_KEY_ERTEK
			+ " integer, " + MENU_KEY_USER_ID + " integer);";

	private static final String TABLE_DELETE = "DROP TABLE IF EXITS ";

	private static class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_ARUT);
			db.execSQL(DATABASE_CREATE_ARU);
			db.execSQL(DATABASE_CREATE_ELADASIAR);
			db.execSQL(DATABASE_CREATE_BESZERZESIAR);
			db.execSQL(DATABASE_CREATE_VETELEZES);
			db.execSQL(DATABASE_CREATE_SZAMLA);
			db.execSQL(DATABASE_CREATE_SZALLITO);
			db.execSQL(DATABASE_CREATE_AE);
			db.execSQL(DATABASE_CREATE_ELADASOK);
			db.execSQL(DATABASE_CREATE_ELADAS);
			db.execSQL(DATABASE_CREATE_HITEL);
			db.execSQL(DATABASE_CREATE_VENDEG);
			db.execSQL(DATABASE_CREATE_KTG);
			db.execSQL(DATABASE_CREATE_KTG_T);
			db.execSQL(DATABASE_CREATE_FELHASZNALO);
			db.execSQL(DATABASE_CREATE_SS);
			db.execSQL(DATABASE_CREATE_S);
			db.execSQL(DATABASE_CREATE_SF);
			db.execSQL(DATABASE_CREATE_MARADVANY);
			db.execSQL(DATABASE_CREATE_EGYEB_BEVETEL);
			db.execSQL(DATABASE_CREATE_MENU);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL(TABLE_DELETE + TABLA_ARUT);
			db.execSQL(TABLE_DELETE + TABLA_ARU);
			db.execSQL(TABLE_DELETE + TABLA_ELADASIAR);
			db.execSQL(TABLE_DELETE + TABLA_BESZERZESIAR);
			db.execSQL(TABLE_DELETE + TABLA_VETELEZES);
			db.execSQL(TABLE_DELETE + TABLA_SZAMLA);
			db.execSQL(TABLE_DELETE + TABLA_SZALLITO);
			db.execSQL(TABLE_DELETE + TABLA_AE);
			db.execSQL(TABLE_DELETE + TABLA_ELADASOK);
			db.execSQL(TABLE_DELETE + TABLA_ELADAS);
			db.execSQL(TABLE_DELETE + TABLA_HITEL);
			db.execSQL(TABLE_DELETE + TABLA_VENDEG);
			db.execSQL(TABLE_DELETE + TABLA_KTG);
			db.execSQL(TABLE_DELETE + TABLA_KTG_T);
			db.execSQL(TABLE_DELETE + TABLA_FELHASZNALO);
			db.execSQL(TABLE_DELETE + TABLA_SS);
			db.execSQL(TABLE_DELETE + TABLA_S);
			db.execSQL(TABLE_DELETE + TABLA_SF);
			db.execSQL(TABLE_DELETE + TABLA_MARADVANY);
			db.execSQL(TABLE_DELETE + TABLA_EGYEB_BEVETEL);
			db.execSQL(TABLE_DELETE + TABLA_MENU);
			onCreate(db);
		}
	}

	DbAdapter(Context ctx) {
		this.mctx = ctx;
	}

	public DbAdapter open() throws SQLException {
		mDbHelp = new DatabaseHelper(mctx);
		mDb = mDbHelp.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelp != null) {
			mDbHelp.close();
		}
	}

	public long createArut(String megnevezes, String lathato,
			String kiszereles, int user_id, int sorrend) {
		ContentValues ertek = new ContentValues();
		ertek.put(ARUT_KEY_MEGNEVEZES, megnevezes);
		ertek.put(ARUT_KEY_LATHATO, lathato);
		ertek.put(ARUT_KEY_KISZERELES, kiszereles);
		ertek.put(ARUT_KEY_USER_ID, user_id);
		ertek.put(ARUT_KEY_SORREND, sorrend);
		return mDb.insert(TABLA_ARUT, null, ertek);
	}

	public long createAru(int arut_id, String megnevezes, String lathato,
			int user_id) {
		ContentValues ertek = new ContentValues();
		ertek.put(ARU_KEY_ARUT_ID, arut_id);
		ertek.put(ARU_KEY_MEGNEVEZES, megnevezes);
		ertek.put(ARU_KEY_LATHATO, lathato);
		ertek.put(ARU_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_ARU, null, ertek);
	}

	public long createEladasiar(int aru_id, String ne, String br,
			String aktualis, Date datum, int user_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(ELADASIAR_KEY_ARU_ID, aru_id);
		ertek.put(ELADASIAR_KEY_NE, ne);
		ertek.put(ELADASIAR_KEY_BR, br);
		ertek.put(ELADASIAR_KEY_AKTUALIS, aktualis);
		ertek.put(ELADASIAR_KEY_TOL, dateFormat.format(datum));
		ertek.put(ELADASIAR_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_ELADASIAR, null, ertek);
	}

	public long createBeszerzesiar(int aru_id, String ne, String br,
			String aktualis, Date datum, int szallito_id, int user_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(BESZERZESIAR_KEY_ARU_ID, aru_id);
		ertek.put(BESZERZESIAR_KEY_NE, ne);
		ertek.put(BESZERZESIAR_KEY_BR, br);
		ertek.put(BESZERZESIAR_KEY_AKTUALIS, aktualis);
		ertek.put(BESZERZESIAR_KEY_TOL, dateFormat.format(datum));
		ertek.put(BESZERZESIAR_KEY_SZALLITO, szallito_id);
		ertek.put(BESZERZESIAR_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_BESZERZESIAR, null, ertek);
	}

	public long createVetelezes(int bear_id, Date datum, int szamla_id,
			String mennyiseg, int user_id, int ss_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(VETELEZES_KEY_BEAR_ID, bear_id);
		ertek.put(VETELEZES_KEY_DATUM, dateFormat.format(datum));
		ertek.put(VETELEZES_KEY_SZAMLA_ID, szamla_id);
		ertek.put(VETELEZES_KEY_MENNYISEG, mennyiseg);
		ertek.put(VETELEZES_KEY_USER_ID, user_id);
		ertek.put(VETELEZES_KEY_SS_ID, ss_id);
		return mDb.insert(TABLA_VETELEZES, null, ertek);
	}

	public long createSzamla(int szallito_id, Date datum, String m,
			String brutto_osszeg, String netto_osszeg, int user_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(SZAMLA_KEY_SZALLITO_ID, szallito_id);
		ertek.put(SZAMLA_KEY_DATUM, dateFormat.format(datum));
		ertek.put(SZAMLA_KEY_M, m);
		ertek.put(SZAMLA_KEY_BRUTTO_OSSZEG, brutto_osszeg);
		ertek.put(SZAMLA_KEY_NETTO_OSSZEG, netto_osszeg);
		ertek.put(SZAMLA_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_SZAMLA, null, ertek);
	}

	public long createSzallito(String megnevezes, String nev, String cim,
			String tel1, String tel2, String tel3, String fax, String email,
			String web, int user_id) {

		ContentValues ertek = new ContentValues();
		ertek.put(SZALLITO_KEY_MEGNEVEZES, megnevezes);
		ertek.put(SZALLITO_KEY_NEV, nev);
		ertek.put(SZALLITO_KEY_CIM, cim);
		ertek.put(SZALLITO_KEY_TEL1, tel1);
		ertek.put(SZALLITO_KEY_TEL2, tel2);
		ertek.put(SZALLITO_KEY_TEL3, tel3);
		ertek.put(SZALLITO_KEY_FAX, fax);
		ertek.put(SZALLITO_KEY_EMAIL, email);
		ertek.put(SZALLITO_KEY_WEB, web);
		ertek.put(SZALLITO_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_SZALLITO, null, ertek);
	}

	public long createAe(int aru_id, String m, String brutto, String netto,
			String aktualis, Date datum, int user_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(AE_KEY_ARU_ID, aru_id);
		ertek.put(AE_KEY_ELADASI_MENNYISEG, m);
		ertek.put(AE_KEY_B, brutto);
		ertek.put(AE_KEY_N, netto);
		ertek.put(AE_KEY_AKTUALIS, aktualis);
		ertek.put(AE_KEY_TOL, dateFormat.format(datum));
		ertek.put(AE_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_AE, null, ertek);
	}

	public long createEladasok(int ae_id, String m, String brutto,
			String netto, String gratis, int eladas_id, String hitel,
			String hitelo, String u, int user_id, String arukiadas, int ss_id,
			int kinek_user_id) {

		ContentValues ertek = new ContentValues();
		ertek.put(ELADASOK_KEY_AE_ID, ae_id);
		ertek.put(ELADASOK_KEY_MENNYISEG, m);
		ertek.put(ELADASOK_KEY_B, brutto);
		ertek.put(ELADASOK_KEY_N, netto);
		ertek.put(ELADASOK_KEY_GRATIS, gratis);
		ertek.put(ELADASOK_KEY_ELADAS_ID, eladas_id);
		ertek.put(ELADASOK_KEY_HITEL, hitel);
		ertek.put(ELADASOK_KEY_HITEL_OSSZEG, hitelo);
		ertek.put(ELADASOK_KEY_U, u);
		ertek.put(ELADASOK_KEY_USER_ID, user_id);
		ertek.put(ELADASOK_KEY_ARUKIADAS, arukiadas);
		ertek.put(ELADASOK_KEY_SS_ID, ss_id);
		ertek.put(ELADASOK_KEY_KINEK_USER_ID, kinek_user_id);
		return mDb.insert(TABLA_ELADASOK, null, ertek);
	}

	public long createEladas(Date datum, String lezarva, int vendeg_id,
			int user_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		ContentValues ertek = new ContentValues();
		ertek.put(ELADAS_KEY_DATUM, dateFormat.format(datum));
		ertek.put(ELADAS_KEY_LEZARVA, lezarva);
		ertek.put(ELADAS_KEY_VENDEG_ID, vendeg_id);
		ertek.put(ELADAS_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_ELADAS, null, ertek);
	}

	public long createHitel(Date datum, String osszeg, int vendeg_id,
			int kiad_user_id, int fizet_user_id, String fizetve) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		ContentValues ertek = new ContentValues();
		ertek.put(HITEL_KEY_DATUM, dateFormat.format(datum));
		ertek.put(HITEL_KEY_OSSZEG, osszeg);
		ertek.put(HITEL_KEY_VENDEG_ID, vendeg_id);
		ertek.put(HITEL_KEY_KIAD_USER_ID, kiad_user_id);
		ertek.put(HITEL_KEY_FIZET_USER_ID, fizet_user_id);
		ertek.put(HITEL_KEY_FIZETVE, fizetve);

		return mDb.insert(TABLA_HITEL, null, ertek);
	}

	public long createVendeg(String nev, String cim, String tel, String email,
			int user_id) {
		ContentValues ertek = new ContentValues();
		ertek.put(VENDEG_KEY_NEV, nev);
		ertek.put(VENDEG_KEY_CIM, cim);
		ertek.put(VENDEG_KEY_TEL, tel);
		ertek.put(VENDEG_KEY_EMAIL, email);
		ertek.put(VENDEG_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_VENDEG, null, ertek);
	}

	public long createKtg(String megnevezes, int tipus_id, String ne,
			String br, Date datum, String tag, int user_id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		ContentValues ertek = new ContentValues();
		ertek.put(KTG_KEY_MEGNEVEZES, megnevezes);
		ertek.put(KTG_KEY_KTGTIPUS_ID, tipus_id);
		ertek.put(KTG_KEY_NE, ne);
		ertek.put(KTG_KEY_BR, br);
		ertek.put(KTG_KEY_DATUM, dateFormat.format(datum));
		ertek.put(KTG_KEY_TAG, tag);
		ertek.put(KTG_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_KTG, null, ertek);
	}

	public long createKtgt(String megnevezes, String tipus, int kapcs_id,
			String ne, String br, String tag, int user_id) {
		ContentValues ertek = new ContentValues();
		ertek.put(KTG_T_KEY_MEGNEVEZES, megnevezes);
		ertek.put(KTG_T_KEY_TIPUS, tipus);
		ertek.put(KTG_T_KEY_KAPCS_ID, kapcs_id);
		ertek.put(KTG_T_KEY_NE, ne);
		ertek.put(KTG_T_KEY_BR, br);
		ertek.put(KTG_T_KEY_TAG, tag);
		ertek.put(KTG_T_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_KTG_T, null, ertek);
	}

	public long createFelhasznalo(String nev, String p, String admin,
			int user_id) {
		ContentValues ertek = new ContentValues();
		ertek.put(FELHASZNALO_KEY_NEV, nev);
		ertek.put(FELHASZNALO_KEY_P, p);
		ertek.put(FELHASZNALO_KEY_ADMIN, admin);
		ertek.put(FELHASZNALO_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_FELHASZNALO, null, ertek);
	}

	public long createFelhasznalo(String nev, String p, String admin,
			int user_id, String vetelezes, String standolas, String koltseg,
			String bevetel, String informaciok) {
		ContentValues ertek = new ContentValues();
		ertek.put(FELHASZNALO_KEY_NEV, nev);
		ertek.put(FELHASZNALO_KEY_P, p);
		ertek.put(FELHASZNALO_KEY_ADMIN, admin);
		ertek.put(FELHASZNALO_KEY_USER_ID, user_id);
		ertek.put(FELHASZNALO_KEY_VETELEZES, vetelezes);
		ertek.put(FELHASZNALO_KEY_STANDOLAS, standolas);
		ertek.put(FELHASZNALO_KEY_BEVETEL, bevetel);
		ertek.put(FELHASZNALO_KEY_KOLTSEG, koltseg);
		ertek.put(FELHASZNALO_KEY_INFORMACIOK, informaciok);
		return mDb.insert(TABLA_FELHASZNALO, null, ertek);
	}

	public long createSs(int aru_id, String nyitokeszlet, int s_id,
			String osszesen, String fogyas, String brutto, String netto) {
		ContentValues ertek = new ContentValues();
		ertek.put(SS_KEY_ARU_ID, aru_id);
		ertek.put(SS_KEY_NYITOKESZLET, nyitokeszlet);
		ertek.put(SS_KEY_S_ID, s_id);
		ertek.put(SS_KEY_OSSZESEN, osszesen);
		ertek.put(SS_KEY_FOGYAS, fogyas);
		ertek.put(SS_KEY_BR, brutto);
		ertek.put(SS_KEY_NE, netto);
		return mDb.insert(TABLA_SS, null, ertek);
	}

	public long createS(Date datum, int felvivo_user_id, int akie_user_id,
			String brutto, String netto) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(S_KEY_DATUM, dateFormat.format(datum));
		ertek.put(S_KEY_USER_ID, felvivo_user_id);
		ertek.put(S_KEY_BR, brutto);
		ertek.put(S_KEY_NE, netto);
		return mDb.insert(TABLA_S, null, ertek);
	}

	public long createSF(int s_id, int user_id) {
		ContentValues ertek = new ContentValues();
		ertek.put(SF_KEY_S_ID, s_id);
		ertek.put(S_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_SF, null, ertek);
	}

	public long createMaradvany(int aru_id, int ss_id, String mennyiseg) {
		ContentValues ertek = new ContentValues();
		ertek.put(MARADVANY_KEY_ARU_ID, aru_id);
		ertek.put(MARADVANY_KEY_SS_ID, ss_id);
		ertek.put(MARADVANY_KEY_MENNYISEG, mennyiseg);
		return mDb.insert(TABLA_MARADVANY, null, ertek);
	}

	public long createEgyebBevetel(String megnevezes, int ne, int br,
			Date datum, int user_id, String szamol) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

		ContentValues ertek = new ContentValues();
		ertek.put(EGYEB_BEVETEL_KEY_DATUM, dateFormat.format(datum));
		ertek.put(EGYEB_BEVETEL_KEY_MEGNEVEZES, megnevezes);
		ertek.put(EGYEB_BEVETEL_KEY_NE, ne);
		ertek.put(EGYEB_BEVETEL_KEY_BR, br);
		ertek.put(EGYEB_BEVETEL_KEY_USER_ID, user_id);
		ertek.put(EGYEB_BEVETEL_KEY_SZAMOL, szamol);
		return mDb.insert(TABLA_EGYEB_BEVETEL, null, ertek);
	}

	public long createMenu(String menut, String menu, int ertek1, int user_id) {
		ContentValues ertek = new ContentValues();
		ertek.put(MENU_KEY_MENUT, menut);
		ertek.put(MENU_KEY_MENU, menu);
		ertek.put(MENU_KEY_ERTEK, ertek1);
		ertek.put(MENU_KEY_USER_ID, user_id);
		return mDb.insert(TABLA_MENU, null, ertek);
	}

	public boolean deleteAllArut() {
		int del = 0;
		del = mDb.delete(TABLA_ARUT, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllAru() {
		int del = 0;
		del = mDb.delete(TABLA_ARU, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllEladasiar() {
		int del = 0;
		del = mDb.delete(TABLA_ELADASIAR, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllBeszerzesiar() {
		int del = 0;
		del = mDb.delete(TABLA_BESZERZESIAR, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllVetelezes() {
		int del = 0;
		del = mDb.delete(TABLA_VETELEZES, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllSzamla() {
		int del = 0;
		del = mDb.delete(TABLA_SZAMLA, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllSzallito() {
		int del = 0;
		del = mDb.delete(TABLA_SZALLITO, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllAe() {
		int del = 0;
		del = mDb.delete(TABLA_AE, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllEladasok() {
		int del = 0;
		del = mDb.delete(TABLA_ELADASOK, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllEladas() {
		int del = 0;
		del = mDb.delete(TABLA_ELADAS, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllHitel() {
		int del = 0;
		del = mDb.delete(TABLA_HITEL, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllVendeg() {
		int del = 0;
		del = mDb.delete(TABLA_VENDEG, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllKtg() {
		int del = 0;
		del = mDb.delete(TABLA_KTG, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllKtgt() {
		int del = 0;
		del = mDb.delete(TABLA_KTG_T, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllFelhasznalo() {
		int del = 0;
		del = mDb.delete(TABLA_FELHASZNALO, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllSs() {
		int del = 0;
		del = mDb.delete(TABLA_SS, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllS() {
		int del = 0;
		del = mDb.delete(TABLA_S, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllSF() {
		int del = 0;
		del = mDb.delete(TABLA_SF, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllMaradvany() {
		int del = 0;
		del = mDb.delete(TABLA_MARADVANY, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllEgyebBevtel() {
		int del = 0;
		del = mDb.delete(TABLA_EGYEB_BEVETEL, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public boolean deleteAllMenu() {
		int del = 0;
		del = mDb.delete(TABLA_MENU, null, null);
		Log.w(TAG, del + ". db törölve");
		return del > 0;
	}

	public Cursor osszFelhasznalok() {
		Cursor c = null;
		c = mDb.query(TABLA_FELHASZNALO,
				new String[] { FELHASZNALO_KEY_ROWID, FELHASZNALO_KEY_NEV,
						FELHASZNALO_KEY_P, FELHASZNALO_KEY_ADMIN }, null, null,
				null, null, null);
		return c;
	}

	public Cursor fetchArut(boolean lathato){
		Cursor mCursor =null;
		if (lathato) {
			mCursor=mDb.query(TABLA_ARUT, new String[] {ARUT_KEY_ROWID,ARUT_KEY_MEGNEVEZES,
					ARUT_KEY_LATHATO,ARUT_KEY_KISZERELES,ARUT_KEY_SORREND}, ARUT_WHERE_LATHATO_TRUE,
					null, null, null, ARUT_KEY_SORREND);
			
		}
		else{
			mCursor=mDb.query(TABLA_ARUT, new String[] {ARUT_KEY_ROWID,ARUT_KEY_MEGNEVEZES,
					ARUT_KEY_LATHATO,ARUT_KEY_KISZERELES,ARUT_KEY_SORREND}, ARUT_WHERE_LATHATO_FALSE,
					null, null, null, ARUT_KEY_SORREND);
			
		}
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	
	public Cursor fetchFoMenu(int user_id) {

		Log.w(TAG, "fetchFõmenü, mCursor példányosítása elõtt");
		Cursor mCursor = null;
		mCursor = mDb.query(TABLA_MENU, new String[] { MENU_KEY_ROWID,
				MENU_KEY_MENU }, FOMENU_WHERE + user_id + "'", null, null,
				null, MENU_KEY_ERTEK);
		Log.w(TAG, mCursor.toString());
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	
	public int getUserId(String user) {
		Cursor mCursor = null;
		mCursor = mDb.query(TABLA_FELHASZNALO, new String[] { FELHASZNALO_KEY_ROWID,
				FELHASZNALO_KEY_NEV }, FELHASZNALO_KEY_NEV + " like '" + user
				+ "'", null, null, null, null);
		Log.w(TAG, mCursor.toString());
		if (mCursor != null) {
			if (mCursor.getCount()==0){}
			else{
			mCursor.moveToFirst();
			return mCursor.getInt(0);}
		}
		return 0;
	}
	public int countArut() {
		Cursor mCursor = null;
		mCursor = mDb.rawQuery("select count(*) from aru_tipus", null);
		Log.w(TAG, mCursor.toString());
		if (mCursor != null) {
			if (mCursor.getCount()==0){}
			else{
			mCursor.moveToFirst();
			return mCursor.getInt(0);}
		}
		return 0;
	}
	public void createMenuRendszer(int user_id) {
		Cursor mCursor = mDb.query(TABLA_MENU, new String[] { MENU_KEY_MENU,
				MENU_KEY_USER_ID }, FOMENU_WHERE + user_id + "'", null, null,
				null, null);
		Log.w(TAG, "fetchFõmenü, createmenurendszer mcursor után");
		Log.w(TAG,
				"cursor sorainak száma=" + Integer.toString(mCursor.getCount()));
		if (mCursor.getCount() == 0) {

			Log.w(TAG, "fetchFõmenü, krb elõtt");
			createMenu("fomenu", "Bónuszkör", 1, user_id);
			createMenu("fomenu", "Vételezés", 2, user_id);
			createMenu("fomenu", "Standolás", 3, user_id);
			createMenu("fomenu", "Hitel", 4, user_id);
			createMenu("fomenu", "Költség", 5, user_id);
			createMenu("fomenu", "Bevétel", 6, user_id);
			createMenu("fomenu", "Információk", 7, user_id);
			createMenu("fomenu", "Karbantartás", 8, user_id);
			createMenu("fomenu", "Kijelentkezés", 9, user_id);
			createMenu("fomenu", "Kilépés", 10, user_id);
		}
	}

	public void createMenuRendszer(int user_id, boolean vetel, boolean stand,
			boolean ktg, boolean bev, boolean info) {
		Cursor mCursor = mDb.query(TABLA_MENU, new String[] { MENU_KEY_MENU,
				MENU_KEY_USER_ID }, FOMENU_WHERE + user_id + "'", null, null,
				null, null);
		Log.w(TAG, "fetchFõmenü, createmenurendszer mcursor után");
		Log.w(TAG,
				"cursor sorainak száma=" + Integer.toString(mCursor.getCount()));
		if (mCursor.getCount() == 0) {
			int sorrend = 1;
			Log.w(TAG, "fetchFõmenü, krb elõtt");
			createMenu("fomenu", "Bónuszkör", sorrend, user_id);
			if (vetel) {
				sorrend++;
				createMenu("fomenu", "Vételezés", sorrend, user_id);
			}
			if (stand) {
				sorrend++;
				createMenu("fomenu", "Standolás", sorrend, user_id);
			}
			sorrend++;
			createMenu("fomenu", "Hitel", sorrend, user_id);
			if (ktg) {
				sorrend++;
				createMenu("fomenu", "Költség", sorrend, user_id);
			}
			if (bev) {
				sorrend++;
				createMenu("fomenu", "Bevétel", sorrend, user_id);
			}
			if (info) {
				sorrend++;
				createMenu("fomenu", "Információk", sorrend, user_id);
			}
			sorrend++;
			createMenu("fomenu", "Kijelentkezés", sorrend, user_id);
			sorrend++;
			createMenu("fomenu", "Kilépés", sorrend, user_id);
		}
	}

	/*
	 * public Cursor fetchArut() {
	 * 
	 * Log.w(TAG, "fetchFõmenü, mCursor példányosítása elõtt"); Cursor mCursor =
	 * null; mCursor = mDb.query(TABLA_MENUK, new String[] { MENUK_KEY_ROWID,
	 * MENUK_KEY_MEGNEVEZES }, FOMENU_WHERE, null, null, null,
	 * MENUK_KEY_SORREND); Log.w(TAG, mCursor.toString()); if (mCursor != null)
	 * { mCursor.moveToFirst(); } return mCursor; }
	 */

	/*
	 * public void createMenuRendszer() { Cursor mCursor =
	 * mDb.query(TABLA_MENUK, new String[] { MENUK_KEY_MEGNEVEZES }, null, null,
	 * null, null, null); Log.w(TAG,
	 * "fetchFõmenü, createmenurendszer mcursor után"); Log.w(TAG,
	 * "cursor sorainak száma=" + Integer.toString(mCursor.getCount())); if
	 * (mCursor.getCount() == 0) {
	 * 
	 * Log.w(TAG, "fetchFõmenü, krb elõtt"); createMenu("fomenu",
	 * "Karbantartás", "igen", 4,1); Log.w(TAG, "fetchFõmenü, stand elõtt");
	 * createMenu("fomenu", "Stand", "igen", 3,1); Log.w(TAG,
	 * "fetchFõmenü, mCursor üzlet elõtt"); createMenu("fomenu", "Üzlet",
	 * "igen", 2,1); Log.w(TAG, "fetchFõmenü, információk elõtt");
	 * createMenu("fomenu", "Információk", "igen", 1,1); createMenu("beallitas",
	 * "Kilépés", "nem", 1,1); createMenu("beallitas", "Menüsorrendek", "igen",
	 * 2,1); createMenu("beallitas", "Kijelentkezés", "igen", 1,1);
	 * createMenu("beallitasmenusorrend", "Fõmenü", "igen", 1,1);
	 * createMenu("beallitasmenusorrend", "Karbantartás", "igen", 2,1);
	 * createMenu("beallitasmenusorrend", "Stand", "igen", 3,1);
	 * createMenu("beallitasmenusorrend", "Üzlet", "igen", 4,1);
	 * createMenu("beallitasmenusorrend", "Beállítások", "igen", 5,1);
	 * createMenu("beallitasmenusorrend", "Beállítások / Menüsorrendek", "igen",
	 * 6,1); } }
	 */
}
