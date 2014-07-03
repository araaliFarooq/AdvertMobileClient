package com.client.advert.model;



import com.menu.android.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class AlertDialogManager {
	/**
	 * Function to display simple Alert Dialog
	 * 
	 * @param context
	 *            - application context
	 * @param title
	 *            - alert dialog title
	 * @param message
	 *            - alert message
	 * @param status
	 *            - success/failure (used to set icon) - pass null if you don't
	 *            want icon
	 * */
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		if (status != null)
			// Setting alert dialog icon
			alertDialog
					.setIcon((status) ? R.drawable.success : R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	
	  public void showNoConnectionDialog(Context ctx1) {
	        final Context ctx = ctx1;
	        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
	        builder.setCancelable(true);
	        builder.setMessage("You need a network connection to use this application. Please turn on mobile network or Wi-fi Settings.");
	        builder.setTitle("Unable to Connect");
	        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
	            }
	        });
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	 Intent startMain = new Intent(Intent.ACTION_MAIN);
	                 startMain.addCategory(Intent.CATEGORY_HOME);
	                 startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                 ctx.startActivity(startMain);
	            }
	        });
	        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	                return;
	            }
	        });

	        builder.show();
	    }
	  
	  public void showNoGPSDialog(Context ctx1) {
	        final Context ctx = ctx1;
	        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
	        builder.setCancelable(true);
	        builder.setMessage("Please Enable GPS for better functionality.");
	        builder.setTitle("GPS disabled");
	        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                ctx.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	            }
	        });
	        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	 //do nothing but proceed with activity
	            }
	        });
	        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
	            public void onCancel(DialogInterface dialog) {
	                return;
	            }
	        });

	        builder.show();
	    }
}
