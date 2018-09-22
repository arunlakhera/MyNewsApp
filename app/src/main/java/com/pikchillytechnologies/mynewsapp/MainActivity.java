package com.pikchillytechnologies.mynewsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // Variable Declaration
    static String KEY_AUTHOR;
    static String KEY_TITLE;
    static String KEY_DESCRIPTION;
    static String KEY_URL;
    static String KEY_URLTOIMAGE;
    static String KEY_PUBLISHEDAT;
    ArrayList<HashMap<String, String>> dataList;
    private String API_KEY;
    private String NEWS_SOURCE;
    private ListView listNews;
    private ProgressBar loader;
    Button mBackButton;
    Bundle newsBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        initViews();

        // Check if internet is connected
        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        }

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NewsTypeActivity.class));
            }
        });

    }

    public void initViews() {

        listNews = findViewById(R.id.listNews);
        loader = findViewById(R.id.loader);
        newsBundle = getIntent().getExtras();
        NEWS_SOURCE = newsBundle.getString("news_type","News Type");
        listNews.setEmptyView(loader);
        API_KEY = getResources().getString(R.string.api_key);
        KEY_AUTHOR = getResources().getString(R.string.author);
        KEY_TITLE = getResources().getString(R.string.title);
        KEY_DESCRIPTION = getResources().getString(R.string.description);
        KEY_URL = getResources().getString(R.string.url);
        KEY_URLTOIMAGE = getResources().getString(R.string.urlToImage);
        KEY_PUBLISHEDAT = getResources().getString(R.string.publishedAt);
        dataList = new ArrayList<HashMap<String, String>>();
        mBackButton = findViewById(R.id.button_back);
        mBackButton.setVisibility(View.VISIBLE);

    }

    // Perform task of loading news at background
    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = Function.executeGet("https://newsapi.org/v1/articles?source=" + NEWS_SOURCE + "&sortBy=top&apiKey=" + API_KEY, urlParameters);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) {

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray(String.valueOf(getResources().getString(R.string.articles)));

                    if(jsonArray.length() < 1){
                        Toast.makeText(MainActivity.this,getResources().getString(R.string.link_error),Toast.LENGTH_LONG).show();
                    }else {


                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put(KEY_AUTHOR, (jsonObject.optString(KEY_AUTHOR)));
                            map.put(KEY_TITLE, (jsonObject.optString(KEY_TITLE)));
                            map.put(KEY_DESCRIPTION, (jsonObject.optString(KEY_DESCRIPTION)));
                            map.put(KEY_URL, (jsonObject.optString(KEY_URL)));
                            map.put(KEY_URLTOIMAGE, (jsonObject.optString(KEY_URLTOIMAGE)));
                            map.put(KEY_PUBLISHEDAT, (jsonObject.optString(KEY_PUBLISHEDAT)));

                            dataList.add(map);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.unexpected_err), Toast.LENGTH_SHORT).show();
                }

                ListNewsAdapter adapter = new ListNewsAdapter(MainActivity.this, dataList);
                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                        i.putExtra("url", dataList.get(+position).get(KEY_URL));
                        startActivity(i);
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_news), Toast.LENGTH_SHORT).show();
            }
        }
    }
}