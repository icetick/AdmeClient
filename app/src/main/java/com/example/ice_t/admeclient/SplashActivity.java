package com.example.ice_t.admeclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private ArrayList<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //If connection exists, executing task to load some info
        if(!ConnectManager.hasConnection(SplashActivity.this)) {
            Toast.makeText(SplashActivity.this, R.string.no_internet, Toast.LENGTH_LONG).show();
        }
        new NewsTask().execute();
    }

    //This task fills articles with info
    public class NewsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            //Jsoup DOM class
            Document document;
            try
            {
                //Getting some document
                document = Jsoup.connect(getString(R.string.adme_website)).get();
                //Taking all image elements with article class
                Elements images = document.select(getString(R.string.pictures_class));
                //Also we need to get titles
                Elements titles = document.select(getString(R.string.titles_class));
                //And descriptions
                Elements descriptions = document.select(getString(R.string.descriptions_class));
                //Going through collections and taking info to fill articles collection
                for(int i = 0; i < titles.size(); i++)
                {
                    //Also it needs to get details Url to fill entity
                    articles.add(new Article(images.get(i).absUrl(getString(R.string.source_attribute)),
                            titles.get(i).text(),
                            descriptions.get(i).text(),
                            titles.get(i).select(getString(R.string.link_tag))
                                    .first().absUrl(getString(R.string.link_attribute))));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }
        //After task starts main
        @Override
        protected void onPostExecute(String s) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("articles", articles);
            startActivity(intent);
        }
    }
}
