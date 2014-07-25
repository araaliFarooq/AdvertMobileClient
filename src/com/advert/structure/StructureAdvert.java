
package com.advert.structure;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import com.menu.android.R;
import com.menu.android.Validation;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
private Uri mCapturedImageURI = null;
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


@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.createadvert);
	Intent i = getIntent();
	targetArea = i.getStringExtra("targetArea");
	startTime=i.getStringExtra("startTIme");
	endTime = i.getStringExtra("endTime");
	etSubject = (EditText) findViewById(R.id.etSubject);
	etDescription = (EditText) findViewById(R.id.etDescription);
	ivImage = (ImageView)findViewById(R.id.ivImage);
	btnSelectPhoto=(Button) findViewById(R.id.btnImage);
	btnPost = (Button) findViewById(R.id.btnPost);
	subject= etSubject.getText().toString();
	description = etDescription.getText().toString();
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
			SendHttpRequestTask t = new SendHttpRequestTask();
			String[] params = new String[]{url, subject,description,startTime,endTime,targetArea};
			t.execute(params);
			Toast.makeText(getApplicationContext(),"Successfully Uploaded",Toast.LENGTH_LONG).show();
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
				
				Bitmap b = BitmapFactory.decodeResource(StructureAdvert.this.getResources(), ivImage.getId());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				b.compress(CompressFormat.PNG, 0, baos);
				
				HttpClient client = new DefaultHttpClient();
				
				HttpPost post = new HttpPost(url);
				MultipartEntity multiPart = new MultipartEntity();
				multiPart.addPart("param1", new StringBody(param1));
				multiPart.addPart("param2", new StringBody(param2));
				multiPart.addPart("param3", new StringBody(param3));
				multiPart.addPart("param4", new StringBody(param4));
				multiPart.addPart("param5", new StringBody(param5));
				multiPart.addPart("file", new ByteArrayBody(baos.toByteArray(), "advertImageName.png"));					
				post.setEntity(multiPart);
				client.execute(post);
			}
			catch(Throwable t) {
				// Handle error here
		Toast.makeText(getApplicationContext(),"Advert not Uploaded",Toast.LENGTH_LONG).show();
				t.printStackTrace();
				
			}
				
			return null;
		}

		@Override
		protected void onPostExecute(String data) {			
			item.setActionView(null);
		   }			
        	}
public void openFileChooser(){  
                                       
      
     File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"AdvertExpo");
         if (!imageStorageDir.exists()) {
             imageStorageDir.mkdirs();
             Log.i("hello", "jonathan");
         }
         File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
         mCapturedImageURI = Uri.fromFile(file); // save to the private variable
         Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,null);
         galleryIntent.setType("image/*");
         galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                  Intent chooser = new Intent(Intent.ACTION_CHOOSER);
         chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);      
         chooser.putExtra(Intent.EXTRA_TITLE, "Attach Image");
         chooser.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
         Intent[] intentArray =  {cameraIntent}; 
         chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
         startActivityForResult(chooser,FILECHOOSER_RESULTCODE);

       }

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	//super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == FILECHOOSER_RESULTCODE && resultCode == Activity.RESULT_OK) {
		 Bitmap bitmap = null;
		    
		        if(data.getData()!=null)
		        {
		            try 
		            {
		            if (bitmap != null) 
		                {
		                    bitmap.recycle();
		                }

		            InputStream stream = getContentResolver().openInputStream(data.getData());
		           // bitmap = Media.getBitmap(getContentResolver(), mCapturedImageURI );
		            bitmap = BitmapFactory.decodeStream(stream);
		            stream.close();
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
    	
        // TextWatcher would let us check validation error on the fly
    	etSubject.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(etSubject);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    	
    	
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
    	Toast.makeText(getApplicationContext(),"please Attach an Image",Toast.LENGTH_LONG).show();
		  
    	}   		
    	
        return ret;
    }

}
