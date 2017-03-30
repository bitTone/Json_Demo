package com.tonyjones.jsondemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask task = new DownloadTask();
        task.execute("");
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {


        @Override
        /**
         *
         * gets data in background method
         *  instead of main thread
         *
         */
        protected String doInBackground(String... urls) {
            //The Json Data we receive empty string
            String result = "";
            //url to connect to online
            URL url;
            //opens up the urlConnection
            HttpURLConnection urlConnection = null;
            //the try catch just in cause connection the crashes, just in cause we don't get a valid url
            try {
                //gets a valid url
                url = new URL(urls[0]);
                //opens the url connection
                urlConnection = (HttpURLConnection)url.openConnection();

                //inputstream gets data from
                InputStream in = urlConnection.getInputStream();
                //reads inputstream data from inputstreamreader
                InputStreamReader reader = new InputStreamReader(in);
                //data
                int data = reader.read();
                // loop that adds the data found to the result
                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.i("Website Content", result);



            try {
                //converts string into jsonobject
                JSONObject jsonObject = new JSONObject(result);

                String weatherInfo = jsonObject.getString("weather");

                Log.i("Weather content", weatherInfo);

                JSONArray arr = new JSONArray(weatherInfo);

                for ( int i = 0; i < arr.length(); i++){

                    JSONObject jsonPart = arr.getJSONObject(i);

                    Log.i("main", jsonPart.getString("main"));
                    Log.i("description", jsonPart.getString("description"));


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }
}