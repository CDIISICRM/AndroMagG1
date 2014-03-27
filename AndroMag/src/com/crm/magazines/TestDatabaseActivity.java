package com.crm.magazines;

import java.util.List;

import com.crm.commentmag.*;

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

enum codeRequetes{AJOUTER_MAGAZINE,DETAIL_COMMENTAIRE,MASQUER_MAGAZINE};

public class TestDatabaseActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_test_database);
		MagazineDBhelper dbhelper = new MagazineDBhelper(this);
		List<Magazine> values = dbhelper.getAllMagazines();	
		ArrayAdapter<Magazine> adapter = new ArrayAdapter<Magazine>(TestDatabaseActivity.this,android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
		ListView listview = getListView();
		listview.setOnItemClickListener(new OnItemClickListener() {
	          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {                   
	                  String magazine = ((TextView) view).getText().toString();
	                  Intent i = new Intent(getApplicationContext(), SingleListItem.class);
	                  i.putExtra("magazine", magazine);
	                  startActivity(i);
	              }
	            });
	}


	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.add:
			 Intent ajoutMag = new Intent(this, MagazineActivity.class);
		  
             startActivityForResult(ajoutMag,codeRequetes.AJOUTER_MAGAZINE.ordinal());
             
             //	startActivityForResult(new Intent(this,NewMag.class), codeRequetes.AJOUTER_MAGAZINE.ordinal());
        
             //		String[] comments = new String[] { "Cool", "Very nice", "Hate it" };			int nextInt = new Random().nextInt(3);
			// 		comment = datasource.createComment(comments[nextInt]);			adapter.add(comment);		
             break;
		
		case R.id.buttonValiderMagazine:
			
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	      if(resultCode==RESULT_OK && requestCode==codeRequetes.AJOUTER_MAGAZINE.ordinal()){
	    	
	    	  
	    	       
	    	    //    String breweryID = 
	    	        final String MAGAZINE = "magazine";
	    	        Bundle extras = data.getExtras();
	    	    Magazine magazine = extras.getParcelable(MAGAZINE);
	    		MagazineDBhelper dbhelper = new MagazineDBhelper(TestDatabaseActivity.this);
	    		dbhelper.createMagazine(magazine);
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
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}