package com.crm.magazines;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.crm.commentaires.Commentaire;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import static java.util.Calendar.*;

public class Magazine  implements Parcelable {

		private long id;
		private String nom;
		private float prix;
	    private Boolean visible;	
	    private List<Theme> themes = new ArrayList<Theme>(4);
	    private List<Commentaire> commentaires= new ArrayList<Commentaire>();
	    private String dateDInscription; 
	    

		public static enum Theme  { Jardin,Musique,Maison,TV };
		public static final String TABLE_MAGAZINE = "magazines";
		public static enum  Column { ID, NOM, PRIX, VISIBLE, THEMES, DATEDINSCRIPTION };		
		
		public static final String[] allColumns = { Column.ID.name(), Column.NOM.name(), Column.PRIX.name(), 
													Column.VISIBLE.name(), Column.THEMES.name(), Column.DATEDINSCRIPTION.name() };
		 	
		public static final String MAGAZINES_CREATE = "create table "
				+ Magazine.TABLE_MAGAZINE + "( " 
				+ Magazine.Column.ID  + " integer primary key autoincrement, "
				+ Magazine.Column.NOM + " text not null, " 
				+ Magazine.Column.PRIX + " real not null, "
				+ Magazine.Column.VISIBLE + " numeric, " 
				+ Magazine.Column.THEMES + " text, "
				+ Magazine.Column.DATEDINSCRIPTION + " text not null);";
		
	
					  
	public long getId() {	return id;	}
	public void setId(long id){ this.id = id;	}

	public String getNom(){	 return nom;	}
	public void setNom(String nom) { this.nom = nom; }
		
	public float getPrix(){	 return prix;	}
	public void setPrix(float prix){this.prix= prix; } 

	public Boolean getVisible(){ return this.visible; }
	public void setVisible(Boolean visible){ this.visible = visible;  }
	
	public String getThemes(){
		String listhemes = new String();	
		for (Theme theme : themes) listhemes += theme.toString()+ "  ";	
		return listhemes;	
		}

	public void AddTheme(Theme theme)  {			this.themes.add(theme);			}
	public String getDateDInscription(){			return this.dateDInscription;	}
	public void setdateDInscription(String dateDInscription){	this.dateDInscription=dateDInscription;		}
	public void setdateDInscription(){
		Calendar c = Calendar.getInstance(); c.setTime(new Date());
		int date = c.get(DAY_OF_MONTH);
		int mois = c.get(MONTH)+1;	
		String  datedujour= date + "/" + mois + "/" +c.get(Calendar.YEAR);
		
		this.dateDInscription=datedujour;
		}
	public void AddCommentaire(Commentaire commentaire){
		this.commentaires.add(commentaire);
	}

	public List<Commentaire> getCommentaires(){
		return commentaires;
	}
	
	public void setCommentaires(List<Commentaire> commentaires){
		this.commentaires = commentaires; 
		
	}
	
	public Magazine()	{ super(); }
	public Magazine(Context context) { super(); }
	public Magazine(String nom,	float prix,	String dateDInscription, Boolean visible, ArrayList<Theme> themes) { super();
		this.nom=nom;								this.prix = prix;
		this.visible = visible;						this.themes= themes;		 this.dateDInscription=dateDInscription;
	}
	
	public int describeContents() { 	  return 0; 	     } 
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		  dest.writeLong(id);
	      dest.writeString(nom);
	      dest.writeFloat(prix);
	      dest.writeInt(visible ? 1 : 0); 
	      List<String> themestrings = new ArrayList<String>();
	      for (Theme theme : themes){
				themestrings.add(theme.name());
			}
		  dest.writeStringList(themestrings);
		  dest.writeTypedList(commentaires);
		  dest.writeString(dateDInscription);

		  }
	
	public static final Creator<Magazine> CREATOR= new Creator<Magazine>() {
		  public Magazine createFromParcel(Parcel in) {
			Magazine magazine = new Magazine();
			magazine.id = in.readLong();
			magazine.nom = in.readString();
			magazine.prix = in.readFloat();
			magazine.visible = in.readInt()>=0 ? true : false;
			List<String> themestrings = new ArrayList<String>();
			in.readStringList(themestrings);
			for (String themestring : themestrings){
				magazine.themes.add(Theme.valueOf(themestring));		
			}
			in.readTypedList(magazine.commentaires,Commentaire.CREATOR);
			magazine.dateDInscription = in.readString();
			return magazine;		    
		  }
		  	@Override
			 public Magazine[] newArray(int size) {		 return new Magazine[size];		}
		};		
		
		 @Override
	public String toString() { 
			 						return this.nom;	 
		 		}
	    
	public static Magazine cursorToMagazine(Cursor cursor) {
		        Magazine magazine = new Magazine();
		        magazine.setId(cursor.getLong(Column.ID.ordinal()));
		        magazine.setNom(cursor.getString(Column.NOM.ordinal()));
		        magazine.setPrix(cursor.getFloat(Column.PRIX.ordinal()));
		        magazine.setVisible((cursor.getInt(Column.VISIBLE.ordinal())==1)?true:false);
		        for(Theme theme : Theme.values()){
		        	int presence = cursor.getString(Column.THEMES.ordinal()).lastIndexOf(theme.name()); 		        	
		        	if (presence>=0) magazine.AddTheme(Theme.valueOf(theme.name()));     
		        }		       
		        magazine.setdateDInscription(cursor.getString(Column.DATEDINSCRIPTION.ordinal()));
		        return magazine;
		    }
	}
