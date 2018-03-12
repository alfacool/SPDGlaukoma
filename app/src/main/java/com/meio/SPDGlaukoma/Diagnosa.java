package com.meio.SPDGlaukoma;

import android.app.ProgressDialog;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;



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


public class Diagnosa extends AppCompatActivity implements OnItemSelectedListener{

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    Spinner spinner1,spinner2,spinner3,spinner4,spinner5,spinner6,spinner7,spinner8,spinner9,spinner10;
    String ndiag1, ndiag2,ndiag3,ndiag4,ndiag5,ndiag6,ndiag7,ndiag8,ndiag9,ndiag10;

    String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);

        username=getIntent().getStringExtra("username");
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();


        spinner1 = (Spinner) findViewById(R.id.poin1);
        spinner2 = (Spinner) findViewById(R.id.poin2);
        spinner3 = (Spinner) findViewById(R.id.poin3);
        spinner4 = (Spinner) findViewById(R.id.poin4);
        spinner5 = (Spinner) findViewById(R.id.poin5);
        spinner6 = (Spinner) findViewById(R.id.poin6);
        spinner7 = (Spinner) findViewById(R.id.poin7);
        spinner8 = (Spinner) findViewById(R.id.poin8);
        spinner9 = (Spinner) findViewById(R.id.poin9);
        spinner10 = (Spinner) findViewById(R.id.poin10);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.diag, android.R.layout.simple_spinner_item);
        // Set layout style during dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Load data from adapter
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
        spinner4.setAdapter(adapter);
        spinner5.setAdapter(adapter);
        spinner6.setAdapter(adapter);
        spinner7.setAdapter(adapter);
        spinner8.setAdapter(adapter);
        spinner9.setAdapter(adapter);
        spinner10.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setOnItemSelectedListener(this);
        spinner6.setOnItemSelectedListener(this);
        spinner7.setOnItemSelectedListener(this);
        spinner8.setOnItemSelectedListener(this);
        spinner9.setOnItemSelectedListener(this);
        spinner10.setOnItemSelectedListener(this);







    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        switch(parent.getId()) {
            case R.id.poin1:
                // Do stuff for spinner1
                ndiag1=nilai(item);
                break;

            case R.id.poin2:
                 // Do stuff for spinner2
                ndiag2=nilai(item);
                 break;

            case R.id.poin3:

                ndiag3=nilai(item);
                break;

            case R.id.poin4:

                ndiag4=nilai(item);
                break;

            case R.id.poin5:

                ndiag5=nilai(item);
                break;

            case R.id.poin6:

                ndiag6=nilai(item);
                break;

            case R.id.poin7:

                ndiag7=nilai(item);
                break;

            case R.id.poin8:

                ndiag8=nilai(item);
                break;

            case R.id.poin9:

                ndiag9=nilai(item);
                break;

            case R.id.poin10:

                ndiag10=nilai(item);
                break;
        }

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    String nilai (String item){
        String n="0";
        if (item.equalsIgnoreCase("Tidak")) {
            n="0";
        }
        else if (item.equalsIgnoreCase("Tidak Tahu")){
            n="0.2";
        }
        else if (item.equalsIgnoreCase("Sedikit Yakin")){
            n="0.4";
        }
        else if (item.equalsIgnoreCase("Cukup Yakin")){
            n="0.6";
        }
        else if (item.equalsIgnoreCase("Yakin")){
            n="0.8";
        }
        else if (item.equalsIgnoreCase("Sangat Yakin")){
            n="1";
        }
        return n;
    }


    public void kirim(View view) {
        try{
            //do post
            new AsyncLogin().execute(username,ndiag1, ndiag2,ndiag3,ndiag4,ndiag5,ndiag6,ndiag7,ndiag8,ndiag9,ndiag10);


        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
        }

    }


    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Diagnosa.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides

                url = new URL(getString(R.string.url_diagnosa));

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
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("ndiag1", params[1])
                        .appendQueryParameter("ndiag2", params[2])
                        .appendQueryParameter("ndiag3", params[3])
                        .appendQueryParameter("ndiag4", params[4])
                        .appendQueryParameter("ndiag5", params[5])
                        .appendQueryParameter("ndiag6", params[6])
                        .appendQueryParameter("ndiag7", params[7])
                        .appendQueryParameter("ndiag8", params[8])
                        .appendQueryParameter("ndiag9", params[9])
                        .appendQueryParameter("ndiag10", params[10]);
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

            if (result.equalsIgnoreCase("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Diagnosa.this, Hasil.class);
                intent.putExtra("username", username);
                startActivity(intent);
                Diagnosa.this.finish();

            } else if (result.equalsIgnoreCase("false")) {

                // If username and password does not match display a error message
                Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(getApplicationContext(), "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }


}
