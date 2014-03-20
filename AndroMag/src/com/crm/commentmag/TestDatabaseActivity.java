package com.crm.commentmag;

import java.util.List;
import java.util.Random;


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

enum codeRequetes{AJOUTER_MAGAZINE,AJOUTER_COMMENTAIRE,SUPPRIMER_COMMENTAIRE};

public class TestDatabaseActivity extends ListActivity {
	private CommentDAO datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_test_database);

		datasource = new CommentDAO(this);
		datasource.open();

		List<Comment> values = datasource.getAllComments();

		// Use the SimpleCursorAdapter to show the
		// elements in a ListView
		ArrayAdapter<Comment> adapter = new ArrayAdapter<Comment>(this,android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		ListView listview = getListView();
		listview.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view,
	                  int position, long id) {                   
	                  // selected item
	                  String magazine = ((TextView) view).getText().toString();
	                   
	                  // Launching new Activity on selecting single List Item
	                  Intent i = new Intent(getApplicationContext(), SingleListItem.class);
	                  // sending data to new activity
	                  i.putExtra("magazine", magazine);
	                  startActivity(i);
	              }
	            });
	}


	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
		Comment comment = null;
		switch (view.getId()) {
		case R.id.add:
			
			//  String product = ((TextView) view).getText().toString();
			 Intent ajoutMag = new Intent(getApplicationContext(), NewMag.class);  
             startActivityForResult(ajoutMag,codeRequetes.AJOUTER_MAGAZINE.ordinal());//	startActivityForResult(new Intent(this,NewMag.class), codeRequetes.AJOUTER_MAGAZINE.ordinal());
		
        
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			comment = datasource.createComment(comments[nextInt]);
			adapter.add(comment);
			break;/*
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				comment = (Comment) getListAdapter().getItem(0);
				datasource.deleteComment(comment);
				adapter.remove(comment);
			}
			break;
			*/
		}
		adapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	      if(resultCode==RESULT_OK && requestCode==codeRequetes.AJOUTER_MAGAZINE.ordinal()){
	    	  Magazine magazine = data.getParcelableExtra("magazine");
	    	  Magazine mag = new Magazine();
	    	  mag.createMagazine(magazine);
	//    	  listeMagazines.add(magazine);
	 //   	  mettreAJourAffichage();
	      }
	}
	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
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
        int id = item.getItemId();
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
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}