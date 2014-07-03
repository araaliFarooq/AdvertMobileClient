package com.client.advert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.advert.available.ListViewAdapter;
import com.advert.available.AdvertListScreen;
import com.advert.available.FetchAvailableSchedules;
import com.menu.android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

@SuppressLint("NewApi")
public class AdvertFragment extends Activity implements AdvertListScreen,OnQueryTextListener{
	
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	public static String TARGETAREA = "targetArea";
	public static String TIMESLOT = "timeslot";
	public static String CAPACITY = "capacity";
	public static String PRICE = "price";
    public static String STATUS="status";
	FetchAvailableSchedules fetchAvailableSchedules = new FetchAvailableSchedules();
	View rootView;
	ProgressBar progressBar;
	
	
	public AdvertFragment(){}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_available);
 
       // rootView = inflater.inflate(R.layout.fragment_available, container, false);
        fetchAvailableSchedules.listScreen = this;
        //fetchAvailableSchedules.execute("http://10.0.2.2:8080/api/schedules/available");
        fetchAvailableSchedules.execute("http://172.16.4.104:8080/api/schedules/available");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
       
        
         
       // return rootView;
    }
	 @Override
	    public void displayList(ArrayList<HashMap<String, String>> arraylist) {
	    	listview = (ListView)findViewById(R.id.todoItems);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(AdvertFragment.this, arraylist);
			// Set the adapter to the ListView
			listview.setAdapter(adapter);
			listview.setTextFilterEnabled(true);
	               
	        makeProgressBarDisappear();
	    }

	    private void makeProgressBarDisappear() {
	        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
	        progressBar.setVisibility(View.INVISIBLE);
	    }
	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	     // Inflate the menu; this adds items to the action bar if it is present.
	     getMenuInflater().inflate(R.menu.main, menu);
	     SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
	           SearchView searchView = (SearchView) menu.findItem(R.id.menu_item_search).getActionView();
	    
	           searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	           searchView.setSubmitButtonEnabled(true);
	           searchView.setOnQueryTextListener(this);
	    
	           return super.onCreateOptionsMenu(menu);
	    }
	    
	    @Override
	    public boolean onQueryTextChange(String newText)
	    {
	     // this is your adapter that will be filtered
	         if (TextUtils.isEmpty(newText))
	         {
	               listview.clearTextFilter();
	           }
	         else
	         {
	        	 listview.setFilterText(newText.toString());
	           }
	            
	         return true;
	    }
	    
	    @Override
	    public boolean onQueryTextSubmit(String query) {
	     // TODO Auto-generated method stub
	     return false;
	    }

}
