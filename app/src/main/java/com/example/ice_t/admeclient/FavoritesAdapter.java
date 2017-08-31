package com.example.ice_t.admeclient;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ice-t on 31.08.2017.
 */
//This adapter works with listview of favorite news (topics)
public class FavoritesAdapter extends ArrayAdapter<String> {

    //With that objects this adapter works
    private FavoritesActivity context;
    private ArrayList<Article> favorites;
    private DbFavHelper hp;

    //Ctor
    public FavoritesAdapter(FavoritesActivity context, DbFavHelper helper) {
        super(context, R.layout.news_list_item, new String[helper.getFavorites().size()]);
        this.hp = helper;
        this.context = context;
        this.favorites = helper.getFavorites();
    }

    //This method infates markup and set some items of listview
    public View getView(final int position, View view, ViewGroup parent) {

        //Inflating some markup
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.favs_list_item, null, true);

        //Taking from inflated markup some objects
        TextView txtTitle = (TextView) rowView.findViewById(R.id.article_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.article_image);
        TextView extratxt = (TextView) rowView.findViewById(R.id.article_description);
        ImageButton button = (ImageButton) rowView.findViewById(R.id.image_button);

        //Setting object information into listview
        txtTitle.setText(favorites.get(position).getTitle());
        //Using Picasso to load link into imageview
        Picasso.with(context).load(favorites.get(position).getImageUrl()).into(imageView);
        extratxt.setText(favorites.get(position).getDescription());
        //If we need to delete item
        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ImageButton) view).setImageResource(android.R.drawable.ic_menu_delete);
                    hp.removeItem(favorites.get(position));
                }
            });
        return rowView;
    }
}