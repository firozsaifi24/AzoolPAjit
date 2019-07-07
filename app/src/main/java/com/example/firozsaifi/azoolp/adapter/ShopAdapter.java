package com.example.firozsaifi.azoolp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Shop> adsArrayList;
    String root= "http://azool.ae/multimedia/dealer/";

    public ShopAdapter(Context mContext, ArrayList<Shop> adsArrayList) {
        this.mContext = mContext;
        this.adsArrayList = adsArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_shop_ads, parent,false);
        return new AdsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Shop shop = adsArrayList.get(position);
        AdsHolder holder = (AdsHolder) viewHolder;

        if (shop.getCompanyName() != null && !shop.getCompanyName().isEmpty()) {
            holder.txt_name_english.setText(shop.getCompanyName());
        } else{
            holder.txt_name_english.setText(shop.getCompanyNameArabic());
        }

        if (shop.getSparePart() != null && !shop.getSparePart().isEmpty()) {
            holder.txt_spare_parts.setText("Spare Parts ("+shop.getSparePart()+")");
        } else{
            holder.txt_spare_parts.setText(0);
        }

        if (shop.getAccidentCar() != null && !shop.getAccidentCar().isEmpty()) {
            holder.txt_accident_car.setText("Accident Car ("+shop.getAccidentCar()+")");
        } else{
            holder.txt_accident_car.setText(0);
        }



        /*if (shop.getCompanyNameArabic() != null && !shop.getCompanyNameArabic().isEmpty()) {
            holder.txt_name_arabic.setText(shop.getCompanyNameArabic());
        }*/

        if (mContext != null){
            //Glide.with(mContext).load(ads.getImage()).into(holder.img_ads);
            Picasso.get().load(root+shop.getImage()).into(holder.img_ads);
        }

    }

    @Override
    public int getItemCount() {
        return adsArrayList.size();
    }

    private class AdsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView img_ads;
        TextView txt_name_english;
        TextView txt_spare_parts;
        TextView txt_accident_car;
        TextView txt_visit_shop;
        LinearLayout itemLayout;


        public AdsHolder(View itemView) {
            super(itemView);
            img_ads=itemView.findViewById(R.id.img_ads_item);
            txt_name_english=itemView.findViewById(R.id.txt_name_english);
            txt_spare_parts= itemView.findViewById(R.id.txt_spare_parts);
            txt_accident_car= itemView.findViewById(R.id.txt_accident_car);
            txt_visit_shop=itemView.findViewById(R.id.txt_view_shop);
            itemLayout=itemView.findViewById(R.id.item_shop_ads);

            txt_visit_shop.setOnClickListener(this);
            itemLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==itemLayout)
            {
                if(mContext!=null)
                {
                    try
                    {
                        MainActivity mainActivity=(MainActivity)mContext;
                        mainActivity.visitShop(adsArrayList.get(getAdapterPosition()).getId());

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            if(v==txt_visit_shop)
            {
                if(mContext!=null)
                {
                    try
                    {
                        MainActivity mainActivity=(MainActivity)mContext;
                        mainActivity.visitShop(adsArrayList.get(getAdapterPosition()).getId());

                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

}
