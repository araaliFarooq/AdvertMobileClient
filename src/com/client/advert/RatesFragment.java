package com.client.advert;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.advert.available.AdvertListScreen;
import com.advert.available.FetchAvailableSchedules;
import com.advert.available.ListViewAdapter;
import com.advert.schedule.ListScheduleAdapter;
import com.menu.android.R;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

@SuppressLint("NewApi")
public class RatesFragment extends Fragment implements AdvertListScreen{
	
	
	ListView listview;
	ListScheduleAdapter adapter;
	ProgressDialog mProgressDialog;
	public static String TARGETAREA = "targetArea";
	public static String STARTTIME = "startTime";
	public static String ENDTIME = "endTime";
	public static String CAPACITY = "capacity";
	public static String PRICE = "price";
    public static String STATUS="status";
	FetchAvailableSchedules fetchAdvertSchedules = new FetchAvailableSchedules();
	View rootView;
	ProgressBar progressBar;
	
	
	public RatesFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
     rootView = inflater.inflate(R.layout.fragment_rates, container, false);
     setHasOptionsMenu(true);
     fetchAdvertSchedules.listScreen = this;
     fetchAdvertSchedules.execute("http://192.168.205.1:8080/api/schedules/schedule");
     progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);     
         
        return rootView;
    }
	
	@Override
    public void displayList(ArrayList<HashMap<String, String>> arraylist) {
    	listview = (ListView)rootView.findViewById(R.id.adItems);
		// Pass the results into ListViewAdapter.java
		adapter = new ListScheduleAdapter(getActivity(), arraylist);
		// Set the adapter to the ListView
		listview.setAdapter(adapter);
		listview.setTextFilterEnabled(true);
               
        makeProgressBarDisappear();
    }
	
	 private void makeProgressBarDisappear() {
	        ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
	        progressBar.setVisibility(View.INVISIBLE);
	    }
	@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		  inflater.inflate(R.menu.main, menu);
		 	    		  
    }
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   switch (item.getItemId()) {
	
	      case R.id.menu_item_search:
		
	         return true;
	
	      default:
	
	         return super.onOptionsItemSelected(item);
		   }
		}

	 
	
}
