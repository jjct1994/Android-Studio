package com.example.jossu.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Date;
import java.lang.String;



public class MainActivity extends Activity
{

    private String urlJson = "http://api.openweathermap.org/data/2.5/forecast?q=London,us&APPID=2e3206cfcbc9d647a7cdc4613d4cb8c1";
    private String[] tempItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btnRequest = (Button) findViewById(R.id.requestButton);
        final ListView temps = (ListView) findViewById(R.id.maxMinTemps);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, urlJson, null,
                new Response.Listener<JSONObject>()
                {
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            tempItems = new String[10];
                            JSONObject jCity = response.getJSONObject("city");

                            String name = jCity.getString("name");
                            JSONArray wList = response.getJSONArray("list");
                            for(int i = 0; i<10; i++)
                            {
                                JSONObject jsonObject = wList.getJSONObject(i);
                                String date = jsonObject.getString("dt");
                                int dateInt = Integer.parseInt(date);
                                Date dateFormat = new Date(dateInt);


                               JSONArray wArray = jsonObject.getJSONArray("weather");
                               JSONObject wObject = wArray.getJSONObject(0);
                               String desc = wObject.getString("description");

                                tempItems[i] = date + " had " + desc;
                            }


                            List<String> tempList = new ArrayList<String>(Arrays.asList(tempItems));
                            ArrayAdapter<String> arrayAdapter;
                            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, tempList);

                            temps.setAdapter(arrayAdapter);
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {

                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Volley","Error");
                    }
                }
        );
                btnRequest.setOnClickListener(new OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        requestQueue.add(jor);
                    }
                });
    }
}
