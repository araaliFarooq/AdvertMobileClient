package com.client.advert.model;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import com.menu.android.R;


@SuppressLint("SetJavaScriptEnabled")
public class AboutUs extends Activity {

	private WebView webView;
	
	final Activity activity = this;
	
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.webview);
		    
	    
		String webViewUrl = "http://192.168.205.1:8080/index";
				 
		 webView = (WebView) findViewById(R.id.webView1);   
		 
	     webView.getSettings().setJavaScriptEnabled(true);
	    
	     webView.getSettings().setLoadWithOverviewMode(true);
	    
	     webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
	     webView.setScrollbarFadingEnabled(false);
	     webView.getSettings().setBuiltInZoomControls(true);
	     webView.getSettings().setPluginState(PluginState.ON);
	     webView.getSettings().setSupportZoom(true); 
	     webView.loadUrl(webViewUrl);
	     startWebView(); 
	
	} 
	
	private void startWebView() {
	   		
		webView.setWebViewClient(new WebViewClient() {      
	    ProgressDialog progressDialog;
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {              
	        		        	
	        	if(url.contains("ExternalLinks")){ 
	        		
	        	 view.getContext().startActivity(
                 new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	        
	        	return true;
	        		
	        	} else {
	        		
	        		view.loadUrl(url); 
	                return true;
	            }
	        	  
	        }
	        
	    
	   public void onLoadResource (WebView view, String url) {

	        	if (progressDialog == null && url.contains("8080") 
	            		) {
	               // in standard case YourActivity.this
	                progressDialog = new ProgressDialog(AboutUs.this);
	                progressDialog.setMessage("Loading...");
	                progressDialog.show();
	            }
	        }
	        
	        // Called when all page resources loaded
	        public void onPageFinished(WebView view, String url) {
	        	
	            try{
	            	// Close progressDialog
		            if (progressDialog.isShowing()) {
		                progressDialog.dismiss();
		                progressDialog = null;
		            }
	            }catch(Exception exception){
	                exception.printStackTrace();
	            }
	        }
	       
	    }); 
	}
	// Open previous opened link from history on webview when back button pressed
	
		@Override
	    // Detect when the back button is pressed
	    public void onBackPressed() {
	        if(webView.canGoBack()) {
	            webView.goBack();
	        } else {
	            // Let the system handle the back button
	            super.onBackPressed();
	        }
	    }

}