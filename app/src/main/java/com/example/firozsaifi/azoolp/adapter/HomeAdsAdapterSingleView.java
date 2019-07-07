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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.model.Ads;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdsAdapterSingleView extends RecyclerView.Adapter {


    private Context mContext;
    private ArrayList<Ads> adsArrayList;
    String root= "http://azool.ae/multimedia/products/";

    public HomeAdsAdapterSingleView(Context mContext, ArrayList<Ads> adsArrayList) {
        this.mContext = mContext;
        this.adsArrayList = adsArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home_ads_single_view, parent,false);
        return new HomeAdsAdapterSingleView.AdsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Ads ads = adsArrayList.get(position);
        HomeAdsAdapterSingleView.AdsHolder holder = (HomeAdsAdapterSingleView.AdsHolder) viewHolder;

        if (ads.getName() != null && !ads.getName().isEmpty()) {
            holder.txt_name_english.setText(ads.getName());
        } else{
            holder.txt_name_english.setText(ads.getArabicName());
        }

        if (ads.getArabicName() != null && !ads.getArabicName().isEmpty()) {
            holder.txt_name_arabic.setText(ads.getArabicName());
        }

        if (mContext != null){
            //Glide.with(mContext).load(ads.getImage()).into(holder.img_ads);
            Picasso.get().load(root+ads.getImage()).into(holder.img_ads);
        }
    }

    @Override
    public int getItemCount() {
        return adsArrayList.size();
    }

    private class AdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView img_ads;
        TextView txt_name_arabic;
        TextView txt_name_english;
        LinearLayout txt_visit_shop;
        LinearLayout itemLayout;


        public AdsHolder(View view) {
            super(view);
            img_ads=view.findViewById(R.id.img_ads_item);
            txt_name_arabic=view.findViewById(R.id.txt_name_arabic);
            txt_name_english=view.findViewById(R.id.txt_name_english);
            txt_visit_shop=view.findViewById(R.id.txt_view_shop);
            itemLayout=view.findViewById(R.id.item_home_ads);

            txt_visit_shop.setOnClickListener(this);
            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {

                case R.id.txt_view_shop:

                    if(mContext!=null)
                    {
                        try
                        {
                            MainActivity mainActivity=(MainActivity)mContext;
                            mainActivity.visitShop(adsArrayList.get(getAdapterPosition()).getDealerId());

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }

                    break;

                case R.id.item_home_ads:

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
                                mainActivity.dealerAccidentCarDetails(adsArrayList.get(getAdapterPosition()).getId());

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
                                mainActivity.dealerSparePartDetails(adsArrayList.get(getAdapterPosition()).getId());

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }

                    break;

            }
        }
    }


}
