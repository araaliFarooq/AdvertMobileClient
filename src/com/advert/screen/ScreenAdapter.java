package com.advert.screen;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.advert.AdvertFragment;
import com.client.advert.ScreenFragment;
import com.menu.android.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScreenAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	
	
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ScreenAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView txttarget;
		TextView  txtlocation;
		
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_screen, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		txttarget = (TextView)itemView.findViewById(R.id.target);
		 txtlocation = (TextView)itemView.findViewById(R.id.location);
	
		 
		// Capture position and set results to the TextViews
		 txttarget.setText(resultp.get(ScreenFragment.TARGETAREA));
		 txtlocation.setText(resultp.get(ScreenFragment.LOCATION));
		return itemView;	
		
		
	}
}
