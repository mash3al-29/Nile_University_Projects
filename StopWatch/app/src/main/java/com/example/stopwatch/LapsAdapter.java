package com.example.stopwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LapsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> laps;

    public LapsAdapter(Context context, ArrayList<String> laps) {
        this.context = context;
        this.laps = laps;
    }

    @Override
    public int getCount() {
        return laps.size();
    }

    @Override
    public Object getItem(int position) {
        return laps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lap_item, parent, false);
        }

        TextView labelNum = convertView.findViewById(R.id.labelNum);
        TextView labelTimer = convertView.findViewById(R.id.labelTimer);

        labelNum.setText("Lap " + (laps.size() - position));
        labelTimer.setText(laps.get(laps.size() - position - 1));

        return convertView;
    }
}