package com.pikchillytechnologies.mynewsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class NewsTypeActivity extends AppCompatActivity {

    // Declaration of Variables
    CardView mCardViewNews;
    CardView mCardViewSports;
    CardView mCardViewFinance;
    CardView mCardViewTechnology;
    CardView mCardViewEntertainment;
    String mNewsSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_type);

        // Initialize views
        initViews();

        mCardViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewsSource = getResources().getString(R.string.bbc_news);
                callIntent();
            }
        });

        mCardViewSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewsSource = getResources().getString(R.string.espn_news);
                callIntent();
            }
        });

        mCardViewFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewsSource = getResources().getString(R.string.financial_post);
                callIntent();
            }
        });

        mCardViewTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewsSource = getResources().getString(R.string.tech);
                callIntent();
            }
        });

        mCardViewEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewsSource = getResources().getString(R.string.entertainment_weekly);
                callIntent();
            }
        });

    }

    /**
     * Function to intent
     * */

    public void callIntent(){

        Intent newsIntent = new Intent(NewsTypeActivity.this,MainActivity.class);
        newsIntent.putExtra("news_type",mNewsSource);
        startActivity(newsIntent);
    }

    /**
     * Function to initialize views
     * */
    public void initViews(){
        mCardViewNews = findViewById(R.id.cardView_News);
        mCardViewSports = findViewById(R.id.cardView_Sports);
        mCardViewFinance = findViewById(R.id.cardView_Finance);
        mCardViewTechnology = findViewById(R.id.cardView_Tech);
        mCardViewEntertainment = findViewById(R.id.cardView_Entertainment);
    }
}
