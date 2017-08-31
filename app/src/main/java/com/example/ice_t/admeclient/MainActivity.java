package com.example.ice_t.admeclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ice-t on 29.08.2017.
 */

//MainActivity with actual topics
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Topics, listview with topics
    private ArrayList<Article> articles = new ArrayList<>();
    private ListView articles_list;
    private ArticlesAdapter articles_list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Finding listview and fill with data from splashscreen (or reload)
        articles_list = (ListView) findViewById(R.id.news_list);
        Intent intent = getIntent();
        if (intent.hasExtra("articles")) {
            articles = (ArrayList<Article>) intent.getSerializableExtra("articles");
            articles_list_adapter = new ArticlesAdapter(MainActivity.this, articles);
            articles_list.setAdapter(articles_list_adapter);
            articles_list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                            intent.putExtra(getString(R.string.url_stringed), articles.get(i).getDetailsUrl());
                            startActivity(intent);
                        }
                    }
            );
        } else {
            new ArticleReloadTask().execute();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
        } else if (id == R.id.nav_favorite) {
            //If in navigation drawer we choose favorite -> change this activity to favorite
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //This task fills articles with info
    public class ArticleReloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            //Jsoup DOM class
            Document document;
            try {
                //Getting some document
                document = Jsoup.connect(getString(R.string.adme_website)).get();
                //Taking all image elements with article class
                Elements images = document.select(getString(R.string.pictures_class));
                //Also we need to get titles
                Elements titles = document.select(getString(R.string.titles_class));
                //And descriptions
                Elements descriptions = document.select(getString(R.string.descriptions_class));
                //Going through collections and taking info to fill articles collection
                articles.clear();
                for (int i = 0; i < titles.size(); i++) {
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

        //After task binding some adapter and setting clicker to check details of certain article
        @Override
        protected void onPostExecute(String s) {
            articles_list_adapter = new ArticlesAdapter(MainActivity.this, articles);
            articles_list.setAdapter(articles_list_adapter);
            articles_list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                            intent.putExtra(getString(R.string.url_stringed), articles.get(i).getDetailsUrl());
                            startActivity(intent);
                        }
                    }
            );
        }
    }
}
