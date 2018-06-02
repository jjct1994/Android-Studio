package com.example.jossu.universitiesapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView uniList = (ListView) findViewById(R.id.universityList);


        //Array of School Names
        String[] schoolNames = new String[]
                {
                        "University of Texas at Arlington",
                        "University of Texas at Austin",
                        "Baylor University",
                        "Arizona State University",
                        "Oregon State University",
                        "Harvard University",
                        "Oklahoma State University"
                };

        //List of String Nemes
        List<String> uniNames = new ArrayList<String>(Arrays.asList(schoolNames));

        //Adapter to add list to Container
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1, uniNames);

        //university list added to container
        uniList.setAdapter(arrayAdapter);

        uniList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    goToWebsite("https://www.uta.edu/uta/");
                } else if (position == 1) {
                    goToWebsite("https://www.utexas.edu/");
                } else if (position == 2) {
                    goToWebsite("https://www.baylor.edu/");
                } else if (position == 3) {
                    goToWebsite("https://www.asu.edu/");
                } else if (position == 4) {
                    goToWebsite("http://oregonstate.edu/");
                } else if (position == 5) {
                    goToWebsite("https://www.harvard.edu/");
                } else {
                    goToWebsite("https://go.okstate.edu/");
                }
            }
            //method to lunch website
            public void goToWebsite(String url){

                Uri website = Uri.parse(url);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, website);
                startActivity(launchBrowser);
            }
        });

    }



}


