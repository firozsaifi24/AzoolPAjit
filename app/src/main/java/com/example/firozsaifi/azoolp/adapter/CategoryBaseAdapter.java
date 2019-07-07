package com.example.firozsaifi.azoolp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.model.Category;
import com.example.firozsaifi.azoolp.model.Year;

import java.util.ArrayList;

public class CategoryBaseAdapter extends BaseAdapter {

    ArrayList<Category> categoryArrayList;

    public CategoryBaseAdapter(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }

    @Override
    public int getCount() {
        return categoryArrayList.size();
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
        CategoryBaseHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_popup_brand, parent, false);
            holder = new CategoryBaseHolder(convertView);
            convertView.setTag(holder);
        } else{
            holder = (CategoryBaseHolder) convertView.getTag();
        }
        holder.txt_category.setText(categoryArrayList.get(position).getTitle());
        return convertView;
    }

    class CategoryBaseHolder{

        TextView txt_category;

        public CategoryBaseHolder(View itemView) {
            txt_category = (TextView) itemView.findViewById(R.id.txt_brand_list_item);
        }
    }

}
