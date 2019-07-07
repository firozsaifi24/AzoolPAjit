package com.example.firozsaifi.azoolp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.model.Brand;

import java.util.ArrayList;

public class BrandBaseAdapter extends BaseAdapter {

    ArrayList<Brand> brandsArrayList;

    public BrandBaseAdapter(ArrayList<Brand> brandsArrayList) {
        this.brandsArrayList = brandsArrayList;
    }

    @Override
    public int getCount() {
        return brandsArrayList.size();
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
        BrandsBaseHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popup_brand, parent, false);
            holder = new BrandsBaseHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (BrandsBaseHolder) convertView.getTag();
        }
        holder.txt_brand.setText(brandsArrayList.get(position).getTitle());
        return convertView;
    }

    class BrandsBaseHolder{

        TextView txt_brand;

        public BrandsBaseHolder(View itemView) {
            txt_brand = (TextView) itemView.findViewById(R.id.txt_brand_list_item);
        }
    }

}
