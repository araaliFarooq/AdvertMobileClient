package com.client.advert.model;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.menu.android.R;


public class Home extends Fragment {

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
    View rootView = inflater.inflate(R.layout.home, container, false);
    
	return rootView;
    }	
	}