package com.telanganatourism.android.phase2.database.helper;

import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TtHelper extends BaseDatabase {

	private final String DESTINATION_CONTENT = "Destination_Content";
	private final String SHOPPING_CONTENT = "Shop_Online";
	private final String ACCOMODATION__CONTENT = "Accomodation_Content";
	private final String CULTURE__CONTENT = "Culture_Content";
	private final String DESTINATION_CATEGORY = "DestinationCategoryList";
	private final String PACKAGES_CATEGORY = "TourPackagesCategoryList";
	private final String PACKAGES_CONTENT = "TourPackagesContent";
	
	private final String STORED_TIME = "timestamp";
	 
	private final String DESTINATION_DETAILS = "Destinations_Detail_Content";
	
	private final String SLIDE_IMAGES = "slide_images";

	public TtHelper(Context context) throws IOException {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public void insertDestinationCategory(String id, String data) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put("id", id);
			cv.put("ResponseContent", data);

			db.insert(DESTINATION_CATEGORY, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	} 
	
	public void insertSlideImagesContent(String lang_id, String loc_id, String data) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put("language_id", lang_id);
			cv.put("location_id", loc_id);
			cv.put("ResponseContent", data);

			db.insert(SLIDE_IMAGES, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void insertDestinationCategory1(String id, String data, String language_id) throws Exception {
 
		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
  
			cv.put("id", id);
			cv.put("ResponseContent", data);
			cv.put("language_id", language_id);
 
			db.insert(DESTINATION_CATEGORY, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateDestinationCategory(String uniqueName,
			String data) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(DESTINATION_CATEGORY, cv,
				"id" + "=?", new String[] {
				uniqueName });
	}
	
	 
	public void updateDestinationCategory1(String uniqueName,
			String data,String language_id) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);
 
//		db.update(DESTINATION_CATEGORY, cv,
//				"id" + "=?", new String[] {
//				uniqueName });
		 
		db.update(DESTINATION_CATEGORY, cv,
				"language_id" + "=?  AND id=?", new String[] {
				language_id ,uniqueName});
	}
	
	public void insertTouPackagesCategory(String id, String data, String lang_id) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put("id", id);
			cv.put("ResponseContent", data);
			cv.put("language_id", lang_id);

			db.insert(PACKAGES_CATEGORY, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateTourPackagesCategory(String language_id,
			String data) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(PACKAGES_CATEGORY, cv,
				"language_id" + "=?", new String[] {
				language_id });
	}
	
	public void insertTouPackages(String id, String uniqueName, String data, String lang_id) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", lang_id);

			db.insert(PACKAGES_CONTENT, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void updateTourPackagesContent(String uniqueName,
			String data,String language_id) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(PACKAGES_CONTENT, cv,
				"language_id" + "=?  AND ModuleUniqueName=?", new String[] {
				language_id ,uniqueName});
	}
	
	



	public void insertDestinationContent(String id, String uniqueName,
			String data, String language_id) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", language_id);

			db.insert(DESTINATION_CONTENT, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateDestinationContent(String uniqueName,
			String data) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(DESTINATION_CONTENT, cv,
				"ModuleUniqueName" + "=?", new String[] {
				uniqueName });
	}
	
	public void insertAccomodationContent(String languageId, String id, String uniqueName,
			String data) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("language_id", languageId);
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);

			db.insert(ACCOMODATION__CONTENT, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	} 

	public void updateAccomodationContent(String languageId,
			String data, String locId) throws Exception {
 
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(ACCOMODATION__CONTENT, cv,
				"language_id" + "=?  AND id=?", new String[] {
				languageId, locId });
	}
	
	public void insertCultureContent(String id, String uniqueName,
			String data) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);

			db.insert(CULTURE__CONTENT, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	} 

	public void updateCultureContent(String data, String language) throws Exception {
 
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(CULTURE__CONTENT, cv,
				"language_id" + "=?", new String[] {
				language });
	}
	
	
	// Details
	public void insertDetailsContent(String tableName, String id, String uniqueName,
			String data, String language) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", language);

			db.insert(DESTINATION_CONTENT, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void insertShoppingContent(String tableName, String id, String uniqueName,
			String data, String language) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", language);

			db.insert(SHOPPING_CONTENT, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void insertDetailsContent1(String tableName, String id, String uniqueName,
			String data,String language_id) throws Exception {
 
		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("language_id", language_id);
			cv.put("id", id); 
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
   
			db.insert(tableName, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void insertDestinationDetailsContent(String id, String uniqueName,
			String data, String lang_id) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", lang_id);

			db.insert(DESTINATION_DETAILS, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("Ex :", ""+e);
		}
	}
	
	public void updateDestinationDetailsContent(String uniqueName,
			String data, String locationId) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(DESTINATION_DETAILS, cv,
				"ModuleUniqueName" + "=?", new String[] {
				uniqueName });
	}

	public void updateDetailsContent(String tableName, String uniqueName,
			String data, String locationId, String lang_id) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(DESTINATION_CONTENT, cv,
				"ModuleUniqueName" + "=? AND language_id=?", new String[] {
				uniqueName, lang_id });
	}
	
	public void updateDetailsContent1(String tableName, String uniqueName,
			String data, String locationId,String language_id) throws Exception {
  
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

//		db.update(tableName, cv,
//				"ModuleUniqueName" + "=? AND id=?", new String[] {
//				uniqueName, locationId });
		 
		  
		db.update(tableName, cv,
				"language_id" + "=?  AND id=? AND ModuleUniqueName=?", new String[] {
				language_id ,locationId,uniqueName});
	}
	
	public void insertAccomodationDetailsContent(String tableName, String id, String uniqueName,
			String data, String lang_id) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", lang_id);

			db.insert(tableName, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateAccomodationDetailsContent(String tableName, String uniqueName,
			String data, String lang_id) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(tableName, cv,
				"ModuleUniqueName" + "=? AND language_id=?", new String[] {
				uniqueName, lang_id });
	}
	
	public void insertCultureDetailsContent(String tableName, String id, String uniqueName,
			String data, String languageId) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", languageId);

			db.insert(tableName, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void insertSlideImageContent(String uniqueName,
			String data) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();
 
			ContentValues cv = new ContentValues();
			cv.put("location_id", uniqueName);
			cv.put("ResponseContent", data);

			db.insert("slide_images", null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updateCultureDetailsContent(String tableName, String uniqueName,
			String data, String locationId) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put("ResponseContent", data);

		db.update(tableName, cv,
				"ModuleUniqueName" + "=?", new String[] {
				uniqueName });
	}
	
	public void insertPackageDetailsContent(String tableName, String id, String uniqueName,
			String data, String language_id) throws Exception {

		// deleteAllData(WHERE_TO_STAY);
		try {
			SQLiteDatabase db = this.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put("id", id);
			cv.put("ModuleUniqueName", uniqueName);
			cv.put("ResponseContent", data);
			cv.put("language_id", language_id);

			db.insert(tableName, null, cv);
			db.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void updatePackageDetailsContent(String uniqueName,
			String data,String language_id) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

//		cv.put("ModuleUniqueName", uniqueName);
		cv.put("ResponseContent", data);

		
		db.update("TourPackagesDetailContent", cv,
				"language_id" + "=?  AND ModuleUniqueName=?", new String[] {
				language_id ,uniqueName});
	}
	
	public void updateStoredTime(String storeMethod, String storeTime) throws Exception {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		cv.put(storeMethod, storeTime);

		db.update("time_stamp_update", cv,
				"id=?", new String[] {"1"});
	}
	
	public void deleteAll(String tablename) {
		SQLiteDatabase db = this.getWritableDatabase();
//		return db.delete(tablename, null, null);
		
		
		Log.d("query", tablename);
		db.execSQL(tablename);
	}

}