package com.example.firozsaifi.azoolp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.model.Year;

import java.util.ArrayList;

public class YearBaseAdapter extends BaseAdapter {

    ArrayList<Year> yearArrayList;

    public YearBaseAdapter(ArrayList<Year> yearArrayList) {
        this.yearArrayList = yearArrayList;
    }

    @Override
    public int getCount() {
        return yearArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        YearBaseHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popup_brand, parent, false);
            holder = new YearBaseHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (YearBaseHolder) convertView.getTag();
        }
        holder.txt_year.setText(yearArrayList.get(position).getTitle());
        return convertView;
    }

    class YearBaseHolder{

        TextView txt_year;

        public YearBaseHolder(View itemView) {
            txt_year = (TextView) itemView.findViewById(R.id.txt_brand_list_item);
        }
    }

}
