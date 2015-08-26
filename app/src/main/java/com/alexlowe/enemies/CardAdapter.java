package com.alexlowe.enemies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Alex on 8/25/2015.
 */
public class CardAdapter extends ArrayAdapter {
    private ArrayList<String> reasons;

    public CardAdapter(Context context, ArrayList<String> reasons){
        super(context, 0, reasons);
        this.reasons = reasons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView == null) {
            vi = convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_card, parent, false);
        }

        TextView reasonTV = (TextView) vi.findViewById(R.id.desc_text);
        TextView timpStampTV = (TextView) vi.findViewById(R.id.timeStamp);

        reasonTV.setText(reasons.get(position));
        return vi;
     }
}
