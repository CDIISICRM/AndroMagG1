package com.crm.commentaires;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.crm.magazines.Magazine;



public class Commentaire implements Parcelable{

	private long id;
	private String rubrique;
	private int numero;
	private float note;
	private String texte;
	private long id_magazine;
	
	public static enum  Column {ID, RUBRIQUE, NUMERO, NOTE, TEXTE, ID_MAGAZINE};
	public static final String TABLE_COMMENTS = "commentaires";


	public static final String COMMENTAIRES_CREATE = "create table "
	           + TABLE_COMMENTS + "( " 
	           + Commentaire.Column.ID  + " integer primary key autoincrement, "
	           + Commentaire.Column.RUBRIQUE + " text, "
	           + Commentaire.Column.NUMERO + " integer, "
	           + Commentaire.Column.NOTE + " real, "
	           + Commentaire.Column.TEXTE  + " text, "
	           + Commentaire.Column.ID_MAGAZINE + " long, "
	           + "FOREIGN KEY("+ Commentaire.Column.ID_MAGAZINE+") REFERENCES " + Magazine.TABLE_MAGAZINE + "("+ Commentaire.Column.ID+"));";
	   
	public static final String[] allColumns = { Column.ID.name(), Column.RUBRIQUE.name(), Column.NUMERO.name(), 
												Column.NOTE.name(), Column.TEXTE.name(), Column.ID_MAGAZINE.name() };
 
	public long getId() {				return id;		}

	public void setId(long id) {		this.id = id;	}
	

	public String getRubrique() {		return rubrique;	}

	public void setRubrique(String rubrique) {		this.rubrique = rubrique;	}

	public int getNumero() {		return numero;	}

	public void setNumero(int numero) {		this.numero = numero;	}

	public float getNote() {		return this.note;	}

	public void setNote(Float note) {		this.note = note;	}
	
	public String getTexte() {			return texte;	}

	public void setTexte(String texte) {	this.texte = texte;	}
	
	public long getId_magazine() {		return id_magazine;	}

	public void setId_magazine(long id_commentaire) {		this.id_magazine = id_commentaire;	}



	public Commentaire()	{ super(); }
	public Commentaire(Context context) { super(); }
	public Commentaire(long id, String rubrique,	int numero,	int note, String texte, long id_magazine) {	  super();
		this.id = id;  							this.rubrique = rubrique;					this.numero = numero;
		this.note = note;						this.texte= texte;		 					this.id_magazine=id_magazine;
	}
	
	@Override
	public int describeContents() { 	  return 0; 	     } 
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		  dest.writeLong(id);
	      dest.writeString(rubrique);
	      dest.writeInt(numero);
	      dest.writeFloat(note);
	      dest.writeString(texte);    	      
		  dest.writeLong(id_magazine);
	}	
	
	public static final Creator<Commentaire> CREATOR= new Creator<Commentaire>() {
		  public Commentaire createFromParcel(Parcel in) {
			Commentaire commentaire = new Commentaire();
			commentaire.id = in.readLong();
			commentaire.rubrique = in.readString();
			commentaire.numero = in.readInt();
			commentaire.note = in.readFloat();
			commentaire.texte = in.readString();
			commentaire.id_magazine = in.readLong();
				 return commentaire;
		  }
		  	@Override
			 public Commentaire[] newArray(int size) {		 return new Commentaire[size];		}
		};		
		
	public static Commentaire cursorToCommentaire(Cursor cursor) {
		        Commentaire commentaire = new Commentaire();
		        commentaire.setId(cursor.getLong(Column.ID.ordinal()));
		        commentaire.setRubrique(cursor.getString(Column.RUBRIQUE.ordinal()));
		        commentaire.setNumero(cursor.getInt(Column.NUMERO.ordinal()));
		        commentaire.setNote(cursor.getFloat(Column.NOTE.ordinal()));	       
		        commentaire.setTexte(cursor.getString(Column.TEXTE.ordinal()));
		        commentaire.setId_magazine(cursor.getLong(Column.ID_MAGAZINE.ordinal()));
		        return commentaire;
		    }
	
	@Override
	public String toString() {
		return rubrique + " : "+ String.valueOf(numero) + " : " + texte + " : " + String.valueOf(note);	
		}
}