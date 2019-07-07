package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.adapter.AccidentAdsAdapter;
import com.example.firozsaifi.azoolp.adapter.HomeAdsAdapter;
import com.example.firozsaifi.azoolp.adapter.PartAdsAdapter;
import com.example.firozsaifi.azoolp.adapter.RelatedAccidentAdsAdapter;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnAdsDetailCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.CustomProgressDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class AccidentCarDetailsFragment extends Fragment implements View.OnClickListener, OnAdsDetailCallback {

    //my objects
    ImageView menu_btn;
    //ends here
    String productId;
    String dealerId; //dealer id for visiting dealer

    //arraylist
    //private ArrayList<Ads> adsList = new ArrayList<>();
    //private ArrayList<String> imageList = new ArrayList<>();
    CustomRequest customRequest;
    private ProgressDialog progressDialog;

    ImageView imgDisplay, imgOne, imgTwo, imgThree;
    TextView txt_name, txt_brand, txt_model, txt_year, txt_date, txt_price, txt_description;
    String root= "http://azool.ae/multimedia/products/";

    //related products
    //recyclerview
    RecyclerView recyclerView_related_cars;
    //arraylist
    private ArrayList<Ads> adsList = new ArrayList<>();
    //adapters
    private RelatedAccidentAdsAdapter adsAdapter;

    LinearLayout visit_shop_layout;

    LinearLayout img_one_layout, img_two_layout, img_three_layout;

    boolean imageOneFound=true;
    boolean imageTwoFound=false;
    boolean imageThreeFound=false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccidentCarDetailsFragment() {
        // Required empty public constructor
    }

   
    // TODO: Rename and change types and number of parameters
    public static AccidentCarDetailsFragment newInstance(String param1, String param2) {
        AccidentCarDetailsFragment fragment = new AccidentCarDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            productId= getArguments().getString("product_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_accident_car_details, container, false);
        menu_btn= view.findViewById(R.id.menu_btn);
        imgDisplay= view.findViewById(R.id.img_image_display);
        imgOne= view.findViewById(R.id.img_image_one);
        imgTwo= view.findViewById(R.id.img_image_two);
        imgThree= view.findViewById(R.id.img_image_three);
        txt_brand= view.findViewById(R.id.txt_brand);
        txt_model= view.findViewById(R.id.txt_model);
        txt_year= view.findViewById(R.id.txt_year);
        txt_date=view.findViewById(R.id.txt_date);
        txt_price= view.findViewById(R.id.txt_price);
        txt_description= view.findViewById(R.id.txt_description);
        txt_name= view.findViewById(R.id.txt_name);

        img_one_layout=view.findViewById(R.id.img_one_layout);
        img_two_layout=view.findViewById(R.id.img_two_layout);
        img_three_layout=view.findViewById(R.id.img_three_layout);

        visit_shop_layout= view.findViewById(R.id.visit_shop_layout);

        recyclerView_related_cars= view.findViewById(R.id.recycler_related_accident_car);

        //horizontal layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //grid layout manager
        //GridLayoutManager adsGridLayoutManager= new GridLayoutManager(getActivity(),2);
        recyclerView_related_cars.setLayoutManager(mLayoutManager);
        recyclerView_related_cars.setScrollContainer(false);
        recyclerView_related_cars.setNestedScrollingEnabled(false);

        menu_btn.setOnClickListener(this);
        visit_shop_layout.setOnClickListener(this);
        imgOne.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        imgThree.setOnClickListener(this);
        customRequest= new CustomRequest();

        showProgressDialog();

        ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.adsDetail(Constants.URL_ADS_DETAILS_VIEW, productId,this));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v==menu_btn)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.onBackPressed();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==visit_shop_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.visitShop(dealerId);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==imgOne)
        {
            if(imageOneFound || imageTwoFound || imageThreeFound)
            {
                imgDisplay.setImageDrawable(imgOne.getDrawable());
                img_one_layout.setBackgroundResource(R.drawable.border2selected);
                img_two_layout.setBackgroundResource(R.drawable.border2);
                img_three_layout.setBackgroundResource(R.drawable.border2);

                //Toast.makeText(getActivity(), "Image One selected", Toast.LENGTH_LONG).show();
            }

        }

        if(v==imgTwo)
        {
            if(imageTwoFound || imageThreeFound)
            {
                imgDisplay.setImageDrawable(imgTwo.getDrawable());
                img_two_layout.setBackgroundResource(R.drawable.border2selected);
                img_one_layout.setBackgroundResource(R.drawable.border2);
                img_three_layout.setBackgroundResource(R.drawable.border2);

                //Toast.makeText(getActivity(), "Image Two selected", Toast.LENGTH_LONG).show();
            }
        }

        if(v==imgThree)
        {
            if(imageThreeFound)
            {
                imgDisplay.setImageDrawable(imgThree.getDrawable());
                img_three_layout.setBackgroundResource(R.drawable.border2selected);
                img_one_layout.setBackgroundResource(R.drawable.border2);
                img_two_layout.setBackgroundResource(R.drawable.border2);

                //Toast.makeText(getActivity(), "Image Three selected", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void showProgressDialog(){
        if (getActivity() != null) {
            if (progressDialog == null) {
                progressDialog = CustomProgressDialog.createProgressDialog(getActivity(), false);
            } else {
                progressDialog.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void onAdsDetailSuccess(JSONObject adsDetail, ArrayList<Ads> carArrayList, ArrayList<Ads> spareArrayList) {

        Log.e("onAds DetailSuccess","Reached here");
        Log.e("Ads Details array",adsDetail.toString());

        adsList= carArrayList;
        Log.d("CAR", String.valueOf(adsList.size()));

        if(adsList.size() != 0)
        {
            if(getActivity() != null) {
                adsAdapter = new RelatedAccidentAdsAdapter(getActivity(), adsList);
                recyclerView_related_cars.setAdapter(adsAdapter);
                Toast.makeText(getActivity(), "Showing Accident Ads", Toast.LENGTH_LONG).show();
            }
        }

        JSONObject jObj = null;

        try{
            jObj= adsDetail.getJSONObject("alladsdetailsdata");
            int productid= jObj.getInt("id");
            String name= jObj.getString("name");
            String arabicName= jObj.getString("arabic_name");
            String dealerid= jObj.getString("dealer_id");
            dealerId= dealerid;
            String brand = jObj.getString("brand");
            String model = jObj.getString("model");
            String year = jObj.getString("year");
            String category = jObj.getString("category");
            String date = jObj.getString("date_added");
            String partType = jObj.getString("part_type");
            String description = jObj.getString("description");
            String price = jObj.getString("price");
            String views = jObj.getString("views");
            JSONArray images = jObj.getJSONArray("images");

            Log.d("Images Array", images.toString());
            //Log.d("JSON Arr list", images.getString(0)+ images.get(1).toString() + images.get(0).toString());
            Log.d("Image length", String.valueOf(images.length()));

            if(name!=null && !name.isEmpty())
            {
                txt_name.setText(name);
            }
            else
            {
                txt_name.setText(name);
            }

            txt_brand.setText(brand);
            txt_model.setText(model);
            txt_year.setText(year);
            txt_price.setText("AED " + price);
            txt_date.setText(date);
            txt_description.setText(description);

            if(images.length()==1)
            {
                Picasso.get().load(root+images.get(0)).into(imgDisplay);
                Picasso.get().load(root+images.get(0)).into(imgOne);
                Picasso.get().load(R.drawable.no_image_found).into(imgTwo);
                Picasso.get().load(R.drawable.no_image_found).into(imgThree);

                imageOneFound=true;
            }
            else if(images.length()==2)
            {
                Picasso.get().load(root+images.get(1)).into(imgDisplay);
                Picasso.get().load(root+images.get(1)).into(imgOne);
                Picasso.get().load(root+images.get(0)).into(imgTwo);
                Picasso.get().load(R.drawable.no_image_found).into(imgThree);

                imageTwoFound=true;
            }
            else if(images.length()==3)
            {
                Picasso.get().load(root+images.get(2)).into(imgDisplay);
                Picasso.get().load(root+images.get(2)).into(imgOne);
                Picasso.get().load(root+images.get(1)).into(imgTwo);
                Picasso.get().load(root+images.get(0)).into(imgThree);

                imageThreeFound=true;
            }

            /*ArrayList<String> arrayImg = new ArrayList<>();

                for (int i = 0; i < images.length(); i++) {
                //JSONObject eachAd = s.getJSONObject(i);
                arrayImg.add((String)images.get(i));
                //String c= (String)images.get(i);
                //imgList.add(s.get(i));
                //Log.d("JSON Arr list", c);
            }

            Log.d("Array list", arrayImg.toString());

            Log.d("Array separate", arrayImg.get(0)+arrayImg.get(1)+arrayImg.get(2));*/


            Log.d("Session data", productId+" and "+name+" and "+" and"+arabicName + " and " + dealerid + " and " + brand
                    + " and " + model + " and " + year + " and " + category + " and "+ date + " and "
                    + " and " + partType + " and " + description + " and " + price + " and " + views );

           // Toast.makeText(getActivity(),"Showing Ads details",Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        dismissProgressDialog();

        if(getActivity() != null)
        {
            /*adsAdapter= new HomeAdsAdapter(getActivity(),adsList);
            recyclerView_ads.setAdapter(adsAdapter);*/
            Toast.makeText(getActivity(),"Showing Ads Details", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onAdsDetailError(String error) {
        dismissProgressDialog();
        if(getActivity()!=null)
        {
            if (error != null && !error.isEmpty()){
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onNetworkError() {
        dismissProgressDialog();
        if(getActivity()!=null)
        {
            Toast.makeText(getActivity(),"No Internet Connection!", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
