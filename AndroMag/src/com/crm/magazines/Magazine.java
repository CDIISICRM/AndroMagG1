package com.crm.magazines;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

public class Magazine  implements Parcelable {

		private long id;
		private String nom;
		private float prix;
		private String dateDInscription; 
	    private Boolean visible;	
		private List<Theme> themes = new ArrayList<Theme>(4);
		
		public enum Theme  {	Jardin,Musique,Maison,TV};
		
	
		
		public static final String TABLE_MAGAZINE = "magazines";
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_NOM = "nom";
		public static final String COLUMN_PRIX = "prix";
		public static final String COLUMN_DATEDINSCRIPTION = "dateDInscription";
	    public static final String COLUMN_VISIBLE= "visible";
	    public static final String COLUMN_THEMES = "themes";
		public static final String[] allColumns = { COLUMN_ID, COLUMN_NOM, COLUMN_PRIX, COLUMN_DATEDINSCRIPTION, COLUMN_VISIBLE, COLUMN_THEMES };
		 
		
		static final String MAGAZINES_CREATE = "create table "
				+ Magazine.TABLE_MAGAZINE + "( " 
				+ Magazine.COLUMN_ID  + " integer primary key autoincrement, "
				+ Magazine.COLUMN_NOM + " text not null, " 
				+ Magazine.COLUMN_PRIX + " real not null, "
				+ Magazine.COLUMN_DATEDINSCRIPTION + " text not null, "
				+ Magazine.COLUMN_VISIBLE + " numeric, " 
				+ Magazine.COLUMN_THEMES + " text);";
					  
	public long getId() {	return id;	}
	public void setId(long id){ this.id = id;	}

	public String getNom(){	 return nom;	}
	public void setNom(String nom) { this.nom = nom; }
		
	public float getPrix(){	 return prix;	}
	public void setPrix(float prix){this.prix= prix; } 

	public Boolean getVisible(){ return this.visible; }
	public void setVisible(Boolean visible){ this.visible = visible;  }
	
	public String getThemes(){	String listhemes = new String();		for (Theme theme : themes) listhemes += theme.toString();			return listhemes;		}
	public String getDateDInscription(){						return this.dateDInscription;				}
	public void setdateDInscription(String dateDInscription){	this.dateDInscription=dateDInscription;		}
	public void setdateDInscription(){
		Calendar c = Calendar.getInstance(); c.setTime(new Date());
		String mois = c.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, Locale.FRANCE);	
		String  datedujour =  c.get(Calendar.DAY_OF_MONTH) + " " + mois + " " + c.get(Calendar.YEAR);
		this.dateDInscription=datedujour;
		}
	
	
	public Magazine()	{super();}
	public Magazine(Context context) {super();}
	public Magazine(String nom,	float prix,	String dateDInscription, Boolean visible, ArrayList<Theme> themes) {
		super();
		this.nom=nom;
		this.prix = prix;
		this.dateDInscription=dateDInscription;
		this.visible = visible;		
		this.themes= themes;
	}
	
	public void AddTheme(Theme theme)	{	
		this.themes.add(theme);		
	}
	public int describeContents() { 	  return 0; 	     } 
	@Override
	public void writeToParcel(Parcel dest, int flags) {
	      dest.writeString(nom);
	      dest.writeFloat(prix);
	      dest.writeInt(visible ? 1 : 0);    	      
	      List<String> themestrings = new ArrayList<String>();
	      for (Theme theme : themes){
				themestrings.add(theme.name());
			}
			dest.writeStringList(themestrings);
		    dest.writeString(dateDInscription);
	}	
	public static final Creator<Magazine> CREATOR= new Creator<Magazine>() {		 
		  public Magazine createFromParcel(Parcel in) {
			Magazine magazine = new Magazine();
			magazine.nom = in.readString();
			magazine.prix = in.readFloat();

			magazine.visible = in.readInt()>=0 ? true : false;
			List<String> themestrings = new ArrayList<String>();
			in.readStringList(themestrings);
			for (String themestring : themestrings){
				magazine.themes.add(Theme.valueOf(themestring));		
			}
			magazine.dateDInscription = new Date(in.readLong()).toString();
				 return magazine;
		  }
		  	@Override
			 public Magazine[] newArray(int size) {		 return new Magazine[size];		}
		};		
	private Magazine(Parcel in){
		
		 this.nom = in.readString();
		 this.prix = in.readFloat();
		 
		 this.visible = in.readInt()>=0 ? true : false;
		 
		 List<String> themestrings = new ArrayList<String>();
		 in.readStringList(themestrings);
		 
			for (String themestring : themestrings){
				this.themes.add(Theme.valueOf(themestring));		
			}
			this.dateDInscription = new Date(in.readLong()).toString();
		}
		
		 @Override
	public String toString() { return nom;	}
	    
	public static Magazine cursorToMagazine(Cursor cursor) {
		        Magazine magazine = new Magazine();
		        magazine.setId(cursor.getLong(0));
		        magazine.setNom(cursor.getString(1));
		        magazine.setPrix(cursor.getFloat(2));
		        magazine.setdateDInscription(cursor.getString(3));
		        magazine.setVisible((cursor.getInt(4)==1)?true:false);
		        magazine.AddTheme(Theme.valueOf(cursor.getString(5)));
		        return magazine;
		    }
	/*
    private Magazine cursorToMagazine(Cursor cursor) {
		Magazine magazine = new Magazine();
		magazine.setId(cursor.getLong(0));
		magazine.setNom(cursor.getString(1));
		return magazine;
	}	
		*/
	}
