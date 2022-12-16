package com.example.jsonparser;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.vishnusivadas.advanced_httpurlconnection.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    // Array of strings...
    ListView simpleList;
    Button mainbtn;
    ArrayList<String> arrayList= new ArrayList<>();
    JSONArray fullArray;

    ArrayAdapter<String> arrayAdapter;

    @Override   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      setContentView(R.layout.activity_main);
        simpleList = findViewById(R.id.tavoTevas);
        mainbtn= findViewById(R.id.button2);
        mainbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

    }

    public void getData(){
        String result = null;
        FetchData fetchData = new FetchData("https://api.meteo.lt/v1/places/kaunas/forecasts/long-term");
        if (fetchData.startFetch()) {
            if (fetchData.onComplete()) {
                result = fetchData.getResult();

                try {
                    JSONObject fullObject = new JSONObject(result);
                    fullArray = fullObject.getJSONArray("forecastTimestamps");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for (int i=0; i<fullArray.length(); i++) {

                    try {
                        arrayList.add(fullArray.getJSONObject(i).getString("forecastTimeUtc")+" temp: "+fullArray.getJSONObject(i).getString("airTemperature"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, arrayList);
                simpleList.setAdapter(arrayAdapter);

            }
        }

    }


}