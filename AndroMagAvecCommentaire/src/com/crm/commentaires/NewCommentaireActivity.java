package com.crm.commentaires;
import java.util.Iterator;

import com.crm.commentmag.*;
import com.crm.magazines.Magazine;
import com.crm.magazines.MagazineListActivity;
import com.crm.magazines.Magazine.Theme;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.os.Build;

public class NewCommentaireActivity extends Activity {
	private Magazine magazine;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_commentaire);


		
		magazine = getIntent().getParcelableExtra(Magazine.TABLE_MAGAZINE);
		this.setTitle(magazine.getNom());
		
//     		findViewById(R.id.EditNomMag); 
  //     		findViewById(R.id.ValiderCommentaire);
         	}
	
/*************************************************************************/
	
	public void onValider(View v) {
		Commentaire commentaire = new Commentaire();	
		EditText rubrique=(EditText) findViewById(R.id.newcomment_rubrique);
		commentaire.setRubrique(rubrique.getText().toString());
		EditText numero = (EditText) findViewById(R.id.newcomment_numero);
		commentaire.setNumero(Integer.valueOf(numero.getText().toString()));
		RatingBar note = (RatingBar)  findViewById(R.id.newcomment_note);
		commentaire.setNote(Float.valueOf(note.getRating()));
		EditText texte =  (EditText)  findViewById(R.id.newcomment_commentaire);
		commentaire.setTexte(texte.getText().toString());
		commentaire.setId_magazine(magazine.getId());
		magazine.AddCommentaire(commentaire);
			
		Toast.makeText(this,"Nouveau " + Commentaire.TABLE_COMMENTS + " créé",Toast.LENGTH_SHORT).show();
        try {
        	Bundle bundle = new Bundle();
        	bundle.putParcelable(Magazine.TABLE_MAGAZINE, magazine);
        	Intent data;
        if (getParent() == null) {
        		data = new Intent(this,com.crm.commentaires.CommentaireListActivity.class);
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

/*******************************************/
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_commentaire, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_commentaire,
					container, false);
			return rootView;
		}
	}

}
