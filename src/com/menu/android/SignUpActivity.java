package com.menu.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity implements OnClickListener {

	Button btnSignUp;
	EditText etUserName,etPass,etPhone,etEmail;
	static NewUser newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
               
		btnSignUp = (Button) findViewById(R.id.btnSignUp);
		btnSignUp.setOnClickListener(this);
		register();
    }

    private void register() {
    	etUserName = (EditText) findViewById(R.id.etUserName);
        // TextWatcher would let us check validation error on the fly
    	etUserName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etUserName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    	
    	etPhone = (EditText) findViewById(R.id.etPhone);
        // TextWatcher would let us check validation error on the fly
    	etPhone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etPhone);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    	
    	etEmail = (EditText) findViewById(R.id.etEmail);
        // TextWatcher would let us check validation error on the fly
    	etEmail.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etEmail);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    	etPass = (EditText) findViewById(R.id.etPass);
        // TextWatcher would let us check validation error on the fly
    	etPass.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etPass);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    
    }
    
     public static String POST(String url, NewUser newUser){
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
		    jsonObject.accumulate("userName", newUser.getUserName());
		    jsonObject.accumulate("password", newUser.getPassword());
		    jsonObject.accumulate("emailAddress", newUser.getEmailAddress());
		    jsonObject.accumulate("phoneNumber", newUser.getPhoneNumber());
		    		    
		    // 4. convert JSONObject to JSON to String
		    json = jsonObject.toString();
		    
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
				result = "Failed to Create Account!";
		
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		
		// 11. return result
		return result;
	}
	
 @Override
 public void onClick(View view) {
		
		switch(view.getId()){
			case R.id.btnSignUp:
				if ( checkValidation () )
				new HttpAsyncTask().execute("http://192.168.205.1:8080/api/users");
                else
                  Toast.makeText(SignUpActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
				break;
			}
     		}
	      
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
             
        	newUser = new NewUser();
        	newUser.setUserName(etUserName.getText().toString());
        	newUser.setPassword(etPass.getText().toString());
        	newUser.setEmailAddress(etEmail.getText().toString());
        	newUser.setPhoneNumber(etPhone.getText().toString());
        	
        	return POST(urls[0],newUser);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	
        	Toast.makeText(getBaseContext(), "Account Created!", Toast.LENGTH_LONG).show();
        	Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
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
	
	private boolean checkValidation() {
        boolean ret = true;
 
        if (!Validation.hasText(etUserName)) ret = false;
        if (!Validation.isEmailAddress(etEmail, true)) ret = false;
        if (!Validation.isPhoneNumber(etPhone, false)) ret = false;
        if (!Validation.hasText(etPass)) ret = false;        
        
        return ret;
    }

	
}
   