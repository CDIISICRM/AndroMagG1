package com.crm.commentaires;



import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.crm.magazines.Magazine;
import com.crm.magazines.MagazineListActivity;
import com.crm.magazines.NewMagazineActivity;
import com.crm.commentmag.CommentMagDBhelper;
import com.crm.commentmag.R;
import com.crm.commentmag.CommentMagDBhelper.*;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.os.Build;

public class CommentaireListActivity extends ListActivity {
	private Magazine magazine;
	private Commentaire commentaire;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_list_commentaire);
		
		magazine = getIntent().getParcelableExtra(Magazine.TABLE_MAGAZINE);
		final List<Commentaire> commentaires = magazine.getCommentaires();
		this.setTitle(magazine.getNom());
		
		TextView prix_magazine = (TextView) findViewById(R.id.prix_magazine);
		TextView theme_magazine = (TextView) findViewById(R.id.theme_magazine);
		TextView date_inscription = (TextView) findViewById(R.id.date_inscription);

		 
	     prix_magazine.setText(String.valueOf(magazine.getPrix())+" €");
	     date_inscription.setText(magazine.getDateDInscription()); 
	     theme_magazine.setText(magazine.getThemes());
	        
		Button commenter = (Button)findViewById(R.id.commenter);
		Button retour = (Button)findViewById(R.id.retour);
		Button masquer = (Button)findViewById(R.id.masquer);
		
		CommentMagDBhelper dbhelper = new CommentMagDBhelper(this);
		
		ArrayAdapter<Commentaire> adapter = new ArrayAdapter<Commentaire>(CommentaireListActivity.this,android.R.layout.simple_expandable_list_item_1, magazine.getCommentaires());
		setListAdapter(adapter);
		ListView listview = getListView();
	/*	listview.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {  
/*
	        	  String choix = ((TextView) view).getText().toString();
	        	  for(Commentaire comment : commentaires) if(comment.getTexte().equals(choix)) commentaire = comment;	                   
	        	  Bundle bundle = new Bundle();
	        	  bundle.putParcelable(Commentaire.TABLE_COMMENTS, commentaire);
	        	  Intent i = new Intent(getApplicationContext(), CommentaireListActivity.class);
	              i.putExtras(bundle);
	              startActivity(i);
	              
	              }
	            });
	            */
		
		commenter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				  Bundle bundle = new Bundle();
	        	  bundle.putParcelable(Magazine.TABLE_MAGAZINE, magazine);
	              Intent commenter = new Intent(view.getContext(), NewCommentaireActivity.class);
	              commenter.putExtras(bundle);          
	              startActivityForResult(commenter,codeRequetes.AJOUT_COMMENTAIRE.ordinal());
	             
			}

		});	

		retour.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent retour = new Intent(view.getContext(), com.crm.magazines.MagazineListActivity.class);
				startActivity(retour);

			}

		});
	}

	public void onMasquer(View view) {
		NewMagazineActivity magazineactivity = new NewMagazineActivity();
		CommentMagDBhelper DBhelper = new CommentMagDBhelper(this); 
		int n = DBhelper.updateMasqueOn(magazine);
		if (n==1)	Toast.makeText(this,"Masquage du " + Magazine.TABLE_MAGAZINE + ": " + magazine.getNom()+ " effectué",Toast.LENGTH_LONG).show();

		Intent masquer = new Intent(view.getContext(), com.crm.magazines.MagazineListActivity.class);
		startActivity(masquer);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	      if(resultCode==RESULT_OK && requestCode==codeRequetes.AJOUT_COMMENTAIRE.ordinal()){
	    	    Bundle extras = data.getExtras();
	    	    Magazine magazine = extras.getParcelable(Magazine.TABLE_MAGAZINE);
	    		CommentMagDBhelper dbhelper = new CommentMagDBhelper(CommentaireListActivity.this);
	    
	    		dbhelper.createComment(magazine);
	    		Toast.makeText(this,"Nouveau " + Commentaire.TABLE_COMMENTS +" créé",Toast.LENGTH_SHORT).show();
	    	
	    		@SuppressWarnings("unchecked")
				ArrayAdapter<Commentaire> adapter = (ArrayAdapter<Commentaire>) getListAdapter();
	    		int size = magazine.getCommentaires().size();
	    		adapter.add(magazine.getCommentaires().get(size-1));
	    		adapter.notifyDataSetChanged();
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
	
		public PlaceholderFragment() {
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_commentaires,container, false);
			return rootView;
		}
	}
	
}
