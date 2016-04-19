package com.telanganatourism.android.phase2.database.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class BaseDatabase extends SQLiteOpenHelper {
	public SQLiteDatabase myDataBase;
	private final Context myContext;
	private static final String DATABASE_NAME = "telanganatour";
	public final static String DATABASE_PATH = Environment.getDataDirectory() + "/data/com.telanganatourism.android.phase2/databases/";
	public static final int DATABASE_VERSION = 1;

	// public static final int DATABASE_VERSION_old = 1;

	// Constructor
	public BaseDatabase(Context context) {
		super(context, "telanganatour", null, 1);
		this.myContext = context;
	}

	// Create a empty database on the system
	public void createDataBase() throws IOException {
		// boolean dbExist = checkDataBase();
		//
		// if (dbExist) {
		// Log.v("DB Exists", "db exists");
		// // By calling this method here onUpgrade will be called on a
		// // writeable database, but only if the version number has been
		// // bumped
		// // onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
		// }

		boolean dbExist1 = checkDataBase();
		SQLiteDatabase db_Read = null;
		
		if (!dbExist1) {
			db_Read = this.getReadableDatabase(); 
			db_Read.close();
			try {
				this.close();
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	// Check database already exist or not
	private boolean checkDataBase() {
		boolean checkDB = false;
		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			File dbfile = new File(myPath);
			checkDB = dbfile.exists();
		} catch (SQLiteException e) {
		}
		return checkDB;
	}

	// Copies your database from your local assets-folder to the just created
	// empty database in the system folder
	private void copyDataBase() throws IOException {
		String outFileName = DATABASE_PATH + DATABASE_NAME;
		OutputStream myOutput = new FileOutputStream(outFileName);
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myInput.close();
		myOutput.flush();
		myOutput.close();
	}

	// delete database
	public void db_delete() {
		File file = new File(DATABASE_PATH + DATABASE_NAME);
		if (file.exists()) {
			file.delete();
			System.out.println("delete database file.");
		}
	}

	public Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		return myDataBase.query("data_table", null, null, null, null, null,
				null);

	}

	public Cursor qureyDatabase(String sql) {
		Cursor cursor = myDataBase.rawQuery(sql, null);
		return cursor;
	}

	// Open database
	public void openDataBase() throws SQLException {
		String myPath = DATABASE_PATH + DATABASE_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);
	}

	public int display(String str) {
		// use cursor to keep all data
		// cursor can keep data of any data type
		int postion = 0;
		Cursor cursor = null;
		String Query = "select * from " + str;
		cursor = myDataBase.rawQuery(Query, null);
		if (cursor != null && cursor.moveToFirst()) {
			// do {
			//
			// } while (cursor.moveToNext());
			postion = cursor.getCount();
			cursor.close();
		}

		return postion;
	}
	
	public int checkModuleId(String tableName, String strid, String locId) {
		// use cursor to keep all data
		// cursor can keep data of any data type
		int postion = 0;
		Cursor cursor = null;
		String Query = "select * from "+ tableName + " where ModuleUniqueName="+ strid + " AND id="+locId;
		cursor = myDataBase.rawQuery(Query, null);
		if (cursor != null && cursor.moveToFirst()) {
			// do {
			//
			// } while (cursor.moveToNext());
			postion = cursor.getCount();
			cursor.close();
		}
 
		return postion;
	} 
	
	public int checkModuleId1(String tableName, String strid, String locId, String language_id) {
		// use cursor to keep all data
		// cursor can keep data of any data type
		int postion = 0; 
		Cursor cursor = null;
//		String Query = "select * from "+ tableName + " where ModuleUniqueName="+ strid + " AND id="+locId+ " AND id="+locId;
		String Query = "select * from "+ tableName + " where language_id="+ language_id + " AND id="+locId+ " AND ModuleUniqueName="+strid;
 
		cursor = myDataBase.rawQuery(Query, null);
		if (cursor != null && cursor.moveToFirst()) {
			// do {
			//
			// } while (cursor.moveToNext());
			postion = cursor.getCount();
			cursor.close();
		}
 
		return postion;
	}
	
	public int checkCategoryId(String tableName, String strid) {
		// use cursor to keep all data
		// cursor can keep data of any data type
		int postion = 0;
		Cursor cursor = null;
		String Query = "select * from "+ tableName + " where id="+ strid;
		cursor = myDataBase.rawQuery(Query, null);
		if (cursor != null && cursor.moveToFirst()) {
			// do {
			//
			// } while (cursor.moveToNext());
			postion = cursor.getCount();
			cursor.close();
		}

		return postion;
	} 
	  
	public int checkCategoryId1(String tableName, String strid,String language_id) {
		// use cursor to keep all data
		// cursor can keep data of any data type 
		int postion = 0; 
		Cursor cursor = null;  
//		String Query = "select * from "+ tableName + " where id="+ strid;
		String Query = "select * from "+ tableName + " where language_id="+ language_id + " AND id="+strid;
		cursor = myDataBase.rawQuery(Query, null);
		if (cursor != null && cursor.moveToFirst()) {
			// do {
			//
			// } while (cursor.moveToNext());
			postion = cursor.getCount();
			cursor.close();
		}

		return postion;
	}

	public synchronized void closeDataBase() throws SQLException {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}

	public void onCreate(SQLiteDatabase db) {
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (newVersion > oldVersion) {
			Log.v("Database Upgrade", "Database version higher than old.");
			db_delete();
		}
	}

	// add your public methods for insert, get, delete and update data in
	// database.
}
