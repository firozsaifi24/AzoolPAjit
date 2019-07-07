package com.example.firozsaifi.azoolp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.interfaces.OnProductDeleteCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductViewAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Ads> adsArrayList;
    String root= "http://azool.ae/multimedia/products/";
    public OnProductDeleteCallback onProductDeleteCallback;

    public ProductViewAdapter(Context mContext, ArrayList<Ads> adsArrayList, OnProductDeleteCallback onProductDeleteCallback) {
        this.mContext = mContext;
        this.adsArrayList = adsArrayList;
        this.onProductDeleteCallback= onProductDeleteCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view_products, parent,false);
        return new AdsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Ads ads = adsArrayList.get(position);
        AdsHolder holder = (AdsHolder) viewHolder;

        if(ads.getPartType().equals("spare"))
        {

            if (ads.getName() != null && !ads.getName().isEmpty()) {
                holder.txt_name_english.setText(ads.getName());
            } else{
                holder.txt_name_english.setText(ads.getArabicName());
            }

            holder.txt_price.setText("");
            holder.txt_brand.setText(ads.getBrandValue());
            holder.txt_model.setText(ads.getModelValue());
            holder.txt_year.setText(ads.getYearValue());
            holder.txt_category.setText(ads.getCategoryValue());
            holder.txt_description.setText("");
            holder.description_layout.setVisibility(View.GONE);
            holder.spare_part_layout.setVisibility(View.VISIBLE);
            holder.txt_views.setText("Views "+ads.getViews());
            holder.txt_date.setText(ads.getPosted_date());

        /*if (ads.getArabicName() != null && !ads.getArabicName().isEmpty()) {
            holder.txt_name_arabic.setText(ads.getArabicName());
        }*/

        /*
        .resize(6000, 2000)
        .onlyScaleDown() // the image will only be resized if
        // it's bigger than 6000x2000 pixels.
        */

            if (mContext != null){
                //Glide.with(mContext).load(ads.getImage()).into(holder.img_ads);
                Picasso.get().load(root+ads.getImage()).resize(800,800).onlyScaleDown().into(holder.img_ads);
            }

        }
        else
        {
            if (ads.getName() != null && !ads.getName().isEmpty()) {
                holder.txt_name_english.setText(ads.getName());
            } else{
                holder.txt_name_english.setText(ads.getArabicName());
            }

            holder.txt_price.setText(ads.getPrice());
            holder.txt_brand.setText(ads.getBrandValue());
            holder.txt_model.setText(ads.getModelValue());
            holder.txt_year.setText(ads.getYearValue());
            holder.txt_category.setText("");
            holder.txt_description.setText(ads.getDescription());
            holder.description_layout.setVisibility(View.VISIBLE);
            holder.spare_part_layout.setVisibility(View.GONE);
            holder.txt_views.setText("Views "+ads.getViews());
            holder.txt_date.setText(ads.getPosted_date());

        /*if (ads.getArabicName() != null && !ads.getArabicName().isEmpty()) {
            holder.txt_name_arabic.setText(ads.getArabicName());
        }*/

        /*
        .resize(6000, 2000)
        .onlyScaleDown() // the image will only be resized if
        // it's bigger than 6000x2000 pixels.
        */

            if (mContext != null){
                //Glide.with(mContext).load(ads.getImage()).into(holder.img_ads);
                Picasso.get().load(root+ads.getImage()).resize(1080,1080).onlyScaleDown().into(holder.img_ads);
            }
        }


    }

    @Override
    public int getItemCount() {
        return adsArrayList.size();
    }

    private class AdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView img_ads;
        ImageView img_edit, img_delete;
        //TextView txt_name_arabic;
        TextView txt_name_english;
        TextView txt_price, txt_brand, txt_model, txt_year, txt_category;
        TextView txt_views, txt_date, txt_description;

        RelativeLayout spare_part_layout;
        LinearLayout description_layout;




        public AdsHolder(View view) {
            super(view);
            img_ads=view.findViewById(R.id.img_ads_item);
            //txt_name_arabic=view.findViewById(R.id.txt_name_arabic);
            txt_name_english=view.findViewById(R.id.item_title);
            txt_price= view.findViewById(R.id.txt_price);
            txt_brand= view.findViewById(R.id.txt_brand);
            txt_model= view.findViewById(R.id.txt_model);
            txt_year= view.findViewById(R.id.txt_year);
            txt_category= view.findViewById(R.id.txt_cat);
            txt_views= view.findViewById(R.id.txt_views);
            txt_date= view.findViewById(R.id.txt_date);
            txt_description= view.findViewById(R.id.txt_description);
            img_edit= view.findViewById(R.id.img_edit);
            img_delete= view.findViewById(R.id.img_delete);
            spare_part_layout= view.findViewById(R.id.spare_part_layout);
            description_layout= view.findViewById(R.id.description_layout);

            img_edit.setOnClickListener(this);
            img_delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {

                case R.id.img_edit:

                     Log.e("item ads clicked", "item ads clicked");
                    if(mContext!=null)
                    {
                        Log.e("item ads conext", "item ads clicked context passed");
                        String a=adsArrayList.get(getAdapterPosition()).getPartType();
                        Log.d("Type", a.toString());

                        if(a.equals("accident_part"))
                        {
                            try
                            {
                                Log.e("item ads in try catch", "item ads try catch in accident car");
                                MainActivity mainActivity=(MainActivity)mContext;
                                mainActivity.editProduct(adsArrayList.get(getAdapterPosition()).getId());

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else if(a.equals("spare"))
                        {
                            try
                            {
                                Log.e("item ads in try catch", "item ads try catch in spare parts");
                                MainActivity mainActivity=(MainActivity)mContext;
                                mainActivity.editProduct(adsArrayList.get(getAdapterPosition()).getId());

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }

                    break;

                case R.id.img_delete:

                    if(mContext!=null)
                    {
                        onProductDeleteCallback.onProductDelete(Integer.parseInt(adsArrayList.get(getAdapterPosition()).getId()) , getAdapterPosition());
                    }
                    break;
            }
        }
    }

}
