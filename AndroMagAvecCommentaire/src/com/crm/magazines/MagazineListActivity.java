package com.crm.magazines;

import java.util.List;

import com.crm.commentaires.CommentaireListActivity;
import com.crm.commentmag.*;
import com.crm.commentmag.CommentMagDBhelper.codeRequetes;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



public class MagazineListActivity extends ListActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_list_magazine);
		CommentMagDBhelper dbhelper = new CommentMagDBhelper(this);
		final List<Magazine> magazines = dbhelper.getAllMagazines();	
		ArrayAdapter<Magazine> adapter = new ArrayAdapter<Magazine>(MagazineListActivity.this,android.R.layout.simple_list_item_1, magazines);
		setListAdapter(adapter);
		ListView listview = getListView();
		listview.setOnItemClickListener(new OnItemClickListener() {
		Magazine magazine = new Magazine();
	          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	  String choix = ((TextView) view).getText().toString();
	        	  for(Magazine mag : magazines) if(mag.getNom().equals(choix)) magazine = mag;	                   
	        	  Bundle bundle = new Bundle();
	        	  bundle.putParcelable(Magazine.TABLE_MAGAZINE, magazine);
	        	  Intent i = new Intent(getApplicationContext(), CommentaireListActivity.class);
	              i.putExtras(bundle);
	              startActivity(i);
	              }
	            });
	}
	
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add:
			 Intent ajoutMag = new Intent(this, NewMagazineActivity.class);  
             startActivityForResult(ajoutMag,codeRequetes.AJOUTER_MAGAZINE.ordinal());
             
             break;
		
		case R.id.buttonValiderMagazine:
			
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	      if(resultCode==RESULT_OK && requestCode==codeRequetes.AJOUTER_MAGAZINE.ordinal()){
	    	        Bundle extras = data.getExtras();
	    	    Magazine magazine = extras.getParcelable(Magazine.TABLE_MAGAZINE);
	    		CommentMagDBhelper dbhelper = new CommentMagDBhelper(MagazineListActivity.this);
	    		dbhelper.createMagazine(magazine);
	    		Toast.makeText(this,"Nouveau " + Magazine.TABLE_MAGAZINE + ": " + magazine.getNom()+ " créé",Toast.LENGTH_LONG).show();
	    		@SuppressWarnings("unchecked")
				ArrayAdapter<Magazine> adapter = (ArrayAdapter<Magazine>) getListAdapter();
	    		adapter.add(magazine);
	    		adapter.notifyDataSetChanged();
	      }
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
 //       int id = item.getItemId();
//        if (id == R.id.action_settings) {
  //          return true;
   //     }
        return super.onOptionsItemSelected(item);
    }

    /**
* A placeholder fragment containing a simple view.
*/
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        	Fragment fragment = new Fragment();
			String s = fragment.toString();
			 s = s;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        	  
            View rootView = null;
        	//inflater.inflate(R.layout.fragment_main, container, false);
        	
            return rootView;
        }
    }

}