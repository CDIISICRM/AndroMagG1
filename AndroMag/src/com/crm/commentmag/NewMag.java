package com.crm.commentmag;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.crm.commentmag.Themes.Theme;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
 
public class NewMag extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
         this.setContentView(R.layout.new_mag_view);
     EditText Formulaire=(EditText) findViewById(R.id.EditNomMag); 
         Button bt =(Button) findViewById(R.id.buttonValiderMagazine);
         bt.setOnClickListener(this);
         }

   

	public void onClick(View v) {
	
		Magazine magazine = new Magazine();
		EditText nom=(EditText) findViewById(R.id.EditNomMag);
		magazine.setNom(nom.getText().toString());
		
		Toast toast=Toast.makeText(this,nom.getText(),Toast.LENGTH_LONG); toast.show();
		
		EditText prix = (EditText)findViewById(R.id.EditPrixMag);
		magazine.setPrix(Float.valueOf(prix.getText().toString()));
		
       final   CheckBox cbxJardin = (CheckBox) findViewById(R.id.checkBoxJardin);
        CheckBox cbxTV = (CheckBox) findViewById(R.id.checkBoxTV);
        CheckBox cbxMaison = (CheckBox) findViewById(R.id.checkBoxMaison); 
        CheckBox cbxMusique = (CheckBox) findViewById(R.id.checkBoxMusique);
        
        if (cbxJardin.isChecked()) magazine.AddTheme(Theme.Jardin);
        if (cbxTV.isChecked())  magazine.AddTheme(Theme.TV);
        if (cbxMusique.isChecked())  magazine.AddTheme(Theme.Musique);
        if (cbxMaison.isChecked())  magazine.AddTheme(Theme.Maison);  
         magazine.setdateDInscription(new Date());
        
        magazine.setVisible(true);
        
        try
    	{

    		Intent intent= getIntent();
    		intent.putExtra("magazine", magazine);

    		setResult(RESULT_OK, intent);
    		finish();
    	}
    	catch(NumberFormatException ne )
    	{

         AlertDialog.Builder mesAlert = new AlertDialog.Builder(this);
         
         mesAlert.setTitle("Problème de format de prix");
         mesAlert.setMessage("Le format du prix n'est pas correct.Veuillez corriger!");

         mesAlert.setNeutralButton("OK",null);
        

         mesAlert.show();

    	}
        
    
        
    }
	
}