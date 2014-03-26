package com.crm.magazines;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.crm.commentaires.*;
import com.crm.magazines.Magazine.Theme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MagazineDBhelper 

	extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "andromag.db";
	private static final int DATABASE_VERSION = 1;

	 SQLiteDatabase database ;
	  
	public MagazineDBhelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		try {
			 database=super.getWritableDatabase();
		}
		catch(Exception e)
		{
		String s = e.toString();
		}
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		this.database = database;
		database.execSQL(Magazine.MAGAZINES_CREATE);
        database.execSQL(Comment.COMMENTS_CREATE);
    	String[][] mags = new String[][] { {"Plantes vertes","10","Jardin"}, {"Passer une soirée","2","Maison Jardin"}, {"Le ménage pour les débutants","1","Maison"},{"Décorer un élève ingénieur","8","TV"} };
		for (int n=0;n<mags.length;n++){	
			Magazine magazine = new Magazine();
			magazine.setNom(mags[n][0]);
			magazine.setPrix(Float.valueOf(mags[n][1]));
			magazine.setdateDInscription(new Date().toString());
			magazine.AddTheme(Theme.Jardin);	
			
			this.createMagazine(magazine);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MagazineDBhelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + Comment.TABLE_COMMENTS);
		db.execSQL("DROP TABLE IF EXISTS " + Magazine.TABLE_MAGAZINE);
		onCreate(db);	
	}
	
		
	 public Magazine createMagazine(Magazine magazine) {	
			magazine.setdateDInscription();
	        ContentValues values = new ContentValues();
	        values.put(Magazine.COLUMN_NOM, magazine.getNom());
	        values.put(Magazine.COLUMN_PRIX, String.valueOf(magazine.getPrix()));
	        values.put(Magazine.COLUMN_DATEDINSCRIPTION,  magazine.getDateDInscription());
	        values.put(Magazine.COLUMN_VISIBLE, 1);
	        values.put(Magazine.COLUMN_THEMES, magazine.getThemes());
	 
	        long insertId = database.insert(Magazine.TABLE_MAGAZINE, null,values);
	        Cursor cursor = database.query(Magazine.TABLE_MAGAZINE,Magazine.allColumns, Magazine.COLUMN_ID + " = " + insertId, null, null, null, null);
	        cursor.moveToFirst();
	        Magazine newMagazine = Magazine.cursorToMagazine(cursor);
	        cursor.close(); 
	        return newMagazine;
	    }
	    
	 public  List<Magazine> getAllMagazines() {
	        List<Magazine> magazines = new ArrayList<Magazine>(); 
	        SQLiteDatabase database=super.getWritableDatabase();
	        Cursor cursor = database.query(Magazine.TABLE_MAGAZINE, Magazine.allColumns, null, null, null, null, null);
	        cursor.moveToFirst();
	        int i = 0;
	        int size = cursor.getCount();
	        while (!cursor.isAfterLast()&&(i++<size)) {
	            Magazine magazine = Magazine.cursorToMagazine(cursor);
	            magazines.add(magazine);
	           cursor.moveToNext();
	 
	        }
	        cursor.close(); 
	        return magazines;
	    }
	
}
