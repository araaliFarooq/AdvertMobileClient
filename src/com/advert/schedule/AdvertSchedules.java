package com.advert.schedule;

import com.menu.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class AdvertSchedules extends Activity {
	// Declare Variables
	String targetArea;
	String startTime;
	String endTime;
	String capacity;
	String price;
	String status;
	TextView txttarget, txtstartTime,txtendTime,txtcapacity,txtprice, txtstatus;
	//ImageLoader imageLoader = new ImageLoader(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singlerates);

		Intent i = getIntent();
		// Get the result of rank
		targetArea = i.getStringExtra("targetArea");
		// Get the result of country
		price = i.getStringExtra("price");
		capacity=i.getStringExtra("capacity");
		// Get the result of population
		status = i.getStringExtra("status");
		// Get the result of flag
		startTime = i.getStringExtra("startTime");
		endTime = i.getStringExtra("endTime");

		// Locate the TextViews in singleitemview.xml
		txttarget = (TextView) findViewById(R.id.target);
		 txtstartTime= (TextView) findViewById(R.id.startTime);
		 txtendTime = (TextView) findViewById(R.id.endTime);
		txtcapacity = (TextView) findViewById(R.id.capacity);
		txtprice = (TextView) findViewById(R.id.price);
		 txtstatus = (TextView) findViewById(R.id.status);

		// Locate the ImageView in singleitemview.xml
		//ImageView imgflag = (ImageView) findViewById(R.id.advertising);

		// Set results to the TextViews
		txttarget.setText(targetArea);
		txtstartTime.setText(startTime);
		txtendTime.setText(endTime);
		txtcapacity.setText(capacity);
		txtprice.setText(price);
		txtstatus.setText(status);

		
	}
}