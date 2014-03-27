package com.crm.magazines;


import com.crm.commentmag.*;
import com.crm.magazines.MagazineListActivity.codeRequetes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
 
public class SingleListItem extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_list_item_view);
         
    
        Intent i = getIntent();
        Magazine magazine = (Magazine) i.getParcelableExtra(Magazine.TABLE_MAGAZINE);
       this.setTitle(magazine.getNom());
 
       TextView nom_magazine = (TextView) findViewById(R.id.nom_magazine);
       TextView prix_magazine = (TextView) findViewById(R.id.prix_magazine);
       nom_magazine.setText(magazine.getDateDInscription());
        prix_magazine.setText(String.valueOf(magazine.getPrix())+" €");
        
		Button oui = (Button)findViewById(R.id.commenter);
		Button non = (Button)findViewById(R.id.retour);
		Button jamais = (Button)findViewById(R.id.masquer);
		
		oui.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), com.crm.commentaires.CommentairesActivity.class);
				startActivity(intent);
			}
	    	
	    });	
		/*
		non.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent data;
			
	       //      startActivityForResult(ajoutMag,codeRequetes.AJOUTER_MAGAZINE.ordinal());
	        
		        //	data = new Intent(this,MagazineListActivity.class);
			//	Intent intent = new Intent(v.getContext(), com.crm.magazines.TestDatabaseActivity.class);
				startActivity(data);
				
			}
	    	
	    });*/
		
		jamais.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(v.getContext(), com.crm.magazines.TestDatabaseActivity.class);
				startActivity(intent);
			}
	    	
	    });	
    }
}