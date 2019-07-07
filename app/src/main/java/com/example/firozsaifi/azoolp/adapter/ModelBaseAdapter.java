package com.example.firozsaifi.azoolp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.model.Model;

import java.util.ArrayList;

public class ModelBaseAdapter extends BaseAdapter {

    ArrayList<Model> modelArrayList;

    public ModelBaseAdapter(ArrayList<Model> modelArrayList) {
        this.modelArrayList = modelArrayList;
    }

    @Override
    public int getCount() {
        return modelArrayList.size();
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
        ModelBaseHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popup_brand, parent, false);
            holder = new ModelBaseHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (ModelBaseHolder) convertView.getTag();
        }
        holder.txt_model.setText(modelArrayList.get(position).getTitle());
        return convertView;
    }

    class ModelBaseHolder{

        TextView txt_model;

        public ModelBaseHolder(View itemView) {
            txt_model = (TextView) itemView.findViewById(R.id.txt_brand_list_item);
        }
    }

}
