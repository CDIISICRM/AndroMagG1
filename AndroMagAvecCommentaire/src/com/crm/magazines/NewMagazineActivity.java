package com.crm.magazines;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;

import com.crm.commentmag.*;
import com.crm.magazines.Magazine.Theme;
import com.crm.magazines.MagazineListActivity.*;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NewMagazineActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
   		this.setContentView(R.layout.activity_new_magazine);
         	}
	public void onValider(View v) {
		Magazine magazine = new Magazine();	
		EditText nom=(EditText) findViewById(R.id.EditNomMag);
		magazine.setNom(nom.getText().toString());
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
        	data = new Intent(this,com.crm.magazines.MagazineListActivity.class);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.commentaires, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {
	
		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_magazine,container, true);
			return rootView;
		}
	}
}