package com.example.acer.sampleproject;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class SecondActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    public static final int Loader_ID = 10;
    ProgressBar pb;
    RecyclerView rv;

    String string;
    String joburl = "https://jobs.github.com/positions.json?description=";
    String lourl = "&location=new+york";
    ArrayList<MyModelClass> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        pb = findViewById(R.id.progess);
        rv = findViewById(R.id.recyclerview);
        models = new ArrayList<>();
        string = getIntent().getStringExtra("data");

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            getLoaderManager().initLoader(Loader_ID, null, this);


        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setTitle("Alert Box");
            builder1.setMessage("O0ps!...No Internet Connection");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                try {
                    URL url = new URL(joburl + string + lourl);
                    Log.i("string", url.toString());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    Scanner scanner = new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if (scanner.hasNext()) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                pb.setVisibility(View.VISIBLE);
                forceLoad();
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, final String s) {

        pb.setVisibility(View.INVISIBLE);

            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length()!=0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobs = jsonArray.getJSONObject(i);
                        String title = jobs.getString("title");
                        String location = jobs.getString("location");
                        String apply = jobs.getString("how_to_apply");
                        models.add(new MyModelClass(title, location, apply));
                    }
                    rv.setLayoutManager(new LinearLayoutManager(SecondActivity.this));
                    rv.setAdapter(new MyAdapter(SecondActivity.this, models));
                }
                else
                {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setTitle("Alert Box");
                    builder1.setMessage("Sorry!...No jobs available");
                    builder1.setCancelable(false);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }




    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
