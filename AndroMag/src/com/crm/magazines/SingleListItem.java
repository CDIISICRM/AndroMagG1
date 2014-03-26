package com.crm.magazines;


import com.crm.commentmag.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
        prix_magazine.setText(String.valueOf(magazine.getPrix()));
        
        
    }
}