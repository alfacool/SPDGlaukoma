package com.meio.SPDGlaukoma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by Meio on 05/10/2017.
 */

public class Hasil extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    String username;


    TextView nickname;
    TextView penyakit;
    TextView cf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);
        username=getIntent().getStringExtra("username");

        try{
            //do post
            new AsyncLogin().execute(username);

        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
        }
    }

    public void ulang(View view) {

        Intent intent = new Intent(Hasil.this, Diagnosa.class);
        intent.putExtra("username", username);
        startActivity(intent);
        Hasil.this.finish();

    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Hasil.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            nickname = (TextView)findViewById(R.id.nickname);
            penyakit = (TextView)findViewById(R.id.penyakit);
            cf = (TextView)findViewById(R.id.cf);


            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(getString(R.string.url_json));

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";

            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            //nickname.setText(result);
            //json work
            try {
                // Getting JSON Array
                JSONObject jj = new JSONObject(result);
                JSONArray user = jj.getJSONArray("json");
                JSONObject c = user.getJSONObject(0);
                // Storing  JSON item in a Variable
                String unickname = c.getString("nickname");
                String upenyakit = c.getString("penyakit");
                String ucf = c.getString("cf");
                double d=Double.parseDouble(ucf) * 100;


                //Set JSON Data in TextView
                nickname.setText(unickname);
                penyakit.setText(upenyakit);
                cf.setText(new DecimalFormat("##.##").format(d)+" %");


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), " Json Failed ", Toast.LENGTH_LONG).show();
            }

        }

    }


}
