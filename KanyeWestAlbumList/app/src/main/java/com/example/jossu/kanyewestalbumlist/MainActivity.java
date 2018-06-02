package com.example.jossu.kanyewestalbumlist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.lang.String;

public class MainActivity extends AppCompatActivity
{
    private String urlJson = "http://api.musixmatch.com/ws/1.1/chart.artists.get?page=1&page_size=50&country=it&format=json&apikey=ca2b48e62bb4e5cf332cf298de4f85a6";

    private String[] names;
    private String[] artistWebsites;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRequest = (Button) findViewById(R.id.button);
        final ListView songsListView = (ListView) findViewById(R.id.albumList);
        final RequestQueue requestQueue = Volley.newRequestQueue(this);


        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, urlJson, null,
                new Response.Listener<JSONObject>()
                {

                    public void onResponse(JSONObject response)
                    {


                        try
                        {
                            names = new String[50];
                            artistWebsites = new String[50];

                            JSONObject message = response.getJSONObject("message");


                            JSONObject body = message.getJSONObject("body");
                            JSONArray artistList = body.getJSONArray("artist_list");

                            for(int element = 0; element<50; element++)
                            {

                                JSONObject artist = artistList.getJSONObject(element);
                                JSONObject artisss = artist.getJSONObject("artist");
                                String artistName = artisss.getString("artist_name");
                                String artistWebsite = artisss.getString("artist_share_url");

                                names[element] = artistName;
                                artistWebsites[element] = artistWebsite;


                            }


                            List<String> songList = new ArrayList<String>(Arrays.asList(names));
                            ArrayAdapter<String> arrayAdapter;
                            arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1, songList);

                            songsListView.setAdapter(arrayAdapter);

                            songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    goToWebsite(artistWebsites[position]);
                                }
                                //method to lunch website
                                public void goToWebsite(String url){

                                    Uri website = Uri.parse(url);
                                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, website);
                                    startActivity(launchBrowser);
                                }
                            });
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

