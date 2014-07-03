package com.advert.structure;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import com.menu.android.R;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class StructureAdvert extends Activity implements OnClickListener {

	private final int SELECT_FILE = 1;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	// directory name to store captured images and videos
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

	private Uri fileUri; // file url to store image/video
	EditText etSubject,etDescription;
	Button btnPost,btnSelectPhoto;
	ImageView ivImage;

	static Advert advert;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createadvert);
		
		
		etSubject = (EditText) findViewById(R.id.etSubject);
		etDescription = (EditText) findViewById(R.id.etDescription);
		ivImage = (ImageView)findViewById(R.id.ivImage);
		btnSelectPhoto=(Button) findViewById(R.id.btnImage);
		btnPost = (Button) findViewById(R.id.btnPost);
		
	   
		btnSelectPhoto.setOnClickListener(this);
		btnPost.setOnClickListener(this);
		
		
            }

	/**
	 * Checking device has camera hardware or not
	 * */
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
	public static String POST(String url, Advert advert){
		InputStream inputStream = null;
		String result = "";
		try {			
			HttpClient httpclient = new DefaultHttpClient();			
		    HttpPost httpPost = new HttpPost(url);		    
		    String json = "";		    
		    JSONObject jsonObject = new JSONObject();
		    jsonObject.accumulate("subject", advert.getSubject());
		    jsonObject.accumulate("description", advert.getDescription());
		    jsonObject.accumulate("timeslot", advert.getTimeSlot());
		    jsonObject.accumulate("image", advert.getImageUrl());
		    jsonObject.accumulate("targetArea", advert.getTargetArea());
		    json = jsonObject.toString();		     
		 
		    StringEntity stringEntity = new StringEntity(json);		  
		    httpPost.setEntity(stringEntity);		    
		      
		    httpPost.setHeader("Accept", "application/json");
		    httpPost.setHeader("Content-type", "application/json");			
			HttpResponse httpResponse = httpclient.execute(httpPost);			
			inputStream = httpResponse.getEntity().getContent();
			
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
				case R.id.btnPost:
					if(!validate()){
						Toast.makeText(getBaseContext(), "Enter all Fields!", Toast.LENGTH_LONG).show();
					}else {
					// call AsynTask to perform network operation on separate thread
					new HttpAsyncTask().execute("http://10.0.2.2:8080/adverts/ads");
					}
				break;
			    case R.id.btnImage:
			    	// Checking camera availability
					if (!isDeviceSupportCamera()) {
						Toast.makeText(getApplicationContext(),
								"Sorry! Your device doesn't support camera",
								Toast.LENGTH_LONG).show();
						// will close the app if the device does't have camera
						finish();
					}else {
			    	selectImage();	
					}
			}			
		}
	
      
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
             
        	advert = new Advert();
        	advert.setSubject(etSubject.getText().toString());
        	advert.setDescription(etDescription.getText().toString());
        	advert.setImageUrl(fileUri.getPath().toString());
        	

            return POST(urls[0],advert);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
       }
    }
	
	
	private boolean validate(){
		if(etSubject.getText().toString().trim().equals(""))
			return false;
		else if(etDescription.getText().toString().trim().equals(""))
			return false;
		else if(etSubject.getText().toString().trim().equals(""))
			return false;
		else if(ivImage.getDrawable().toString().trim().equals(""))
		      return false;
		else
			return true;	
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
	
	
	 private void selectImage() {
	       
		  final CharSequence[] items = { "Take Photo", "Choose From Gallery","Cancel" };
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Attach Photo!");
	        builder.setItems(items, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {
	                if (items[item].equals("Take Photo")) {
	                	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                	fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	                	intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	                   	// start the image capture Intent
	            		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	                    
	                } else if (items[item].equals("Choose From Gallery")) {
	                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
	                     intent.setType("image/*");
	                    startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
	                    
	                } else if (items[item].equals("Cancel")) {
	                    dialog.dismiss();
	                }
	            }
	        });
	        builder.show();
	    }
	 /*
		 * Here we store the file url as it will be null after returning from camera
		 * app
		 */
		@Override
	protected void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			// save file url in bundle as it will be null on scren orientation
			// changes
			outState.putParcelable("file_uri", fileUri);
		}

		@Override
		protected void onRestoreInstanceState(Bundle savedInstanceState) {
			super.onRestoreInstanceState(savedInstanceState);

			// get the file url
			fileUri = savedInstanceState.getParcelable("file_uri");
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// if the result is capturing Image
			if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
				if (resultCode == RESULT_OK) {
					// successfully captured the image and display it in image view
					previewCapturedImage();
				} else if (resultCode == RESULT_CANCELED) {
					// user cancelled Image capture
					Toast.makeText(getApplicationContext(),	"User cancelled image capture", Toast.LENGTH_SHORT).show();
				} else {
					// failed to capture image
					Toast.makeText(getApplicationContext(),"Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
				}
			} else if (requestCode == SELECT_FILE) {
				
				if (resultCode == RESULT_OK) {
					previewCapturedImage();
				} else if (resultCode == RESULT_CANCELED) {
					// user cancelled recording
					Toast.makeText(getApplicationContext(),"User cancelled video recording", Toast.LENGTH_SHORT).show();
				} else {
				Toast.makeText(getApplicationContext(),	"Sorry! Failed to upload Image", Toast.LENGTH_SHORT).show();
				}
			}
		}

		/*
		 * Display image from a path to ImageView
		 */
		private void previewCapturedImage() {
			try {
				ivImage.setVisibility(View.VISIBLE);
				// bimatp factory
				BitmapFactory.Options options = new BitmapFactory.Options();
				// downsizing image as it throws OutOfMemory Exception for largerimages
				options.inSampleSize = 8;
				final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),options);
				ivImage.setImageBitmap(bitmap);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}


	 

	             /**
	        	 * ------------ Helper Methods ---------------------- 
	        	 * */

	        	/*
	        	 * Creating file uri to store image
	        	 */
	        	public Uri getOutputMediaFileUri(int type) {
	        		return Uri.fromFile(getOutputMediaFile(type));
	        	}

	        	/*
	        	 * returning image 
	        	 */
	        	private static File getOutputMediaFile(int type) {

	        		// External sdcard location
	        		File mediaStorageDir = new File(
	        				Environment
	        						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	        				IMAGE_DIRECTORY_NAME);

	        		// Create the storage directory if it does not exist
	        		if (!mediaStorageDir.exists()) {
	        			if (!mediaStorageDir.mkdirs()) {
	        				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	        						+ IMAGE_DIRECTORY_NAME + " directory");
	        				return null;
	        			}
	        		}

	        		// Create a media file name
	        		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	        				Locale.getDefault()).format(new Date());
	        		File mediaFile;
	        		if (type == MEDIA_TYPE_IMAGE) {
	        			mediaFile = new File(mediaStorageDir.getPath() + File.separator
	        					+ "IMG_" + timeStamp + ".jpg");
	        		}
	        		else {
	        			return null;
	        		}

	        		return mediaFile;
	        	}

	        }

	
		


