package com.example.ice_t.admeclient;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ice-t on 30.08.2017.
 */

//This adapter works with listview of actual news (topics)
public class ArticlesAdapter extends ArrayAdapter<String> {

    //With that objects this adapter works
    private Activity context;
    private ArrayList<Article> articles;
    private DbFavHelper hp;

    //Ctor
    public ArticlesAdapter(Activity context, ArrayList<Article> articles) {
        super(context, R.layout.news_list_item, new String[articles.size()]);
        this.context = context;
        this.articles = articles;
    }

    //This method infates markup and set some items of listview
    public View getView(final int position, View view, ViewGroup parent) {

        //Inflating some markup
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.news_list_item, null, true);

        //Taking from inflated markup some objects
        TextView txtTitle = (TextView) rowView.findViewById(R.id.article_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.article_image);
        TextView extratxt = (TextView) rowView.findViewById(R.id.article_description);
        ImageButton button = (ImageButton) rowView.findViewById(R.id.image_button);

        //Initializing dbhelper to manipulate with favorites database
        hp = new DbFavHelper(context);

        //Setting object information into listview
        txtTitle.setText(articles.get(position).getTitle());
        //Using Picasso to load link into imageview
        Picasso.with(context).load(articles.get(position).getImageUrl()).into(imageView);
        extratxt.setText(articles.get(position).getDescription());

        //Checking if this object favorite and has been in database
        if(hp.checkFavorite(articles.get(position)))
            (button).setImageResource(android.R.drawable.star_big_on);

        //Setting star button
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Checking if this object favorite and has been in database
                    if (!hp.checkFavorite(articles.get(position))) {
                        //If not - changing image on star
                        ((ImageButton) view).setImageResource(android.R.drawable.star_big_on);
                        //And add item to database
                        hp.addItem(articles.get(position));
                        //Saying some information to user
                        Toast.makeText(context, "Article added to favorite", Toast.LENGTH_SHORT).show();
                    } else {
                        //If it exists we change image and removing it
                        ((ImageButton) view).setImageResource(android.R.drawable.star_big_off);

                        hp.removeItem(articles.get(position));

                        Toast.makeText(context, "Article removed from favorite", Toast.LENGTH_SHORT).show();
                    }
                }});
        return rowView;
    }
}
