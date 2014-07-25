package com.menu.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.client.advert.MainMenu;
import com.client.advert.model.Home;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class SignInActivity extends Activity implements OnClickListener {

    EditText etUserName, etPass;
	TextView error;
	Button btnSignIn;
	private String resp;
	private String errorMsg;
	static User user;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in_screen);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPass = (EditText) findViewById(R.id.etPass);
		btnSignIn = (Button) findViewById(R.id.btnSignIn);
		error = (TextView) findViewById(R.id.tv_error);

		   login();
		btnSignIn.setOnClickListener(this);
	}
	 public static String POST(String url, User user){
			InputStream inputStream = null;
			String result = "";
			try {
				
				// 1. create HttpClient
				HttpClient httpclient = new DefaultHttpClient();
				
				// 2. make POST request to the given URL
			    HttpPost httpPost = new HttpPost(url);
			    
			    String json = "";
			    

			    // 3. build jsonObject
			    JSONObject jsonObject = new JSONObject();
			    jsonObject.accumulate("userName", user.getUserName());
			    jsonObject.accumulate("password", user.getPassword());
			    		    
			    // 4. convert JSONObject to JSON to String
			    json = jsonObject.toString();

			    
			    // ** Alternative way to convert Person object to JSON string usin Jackson Lib 
			    // ObjectMapper mapper = new ObjectMapper();
			    // json = mapper.writeValueAsString(person); 
			    
			    // 5. set json to StringEntity
			    StringEntity se = new StringEntity(json);
			    
			    // 6. set httpPost Entity
			    httpPost.setEntity(se);
			    
			    // 7. Set some headers to inform server about the type of the content   
			    httpPost.setHeader("Accept", "application/json");
			    httpPost.setHeader("Content-type", "application/json");
			    
				// 8. Execute POST request to the given URL
				HttpResponse httpResponse = httpclient.execute(httpPost);
				
				// 9. receive response as inputStream
				inputStream = httpResponse.getEntity().getContent();
				
			    
				// 10. convert inputstream to string
				if(inputStream != null)
					result = convertInputStreamToString(inputStream);
				else
					result = "Did not work!";
			
			} catch (Exception e) {
				Log.d("InputStream", e.getLocalizedMessage());
			}
			
			// 11. return result
			return result;
		}
		
		 @Override
			public void onClick(View view) {
			
				switch(view.getId()){
					case R.id.btnSignIn:
						if(checkValidation()){
						Intent signin = new Intent(SignInActivity.this, MainMenu.class);
						SignInActivity.this.startActivity(signin);
						}
						else{
					Toast.makeText(SignInActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
						}
					break;
				}
				
			}
		
	    public boolean isConnected(){
	    	ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
	    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    	    if (networkInfo != null && networkInfo.isConnected()) 
	    	    	return true;
	    	    else
	    	    	return false;	
	    }
	   
	    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
	        @Override
	        protected String doInBackground(String... urls) {
	             
	        	user = new User();
	        	user.setUserName(etUserName.getText().toString());
	        	user.setPassword(etPass.getText().toString());
	        	return POST(urls[0],user);
	        }
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	        	Toast.makeText(getBaseContext(), "You are Logged in!", Toast.LENGTH_LONG).show();
	       }
	    }
		
		private static String convertInputStreamToString(InputStream inputStream) throws IOException{
	        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	        String line = "";
	        String result = "";
	        while((line = bufferedReader.readLine()) != null)
	            result += line;
	        
	        inputStream.close();
	        return result;
	        
	    }
		 private void login() {
		    	
		        // TextWatcher would let us check validation error on the fly
		    	etUserName.addTextChangedListener(new TextWatcher() {
		            public void afterTextChanged(Editable s) {
		                Validation.hasText(etUserName);
		            }
		            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		            public void onTextChanged(CharSequence s, int start, int before, int count){}
		        });
		    	
		    	 // TextWatcher would let us check validation error on the fly
		    	etPass.addTextChangedListener(new TextWatcher() {
		            public void afterTextChanged(Editable s) {
		                Validation.hasText(etPass);
		            }
		            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		            public void onTextChanged(CharSequence s, int start, int before, int count){}
		        });
		 }
		
		private boolean checkValidation() {
	        boolean ret = true;
	 
	        if (!Validation.hasText(etUserName)) ret = false;
	        if (!Validation.hasText(etPass)) ret = false;        
	        
	        return ret;
	    }
}
