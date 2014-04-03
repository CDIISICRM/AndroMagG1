package com.crm.commentmag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.crm.commentaires.*;
import com.crm.magazines.Magazine;
import com.crm.magazines.Magazine.Column;
import com.crm.magazines.Magazine.Theme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CommentMagDBhelper extends SQLiteOpenHelper{
	public static enum codeRequetes{AJOUTER_MAGAZINE,AJOUT_COMMENTAIRE,MASQUER_MAGAZINE};
	private static final String DATABASE_NAME = "andromag.db";
	private static final int DATABASE_VERSION = 1;

	 SQLiteDatabase database ;
	  
	public CommentMagDBhelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		try {
			 database=super.getWritableDatabase();
		}
		catch(Exception e)
		{
			String s;
		s = e.toString();
		s="";
		}
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		this.database = database;
		database.execSQL(Magazine.MAGAZINES_CREATE);
        database.execSQL(Commentaire.COMMENTAIRES_CREATE);
    	String[][][] mags = new String[][][] {{{"Plantes vertes"},{"10.4"},{"Jardin","TV"}},{{"Passer une soirée"},{"2.7"},{"Maison","Jardin","TV"}},
    								          {{"Le ménage pour les débutants"},{"1.1"},{"Maison"}},{{"Décorer un élève ingénieur"},{"8.2"},{"TV","Jardin"}}};
		for (int n=0;n<mags.length;n++){	
			Magazine magazine = new Magazine();
			magazine.setNom(mags[n][0][0]);
			magazine.setPrix(Float.valueOf(mags[n][1][0]));
			for(int i=0;i<mags[n][2].length;i++) 
				if (mags[n][2][i].length()>0) 
					magazine.AddTheme(Theme.valueOf(mags[n][2][i]));	
			this.createMagazine(magazine);
			}
		}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(CommentMagDBhelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + Commentaire.TABLE_COMMENTS);
		db.execSQL("DROP TABLE IF EXISTS " + Magazine.TABLE_MAGAZINE);
		onCreate(db);	
	}
			
	 /***************************************** MAGAZINES **************************************************************************/
	
	 public Magazine createMagazine(Magazine magazine) {	
			magazine.setdateDInscription();
	        ContentValues values = new ContentValues();
	        values.put(Magazine.Column.DATEDINSCRIPTION.name(),  magazine.getDateDInscription());
	        values.put(Magazine.Column.THEMES.name(), magazine.getThemes());
	        values.put(Magazine.Column.VISIBLE.name(), 1);
	        values.put(Magazine.Column.PRIX.name(), String.valueOf(magazine.getPrix()));
	        values.put(Magazine.Column.NOM.name(), magazine.getNom());   
	        long insertId = database.insert(Magazine.TABLE_MAGAZINE, null,values);
	        Cursor cursor = database.query(Magazine.TABLE_MAGAZINE, Magazine.allColumns, Magazine.Column.ID.name() + " = " + insertId, null, null, null, null);
	        cursor.moveToFirst();
	        magazine = Magazine.cursorToMagazine(cursor);
	        cursor.close(); 
	        return magazine;
	    }

	 
	 public  List<Magazine> getAllMagazines() {
	        List<Magazine> magazines = new ArrayList<Magazine>(); 
	        SQLiteDatabase database=super.getWritableDatabase();
	        Cursor cursor = database.query(Magazine.TABLE_MAGAZINE, Magazine.allColumns,Magazine.Column.VISIBLE.name() + " = 1", null, null, null, null);
	        cursor.moveToFirst();
	        while(!cursor.isAfterLast()){
	            Magazine magazine = Magazine.cursorToMagazine(cursor);
	            getAllComments(magazine);
	            magazines.add(magazine);
	           cursor.moveToNext();
	        }
	        cursor.close(); 
	        return magazines;
	    }
          
	 public int updateMasqueOn(Magazine magazine){
		 int rowsUpdated = 0;
		 SQLiteDatabase database=super.getWritableDatabase();
		 ContentValues values = new ContentValues();
	     values.put(Magazine.Column.VISIBLE.name(), 0);
	     rowsUpdated = database.update(Magazine.TABLE_MAGAZINE, values , Magazine.Column.ID.name() + "=" + magazine.getId(), null);	
	     return rowsUpdated;
	 }	
	 
	 /***************************************** COMMENTAIRES **************************************************************************/
	 
		public Commentaire createComment(Magazine magazine) {
			ContentValues values = new ContentValues();
			int size = magazine.getCommentaires().size();
			Commentaire commentaire = magazine.getCommentaires().get(size-1);
			values.put(Commentaire.Column.RUBRIQUE.name(), commentaire.getRubrique());
			values.put(Commentaire.Column.NUMERO.name(), commentaire.getNumero());
			values.put(Commentaire.Column.NOTE.name(), commentaire.getNote());
			values.put(Commentaire.Column.TEXTE.name(), commentaire.getTexte());
			values.put(Commentaire.Column.ID_MAGAZINE.name(), magazine.getId());
			long insertId = database.insert(Commentaire.TABLE_COMMENTS, null, values);		
			Cursor cursor = database.query(Commentaire.TABLE_COMMENTS, Commentaire.allColumns, Commentaire.Column.ID.name() + " = " + insertId, null, null, null, null);
			cursor.moveToFirst();
			commentaire = Commentaire.cursorToCommentaire(cursor);
			magazine.AddCommentaire(commentaire);
			cursor.close();
			return commentaire;
		}
		
	 	public void getAllComments(Magazine magazine) {
			List<Commentaire> commentaires = new ArrayList<Commentaire>();
	        SQLiteDatabase database=super.getWritableDatabase();
			Cursor cursor = database.query(Commentaire.TABLE_COMMENTS,Commentaire.allColumns, Commentaire.Column.ID_MAGAZINE.name() + " = " + String.valueOf(magazine.getId()), null, null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Commentaire commentaire = Commentaire.cursorToCommentaire(cursor);
				commentaires.add(commentaire);
				cursor.moveToNext();
			}
			cursor.close();
			magazine.setCommentaires(commentaires);
		}
}
