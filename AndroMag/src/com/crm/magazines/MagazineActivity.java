package com.crm.magazines;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import com.crm.commentmag.*;
import com.crm.magazines.Magazine.Theme;
import com.crm.magazines.MagazineListActivity.*;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MagazineActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	//	this.magazine = new Magazine(this);
   		this.setContentView(R.layout.new_mag_view);
     		findViewById(R.id.EditNomMag); 
       		findViewById(R.id.buttonValiderMagazine);
         	}
	public void onValider(View v) {
		Magazine magazine = new Magazine();	
		EditText nom=(EditText) findViewById(R.id.EditNomMag);
		magazine.setNom(nom.getText().toString());
		Toast toast=Toast.makeText(this,nom.getText(),Toast.LENGTH_LONG); toast.show();
		EditText prix = (EditText)findViewById(R.id.EditPrixMag);
		magazine.setPrix(Float.valueOf(prix.getText().toString()));
        magazine.setdateDInscription();
        magazine.setVisible(true);
        CheckBox cbxJardin = (CheckBox) findViewById(R.id.checkBoxJardin);
        CheckBox cbxTV = (CheckBox) findViewById(R.id.checkBoxTV);
        CheckBox cbxMaison = (CheckBox) findViewById(R.id.checkBoxMaison); 
        CheckBox cbxMusique = (CheckBox) findViewById(R.id.checkBoxMusique);
        if (cbxJardin.isChecked()) magazine.AddTheme(Theme.Jardin);
        if (cbxTV.isChecked())  magazine.AddTheme(Theme.TV);
        if (cbxMusique.isChecked())  magazine.AddTheme(Theme.Musique);
        if (cbxMaison.isChecked())  magazine.AddTheme(Theme.Maison);
        try {
        	Bundle bundle = new Bundle();
        	bundle.putParcelable(Magazine.TABLE_MAGAZINE, magazine);
        	Intent data;
        if (getParent() == null) {
        	data = new Intent(this,MagazineListActivity.class);
        	data.putExtras(bundle);
        	this.setResult(Activity.RESULT_OK,data);
   
        	}
        else {
        	data = this.getParent().getIntent(); 
        	data.putExtras(bundle);
        	getParent().setResult(Activity.RESULT_OK, data);
        	}
    		
    			finish();
    		} catch(NumberFormatException ne ) {
    			AlertDialog.Builder mesAlert = new AlertDialog.Builder(this);
    			mesAlert.setTitle("Problème de format de prix");
    			mesAlert.setMessage("Le format du prix n'est pas correct.Veuillez corriger!");
    			mesAlert.setNeutralButton("OK",null);
                mesAlert.show();
    		}    
    }
	

}