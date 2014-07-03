package com.menu.android;

import com.client.advert.model.AlertDialogManager;
import com.client.advert.model.ConnectionDetector;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartPage extends Activity {
	
	Button btnSignUp,btnSignIn;
	TextView tvIsConnected;
	AlertDialogManager alert;
	ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		btnSignUp = (Button)findViewById(R.id.btnSignUp);
		btnSignIn = (Button)findViewById(R.id.btnSignIn);
		tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
		
		alert = new AlertDialogManager();
		  
		cd = new ConnectionDetector(getApplicationContext());

		if (!cd.isConnectingToInternet() && !isConnected()) {
				
         alert.showAlertDialog(StartPage.this, "Internet Connection Error",
		"Please connect to working Internet connection", false);
          tvIsConnected.setText("No Internet Connection");
				
				//return;
			}
		else{
			tvIsConnected.setBackgroundColor(0xFF00CC00);
			tvIsConnected.setText("You Are Connected to Internet");
				}
		
		btnSignUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent signup = new Intent(StartPage.this, SignUpActivity.class);
				StartPage.this.startActivity(signup);
				
			}
		});
		
		btnSignIn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent signin = new Intent(StartPage.this, SignInActivity.class);
				StartPage.this.startActivity(signin);
			}
		});
		
	}
	 public boolean isConnected(){
	    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
	    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    	    if (networkInfo != null && networkInfo.isConnected()) 
	    	    	return true;
	    	    else
	    	    	return false;	
	    }

	

}
