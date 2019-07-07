package com.example.firozsaifi.azoolp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.fragments.DescriptionDialogFragment;
import com.example.firozsaifi.azoolp.fragments.RequestedPartsFragment;
import com.example.firozsaifi.azoolp.model.ReqPartAds;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RequestPartAdsAdapter extends RecyclerView.Adapter<RequestPartAdsAdapter.MyViewHolder> {

    Context ctx;
    private List<ReqPartAds> reqPartAdsList;
    FragmentManager manager;
    String root= "http://azool.ae/multimedia/requestparts/";

    public RequestPartAdsAdapter(Context ctx, List<ReqPartAds> reqPartAdsList, FragmentManager manager) {
        this.ctx = ctx;
        this.reqPartAdsList = reqPartAdsList;
        this.manager=manager;
    }

    @NonNull
    @Override
    public RequestPartAdsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Bind movie_list_row.xml file with the View that is holded by Viewholder.
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_requested_parts,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestPartAdsAdapter.MyViewHolder holder, int position) {

        //set appropriate movie data (title, genre and year) to each row.
        ReqPartAds reqPartAds= reqPartAdsList.get(position);
        holder.txt_customername.setText(reqPartAds.getCustomername());
        holder.txt_brand.setText(reqPartAds.getBrand());
        holder.txt_model.setText(reqPartAds.getModel());
        holder.txt_year.setText(reqPartAds.getYear());
        holder.txt_description.setText(reqPartAds.getDescription());
        holder.txt_call.setText(reqPartAds.getPhone());


        try {
            Picasso.get().load(root+reqPartAds.getImage()).into(holder.image);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return reqPartAdsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;
        TextView txt_customername, txt_brand, txt_model, txt_year, txt_description, txt_show_more, txt_call;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt_customername= itemView.findViewById(R.id.txt_customer_name);
            txt_brand= itemView.findViewById(R.id.txt_brand);
            txt_model= itemView.findViewById(R.id.txt_model);
            txt_year= itemView.findViewById(R.id.txt_year);
            txt_description= itemView.findViewById(R.id.txt_description);
            txt_show_more= itemView.findViewById(R.id.txt_show_more);
            txt_call= itemView.findViewById(R.id.txt_call);
            image= itemView.findViewById(R.id.img_ads_item);
            txt_show_more.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v==txt_show_more)
            {
                DescriptionDialogFragment.newInstance("param1", reqPartAdsList.get(getAdapterPosition()).getDescription()).show(manager, null);
                //Toast.makeText(ctx,"Cliked", Toast.LENGTH_LONG).show();
            }
        }
    }
}
