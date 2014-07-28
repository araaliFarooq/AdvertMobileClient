package com.advert.structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class SaveImage {
	private Context TheThis;
	private String NameOfFolder="/AdvertExpo";
	private String NameOfFile="/AdvertisedImage";
	
	public String storedImageAdvert(Context context,Bitmap ImageToSave){
		TheThis=context;
		String file_path=Environment.getExternalStorageDirectory().getAbsolutePath()+NameOfFolder;
		String CurrentDateAndTime=getCurrentDateAndTime();
		File dir=new File(file_path);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file= new File(dir,NameOfFile+CurrentDateAndTime+".jpg");
		try{
			FileOutputStream fOut=new FileOutputStream(file);
			ImageToSave.compress(Bitmap.CompressFormat.JPEG,85,fOut);
			fOut.flush();
			fOut.close();
			makeSureFileWasCreatedThenMakeAvailabe(file);
			ableToSave();
		}
		catch(FileNotFoundException e){
			unAbleToSave();
		}
		catch(IOException e){
			unAbleToSave();
		}
		
		return NameOfFile+CurrentDateAndTime+".jpg";
	}
	private void ableToSave() {
		Toast.makeText(TheThis,"Advert Picture Saved", Toast.LENGTH_SHORT).show();
		
	}
	private void unAbleToSave() {
		Toast.makeText(TheThis,"Advert Picture not saved",Toast.LENGTH_SHORT).show();
		
	}
	private String getCurrentDateAndTime() {
		Calendar c=Calendar.getInstance();
		SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String formattedDate=df.format(c.getTime());
		
		return formattedDate;
	}
	private void makeSureFileWasCreatedThenMakeAvailabe(File file){
		
		MediaScannerConnection.scanFile(TheThis, new String[]{file.toString()},null,
				new MediaScannerConnection.OnScanCompletedListener(){
			@Override
			public void onScanCompleted(String path,Uri uri){
			Log.e("ExternalStorage","Scanned"+path+":");
			Log.e("ExternalStorage","->uri="+uri);
		}

		});
	}
	
}

