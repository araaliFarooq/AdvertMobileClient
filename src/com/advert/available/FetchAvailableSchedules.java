package com.advert.available;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FetchAvailableSchedules extends AsyncTask<String, Void, JSONArray> {

    public AdvertListScreen listScreen = null;
    ArrayList<HashMap<String, String>> arraylist;

    @Override
    protected JSONArray doInBackground(String... urls) {

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(urls[0]);
        String responseBody = "{}";
        try {
            HttpResponse response = client.execute(get);
            responseBody = getResponseBody(response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            return new JSONArray(responseBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONArray todoJsonArray) {
    	
    	arraylist = new ArrayList<HashMap<String, String>>();
     //   List<AdvertSchedule> todoItems = new ArrayList<AdvertSchedule>();

        for (int i = 0; i < todoJsonArray.length(); i++) {
        	HashMap<String, String> map = new HashMap<String, String>();
            try {
                JSONObject jsonObject = todoJsonArray.getJSONObject(i);
                map.put("targetArea", jsonObject.getString("targetArea"));
				map.put("startTime",jsonObject.getString("startTime"));
				map.put("endTime",jsonObject.getString("endTime"));
				map.put("capacity", jsonObject.getString("capacity"));
				map.put("price", jsonObject.getString("price"));
				map.put("status",jsonObject.getString("status"));
				arraylist.add(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listScreen.displayList(arraylist);
    }

    private String getResponseBody(HttpResponse response) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream body = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(body));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}