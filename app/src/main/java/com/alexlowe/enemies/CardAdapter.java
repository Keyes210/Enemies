package com.alexlowe.enemies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Alex on 8/25/2015.
 */
public class CardAdapter extends ArrayAdapter<Reason> {
    private ArrayList<Reason> reasons;

    public CardAdapter(Context context, ArrayList<Reason> reasons){
        super(context, 0, reasons);
        this.reasons = reasons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reason reason = reasons.get(position);

        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_card, parent, false);
            viewHolder.tvReason = (TextView) convertView.findViewById(R.id.desc_text);
            viewHolder.tvTimeStamp = (TextView) convertView.findViewById(R.id.timeStamp);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

       viewHolder.tvReason.setText(reason.getDescription());
       viewHolder.tvTimeStamp.setText(reason.getTime());

        return convertView;
     }

    private static class ViewHolder {

        public TextView tvReason;
        public TextView tvTimeStamp;
    }
}
