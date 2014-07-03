package com.advert.structure;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.advert.structure.Advert;
import com.advert.structure.StructureAdvert;
import com.menu.android.R;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("NewApi")
public class StructureAdFragment extends Fragment{
//implements OnClickListener{
	
	protected static final int REQUEST_CAMERA = 1;
	protected static final int SELECT_FILE = 2;
	EditText etSubject,etDescription,etTimeslot;
	Spinner etTargetArea;
	Button btnPost,btnSelectPhoto;
	ImageView ivImage;
	static Advert advert;
	
	public StructureAdFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.createadvert, container, false);
        etSubject = (EditText)rootView.findViewById(R.id.etSubject);
		etDescription = (EditText)rootView.findViewById(R.id.etDescription);
		btnSelectPhoto=(Button)rootView.findViewById(R.id.btnImage);
		btnPost = (Button)rootView.findViewById(R.id.btnPost);
		
	         
        return rootView;
    }

	
}



