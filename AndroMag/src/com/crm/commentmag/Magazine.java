package com.crm.commentmag;
import com.crm.commentmag.Themes.Theme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

public class Magazine implements Parcelable{

	    private long id;
		private String nom;
		private float prix;
		private Date dateDInscription; 
	    private Boolean visible;	
		private List<Theme> themes;
		
		public static final String TABLE_MAGAZINE = "magazines";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NOM = "nom";
		public static final String COLUMN_PRIX = "prix";
		public static final String COLUMN_DATEDINSCRIPTION = "dateDInscription";
	    public static final String COLUMN_VISIBLE= "visible";
	    public static final String COLUMN_THEMES = "themes";
		
	public long getId() {	return id;		}
	public void setId(long id){ this.id = id;	}

	public String getNom(){	 return nom;	}
	public void setNom(String nom) { this.nom = nom; }
		
	public float getPrix(){	 return prix;	}
	public void setPrix(float prix){this.prix= prix; } 

	public Boolean getVisible(){ return this.visible; }
	public void setVisible(Boolean visible){ this.visible = visible;  }
	
//	public List<Theme> getThemes(){ return this.themes; }
//	public void setThemes(List<Theme> themes){this.themes = themes; }
	
	public Date getDateDInscription(){return this.dateDInscription;}
	public void setdateDInscription(Date dateDInscription){this.dateDInscription=dateDInscription;}
	
	public static final Creator<Magazine> CREATOR= new Creator<Magazine>() {
			 
		  public Magazine createFromParcel(Parcel in) {
				 Magazine magazine = new Magazine();
				 magazine.nom = in.readString();
				 magazine.prix = in.readFloat();
				 magazine.dateDInscription = new Date(in.readLong());
				 magazine.visible = in.readInt()==1 ? true : false;
				 magazine.themes= new ArrayList<Theme>();
				 return magazine;
			 }

			 public Magazine[] newArray(int size) {		 return new Magazine[size];		}
		};
	
		public int describeContents() { 	  return 0; 	     } 
		
		@Override
		public String toString() { return nom;	}
	

		public void AddTheme(Theme theme)	{		this.themes.add(theme);		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
		      dest.writeString(nom);
		      dest.writeFloat(prix);
		      dest.writeLong(dateDInscription.getTime());
		      dest.writeInt(visible ? 1 : 0);
		      Themes.writeToParcel(dest, themes);

		}
		 private SQLiteDatabase database; 
		 private String[] allColumns = { COLUMN_ID, COLUMN_NOM, COLUMN_VISIBLE };
		 
		 public Magazine createMagazine(Magazine magazine) {
		        ContentValues values = new ContentValues();
		        values.put(COLUMN_NOM, magazine.getNom());
		        values.put(COLUMN_VISIBLE, 1);
		        long insertId = database.insert(TABLE_MAGAZINE, null,values);
		        Cursor cursor = database.query(TABLE_MAGAZINE,
		                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
		                null, null, null);
		        cursor.moveToFirst();
		        Magazine newMagazine = cursorToMagazine(cursor);
		        cursor.close();
		        return newMagazine;
		    }
		    
		    private Magazine cursorToMagazine(Cursor cursor) {
		        Magazine magazine = new Magazine();
		        magazine.setId(cursor.getLong(0));
//		        magazine.setMagazine(cursor.getString(1));
		        return magazine;
		    }
		    public List<Magazine> getAllMagazines() {
		        List<Magazine> magazines = new ArrayList<Magazine>();

		        Cursor cursor = database.query(TABLE_MAGAZINE,
		                allColumns, null, null, null, null, null);
		        cursor.moveToFirst();
		        while (!cursor.isAfterLast()) {
		            Magazine magazine = cursorToMagazine(cursor);
		            magazines.add(magazine);
		            cursor.moveToNext();
		        }
		        cursor.close();
		        return magazines;
		    }
		
	}
