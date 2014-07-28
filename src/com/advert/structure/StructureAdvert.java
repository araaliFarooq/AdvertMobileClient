
package com.advert.structure;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.client.advert.MainMenu;
import com.menu.android.R;
import com.menu.android.Validation;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class StructureAdvert extends Activity implements OnClickListener {
private MenuItem item;

private String url = "http://192.168.205.1:8080/api/adverts";
private static final int FILECHOOSER_RESULTCODE   = 2888;
ByteArrayOutputStream baos;
Bitmap advertImage;
EditText etSubject,etDescription;
Button btnPost,btnSelectPhoto;
ImageView ivImage;
String targetArea;
String startTime;
String endTime;
String description;
String subject;
Bitmap bitmap;
String imageName;


@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.createadvert);
	Intent i = getIntent();
	targetArea = i.getStringExtra("targetArea");
	startTime=i.getStringExtra("startTime");
	endTime = i.getStringExtra("endTime");
	Log.i("targetArea", targetArea);
	Log.i("startTime", startTime);
	Log.i("endTime", endTime);
	
	
	ivImage = (ImageView)findViewById(R.id.ivImage);
	btnSelectPhoto=(Button) findViewById(R.id.btnImage);
	btnPost = (Button) findViewById(R.id.btnPost);
	
	btnSelectPhoto.setOnClickListener(this);
	btnPost.setOnClickListener(this);
	formulate();
}
@Override
	public void onClick(View view) {
	
	switch(view.getId()){
		case R.id.btnPost:
			item.setActionView(R.layout.progress);
			if(checkValidation()){
			subject= etSubject.getText().toString();
			description = etDescription.getText().toString();
			SendHttpRequestTask t = new SendHttpRequestTask();
		
			String[] params = new String[]{url, subject,description,startTime,endTime,targetArea};
			t.execute(params);
					}else{
		    Toast.makeText(StructureAdvert.this, "Form contains error", Toast.LENGTH_LONG).show();
			}
			break;
	    case R.id.btnImage:
	    	  if (!isDeviceSupportCamera()) {
				Toast.makeText(getApplicationContext(),"Sorry! Your device doesn't support camera",Toast.LENGTH_LONG).show();
				finish();
			}else {
				openFileChooser();
				
			}
	    	  }			
            	}	
  
 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		item = menu.getItem(0);
		return true;
	}

	private class SendHttpRequestTask extends AsyncTask<String, Void, String> {

		
		@Override
		protected String doInBackground(String... params) {
			
			String url = params[0];
			String param1 = params[1];
			String param2 = params[2];
			String param3 = params[3];
			String param4 = params[4];
			String param5 = params[5];
			try {
				
				bitmap=BitmapFactory.decodeFile("sdcard/AdvertExpo/"+imageName);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.JPEG, 100, baos);
				
				HttpClient client = new DefaultHttpClient();	
				HttpPost post = new HttpPost(url);
				MultipartEntity multiPart = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				multiPart.addPart("subject", new StringBody(param1));
				multiPart.addPart("description", new StringBody(param2));
				multiPart.addPart("startTime", new StringBody(param3));
				multiPart.addPart("endTime", new StringBody(param4));
				multiPart.addPart("targetArea", new StringBody(param5));
				multiPart.addPart("image", new ByteArrayBody(baos.toByteArray(),imageName));					
				post.setEntity(multiPart);
				client.execute(post);
        		
			}
			catch(Throwable t) {
				
				t.printStackTrace();
				
			}
				
			return null;
		}

		@Override
		protected void onPostExecute(String data) {			
			item.setActionView(null);
		Toast.makeText(getApplicationContext(),"Advert Successfully Uploaded",Toast.LENGTH_LONG).show();
		Intent afterUpload=new Intent(StructureAdvert.this,MainMenu.class);
		StructureAdvert.this.startActivity(afterUpload);

		   }			
        	}
public void openFileChooser(){  
                                
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
        galleryIntent.setType("image/*");
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);      
        chooser.putExtra(Intent.EXTRA_TITLE, "Attach Image");
         Intent[] intentArray =  {cameraIntent}; 
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
        startActivityForResult(chooser,FILECHOOSER_RESULTCODE);

       }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	SaveImage saveImage=new SaveImage();
	if (requestCode == FILECHOOSER_RESULTCODE && resultCode == Activity.RESULT_OK) {
		 		    
		   if(data.getData()!=null)
		        {
		       try 
		            {
		     if (bitmap != null) 
		                {
		             bitmap.recycle();
		                }

		 InputStream stream = getContentResolver().openInputStream(data.getData());
		 bitmap = BitmapFactory.decodeStream(stream);
		 stream.close();
		 imageName= saveImage.storedImageAdvert(this, bitmap);
		 ivImage.setImageBitmap(bitmap);
		 ivImage.setVisibility(View.VISIBLE);
		            }

		        catch (FileNotFoundException e) 
		            {
		                e.printStackTrace();
		            }

		        catch (IOException e) 
		            {
		                e.printStackTrace();
		            }
		        } 

		        else 
		        {
		            bitmap=(Bitmap) data.getExtras().get("data"); 
		            imageName=saveImage.storedImageAdvert(this, bitmap);
		            ivImage.setImageBitmap(bitmap);
		            ivImage.setVisibility(View.VISIBLE);
		            
		        }
		        super.onActivityResult(requestCode, resultCode, data);
		    }
     	}

    private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

    private void formulate() {
    	
    	etSubject = (EditText) findViewById(R.id.etSubject);
    	// TextWatcher would let us check validation error on the fly
    	etSubject.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etSubject);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    	
    	
    	etDescription = (EditText) findViewById(R.id.etDescription);
    	// TextWatcher would let us check validation error on the fly
    	etDescription.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etDescription);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });   	
    }
    
    private boolean checkValidation() {
        boolean ret = true;
 
        if (!Validation.hasText(etSubject)) ret = false;
        
        if (!Validation.hasText(etDescription)) ret = false;  
        
        boolean hasDrawable = (ivImage.getDrawable() != null);
    	if(!hasDrawable) {
    	Toast.makeText(getApplicationContext(),"please Attach an Image Of your Product",Toast.LENGTH_LONG).show();
		  
    	}   		
    	
        return ret;
    }

}
