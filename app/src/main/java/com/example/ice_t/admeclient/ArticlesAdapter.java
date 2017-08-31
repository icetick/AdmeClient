package com.example.ice_t.admeclient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ice-t on 30.08.2017.
 */

public class ArticlesAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<Article> articles;

    public ArticlesAdapter(Activity context, ArrayList<Article> articles) {
        super(context, R.layout.news_list_item, new String[articles.size()]);
        this.context = context;
        this.articles = articles;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.news_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.article_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.article_image);
        TextView extratxt = (TextView) rowView.findViewById(R.id.article_description);

        txtTitle.setText(articles.get(position).getTitle());
        Picasso.with(context).load(articles.get(position).getImageUrl()).into(imageView);
        //imageView.setImageResource(imgid[position]);
        extratxt.setText(articles.get(position).getDescription());
        return rowView;
    }
}
