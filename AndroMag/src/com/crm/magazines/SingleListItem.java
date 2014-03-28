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
        
			
		
    }
    
    public void onRetour(View v){
		Intent i=new Intent(v.getContext(),MagazineListActivity.class);
		startActivity(i);
	}
    public void onCommenter(View v){
		Intent i=new Intent(v.getContext(),com.crm.commentaires.CommentairesActivity.class);
		startActivity(i);
	}

    
}