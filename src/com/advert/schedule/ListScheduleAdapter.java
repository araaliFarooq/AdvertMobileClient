package com.advert.schedule;

import java.util.ArrayList;
import java.util.HashMap;

import com.client.advert.AdvertFragment;
import com.client.advert.RatesFragment;
import com.menu.android.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListScheduleAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	
	
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ListScheduleAdapter(Context context,
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
		TextView txtstartTime;
		TextView txtendTime;
		TextView txtcapacity;
		TextView txtprice;
		TextView txtstatus;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.listview_rate, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		txttarget = (TextView)itemView.findViewById(R.id.target);
		txtstartTime = (TextView)itemView.findViewById(R.id.startTime);
		txtendTime = (TextView)itemView.findViewById(R.id.endTime);
		txtcapacity = (TextView)itemView.findViewById(R.id.capacity);
		txtprice = (TextView)itemView.findViewById(R.id.price);
		txtstatus = (TextView) itemView.findViewById(R.id.status);
		 
		// Capture position and set results to the TextViews
		 txttarget.setText(resultp.get(AdvertFragment.TARGETAREA));
		 txtstartTime.setText(resultp.get(AdvertFragment.STARTTIME));
		 txtendTime.setText(resultp.get(AdvertFragment.ENDTIME));
		 txtcapacity.setText(resultp.get(AdvertFragment.CAPACITY));
		 txtprice.setText(resultp.get(AdvertFragment.PRICE));
		 txtstatus.setText(resultp.get(AdvertFragment.STATUS));
		
		
		itemView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, AdvertSchedules.class);
				// Pass all data rank
				intent.putExtra("targetArea", resultp.get(RatesFragment.TARGETAREA));
				// Pass all data country
				intent.putExtra("startTime", resultp.get(RatesFragment.STARTTIME));
				intent.putExtra("endTime", resultp.get(RatesFragment.ENDTIME));
				// Pass all data population
				intent.putExtra("capacity",resultp.get(RatesFragment.CAPACITY));
				// Pass all data flag
				intent.putExtra("price", resultp.get(RatesFragment.PRICE));
				intent.putExtra("status", resultp.get(RatesFragment.STATUS));
				// Start SingleItemView Class
				context.startActivity(intent);
			}
		});
		return itemView;
	}
}
