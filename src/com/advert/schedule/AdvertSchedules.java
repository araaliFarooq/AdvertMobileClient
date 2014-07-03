package com.advert.schedule;

import com.menu.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AdvertSchedules extends Activity {
	// Declare Variables
	String targetArea;
	String timeslot;
	String capacity;
	String price;
	String status;
	TextView txttarget,txttimeslot,txtcapacity,txtprice, txtstatus;
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
		// Get the result of population
		status = i.getStringExtra("status");
		// Get the result of flag
		timeslot = i.getStringExtra("timeslot");

		// Locate the TextViews in singleitemview.xml
		txttarget = (TextView) findViewById(R.id.target);
		 txttimeslot = (TextView) findViewById(R.id.timeslot);
		txtcapacity = (TextView) findViewById(R.id.capacity);
		txtprice = (TextView) findViewById(R.id.price);
		 txtstatus = (TextView) findViewById(R.id.status);

		// Locate the ImageView in singleitemview.xml
		//ImageView imgflag = (ImageView) findViewById(R.id.advertising);

		// Set results to the TextViews
		txttarget.setText(targetArea);
		txttimeslot.setText(timeslot);
		txtcapacity.setText(capacity);
		txtprice.setText(price);
		txtstatus.setText(status);

		
	}
}