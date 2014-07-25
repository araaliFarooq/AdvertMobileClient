package com.client.advert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.advert.available.AdvertListScreen;
import com.advert.available.FetchAvailableSchedules;
import com.advert.schedule.ListScheduleAdapter;
import com.advert.screen.FetchScreens;
import com.advert.screen.ScreenAdapter;
import com.menu.android.R;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;


@SuppressLint("NewApi")
public class ScreenFragment extends Fragment implements AdvertListScreen  {

	ListView listview;
	ScreenAdapter adapter;
	ProgressDialog mProgressDialog;
	public static String TARGETAREA = "targetArea";
	public static String LOCATION = "location";
	FetchScreens fetchScreens = new FetchScreens();
	View rootView;
	ProgressBar progressBar;
	
	
	
	public ScreenFragment(){}
	
	ListView listView;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.fragment_screens, container, false);
        setHasOptionsMenu(true);
        fetchScreens.listScreen = this;
        fetchScreens.execute("http://192.168.205.1:8080/api/screens");
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);     
            
           
        
        
        return rootView;
    }
	@Override
    public void displayList(ArrayList<HashMap<String, String>> arraylist) {
    	listview = (ListView)rootView.findViewById(R.id.adScreens);
    	
		// Pass the results into ListViewAdapter.java
		adapter = new ScreenAdapter(getActivity(), arraylist);
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
