package com.example.varshadhoni.viewing;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 1000000;
    public static final int READ_TIMEOUT = 1500000;
    private RecyclerView mBookPrice;
    private AdapterBooksCombos mAdapter;
    String responseServer;
    CustomAdapter customAdapter;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncLogin asyncLogin = new AsyncLogin();
        asyncLogin.execute();
    }


    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
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

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall("https://us-central1-bookaholic-786.cloudfunctions.net/home");

            Log.e("responce", "Response from url: " + jsonStr);
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<DataBooksCombos> data = new ArrayList<>();
            List<BooksAdapter> data2 = new ArrayList<>();
            pdLoading.dismiss();

            if (result != null) {
                try {
                    JSONObject jsonObj = new JSONObject(result);

                    // Getting JSON Array node
                    JSONArray json = jsonObj.getJSONArray("products");

                    Log.e("Varsha Vishith" + json.length(), json.toString());


                    // looping through All Contacts
                    for (int i = 1; i < 19; i++) {
                        JSONObject c = json.getJSONObject(i);

                        DataBooksCombos bookData = new DataBooksCombos();
                        bookData.bookImage = c.getString("imageURL");
                        bookData.bookName = c.getString("productName");
                        bookData.descrition = c.getString("bookSummary");
                        bookData.duration = c.getString("readingDuration");
                        bookData.price = c.getInt("MRP");
                        data.add(bookData);
                    }

                    JSONArray json2 = jsonObj.getJSONArray("combos");
                    // looping through All Contacts
                    for (int i = 0; i < json2.length(); i++) {
                        JSONObject c2 = json2.getJSONObject(i);

                        BooksAdapter bookData2 = new BooksAdapter();
                        bookData2.booksImages = c2.getString("imageURL");
                        data2.add(bookData2);
                    }
                    viewPager = (ViewPager) findViewById(R.id.view_pager);
                    customAdapter = new CustomAdapter(MainActivity.this, data2);
                    viewPager.setAdapter(customAdapter);

                    mBookPrice = (RecyclerView) findViewById(R.id.bookRecyclerView);
                    mAdapter = new AdapterBooksCombos(MainActivity.this, data);
                    mBookPrice.setAdapter(mAdapter);
                    mBookPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, true));


                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                    e.printStackTrace();
                }


            }
        }
    }
}

