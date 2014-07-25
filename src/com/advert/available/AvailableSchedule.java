package com.advert.available;

import com.advert.structure.StructureAdvert;
import com.menu.android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AvailableSchedule extends Activity {
	// Declare Variables
	String targetArea;
	String startTime;
	String endTime;
	String capacity;
	String price;
	String status;
	TextView txttarget,txttime,txtstartTime,txtendTime,txtcapacity,txtprice, txtstatus;
	Button btnAdvert;	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.singleitemview);

		Intent i = getIntent();
		// Get the result of rank
		targetArea = i.getStringExtra("targetArea");
		// Get the result of country
		price = i.getStringExtra("price");
		// Get the result of population
		status = i.getStringExtra("status");
		// Get the result of flag
		startTime=i.getStringExtra("startTime");
		endTime = i.getStringExtra("endTime");
		capacity=i.getStringExtra("capacity");

		// Locate the TextViews in singleitemview.xml
		txttarget = (TextView)findViewById(R.id.target);
		txtstartTime = (TextView) findViewById(R.id.startTime);
		txtendTime = (TextView) findViewById(R.id.endTime);
		txtcapacity = (TextView) findViewById(R.id.capacity);
		txtprice = (TextView) findViewById(R.id.price);
		txtstatus = (TextView) findViewById(R.id.status);
		btnAdvert =(Button) findViewById(R.id.btnAdvert);

		
		txttarget.setText(targetArea);
		txtstartTime.setText(startTime);
		txtendTime.setText(endTime);
		txtcapacity.setText(capacity);
		txtprice.setText(price);
		txtstatus.setText(status);
		btnAdvert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent advert = new Intent(AvailableSchedule.this, StructureAdvert.class);
				// Pass all data rank
				
				advert.putExtra("targetArea",targetArea);
				// Pass all data country
				advert.putExtra("startTime", startTime);
				advert.putExtra("endTime", endTime);	
				AvailableSchedule.this.startActivity(advert);
			}
		});

		
	}
}