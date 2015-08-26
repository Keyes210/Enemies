package com.alexlowe.enemies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 7/15/2015.
 */
public class EnemyAdapter extends ArrayAdapter<Enemy>{

    public EnemyAdapter(Context context, ArrayList<Enemy> enemies) {
        super(context, 0, enemies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Enemy enemy = (Enemy) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.name_list_items, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.nameLabel);

        // Populate the data into the template view using the data object
        tvName.setText(enemy.name);


        // Return the completed view to render on screen
        return convertView;
    }
}
