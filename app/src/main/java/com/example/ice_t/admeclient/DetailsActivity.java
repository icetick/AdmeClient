package com.example.ice_t.admeclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by ice-t on 30.08.2017.
 */

//This activity only have a result of choosing article
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Finding webview
        WebView webView = (WebView) findViewById(R.id.details_webview);
        //Getting intent
        Intent intent = getIntent();
        //And eventually getting details url
        String Url = intent.getStringExtra("Url");
        //Loading this url into webview
        webView.loadUrl(Url);
    }

}
