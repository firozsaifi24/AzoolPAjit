package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.example.firozsaifi.azoolp.interfaces.OnShopDetailsCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.CustomProgressDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class DealerFragment extends Fragment implements View.OnClickListener, OnShopDetailsCallback {

    ScrollView content_layout;
    LinearLayout try_again_layout;

    //myobjects

    ImageView menu_btn;

    //ends here
    String dealerId;

    String globalAddress;
    String globalPhone;

    ImageView imageView;
    TextView txt_name_english, txt_name_arabic;
    TextView txt_dealer_name, txt_phone;
    ImageView img_dealer_profile_image;
    LinearLayout address_layout, call_layout;

    CustomRequest customRequest;
    private ProgressDialog progressDialog;

    //arraylist
    private ArrayList<Ads> carList = new ArrayList<>();
    private ArrayList<Ads> partList = new ArrayList<>();
    private ArrayList<Ads> allProductList = new ArrayList<>();

    //recyclerview
    RecyclerView recyclerView_ads;

    //adapters
    private AccidentAdsAdapter adsAccidentAdapter;
    private PartAdsAdapter adsPartAdapter;
    private HomeAdsAdapter adsHomeAdapter;

    LinearLayout all_product_layout, spare_parts_layout, accident_cars_layout;
    View all_line, spare_line, accident_line;

    String root= "http://azool.ae/multimedia/dealer/";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DealerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DealerFragment newInstance(String param1, String param2) {
        DealerFragment fragment = new DealerFragment();
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
            dealerId= getArguments().getString("dealer_id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dealer, container, false);
        menu_btn= view.findViewById(R.id.menu_btn);

        imageView= view.findViewById(R.id.imageView);
        txt_name_english= view.findViewById(R.id.txt_name_english);
        txt_name_arabic= view.findViewById(R.id.txt_name_arabic);
        txt_dealer_name= view.findViewById(R.id.txt_dealer_name);
        txt_phone= view.findViewById(R.id.txt_phone_number);
        img_dealer_profile_image= view.findViewById(R.id.img_dealer_profile_image);
        address_layout= view.findViewById(R.id.address_layout);
        call_layout= view.findViewById(R.id.call_now_layout);

        all_product_layout= view.findViewById(R.id.dealer_all_products);
        spare_parts_layout= view.findViewById(R.id.dealer_spare_parts);
        accident_cars_layout= view.findViewById(R.id.dealer_accident_cars);
        all_line= view.findViewById(R.id.all_line);
        spare_line= view.findViewById(R.id.spare_line);
        accident_line= view.findViewById(R.id.accident_line);

        content_layout= view.findViewById(R.id.content_layout);
        try_again_layout= view.findViewById(R.id.try_again_layout);


        recyclerView_ads= view.findViewById(R.id.recycler_dealer_ads);
        GridLayoutManager adsGridLayoutManager= new GridLayoutManager(getActivity(),2);
        recyclerView_ads.setLayoutManager(adsGridLayoutManager);
        recyclerView_ads.setScrollContainer(false);
        recyclerView_ads.setNestedScrollingEnabled(false);

        menu_btn.setOnClickListener(this);
        address_layout.setOnClickListener(this);
        call_layout.setOnClickListener(this);

        all_product_layout.setOnClickListener(this);
        spare_parts_layout.setOnClickListener(this);
        accident_cars_layout.setOnClickListener(this);
        try_again_layout.setOnClickListener(this);

        customRequest= new CustomRequest();

        showProgressDialog();

        ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.shopDetail(Constants.URL_SHOP_DETAILS_VIEW, dealerId,this));

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

        if(v==call_layout)
        {
            /*Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:"+globalPhone));
            startActivity(callIntent);*/
        }

        if(v==all_product_layout)
        {
            all_line.setVisibility(View.VISIBLE);
            accident_line.setVisibility(View.GONE);
            spare_line.setVisibility(View.GONE);

            if(getActivity() != null)
            {

                    adsHomeAdapter= new HomeAdsAdapter(getActivity(),allProductList);
                    recyclerView_ads.setAdapter(adsHomeAdapter);
                    Toast.makeText(getActivity(),"Showing Home Ads", Toast.LENGTH_LONG).show();

            }

        }

        if(v==spare_parts_layout)
        {
            spare_line.setVisibility(View.VISIBLE);
            all_line.setVisibility(View.GONE);
            accident_line.setVisibility(View.GONE);

            if(getActivity() != null)
            {

                    adsPartAdapter= new PartAdsAdapter(getActivity(),partList);
                    recyclerView_ads.setAdapter(adsPartAdapter);
                    Toast.makeText(getActivity(),"Showing Home Ads", Toast.LENGTH_LONG).show();

            }
        }

        if(v==accident_cars_layout)
        {
            accident_line.setVisibility(View.VISIBLE);
            all_line.setVisibility(View.GONE);
            spare_line.setVisibility(View.GONE);

            if(getActivity() != null)
            {
                    adsAccidentAdapter= new AccidentAdsAdapter(getActivity(),carList);
                    recyclerView_ads.setAdapter(adsAccidentAdapter);
                    Toast.makeText(getActivity(),"Showing Accident Ads", Toast.LENGTH_LONG).show();

            }
        }

        if(v==try_again_layout)
        {
            //super.onStart();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

            Log.d("DealerId", String.valueOf(dealerId));
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
    public void onShopDetailSuccess(JSONObject shopDetail, ArrayList<Ads> carArrayList, ArrayList<Ads> spareArrayList, ArrayList<Ads> allProductArrayList) {
        Log.e("onShop DetailSuccess","Reached here");
        Log.e("Ads Details array",shopDetail.toString());

        carList= carArrayList;
        partList= spareArrayList;
        allProductList= allProductArrayList;
        Log.d("CAR", String.valueOf(carList.size()));
        Log.d("PART", String.valueOf(partList.size()));
        Log.d("ALLPRODUCT", String.valueOf(allProductList.size()));

        if(getActivity() != null)
        {

            adsHomeAdapter= new HomeAdsAdapter(getActivity(),allProductList);
            recyclerView_ads.setAdapter(adsHomeAdapter);
            Toast.makeText(getActivity(),"Showing Home Ads", Toast.LENGTH_LONG).show();

        }

  /*      if(carList.size() != 0)
        {
            if(getActivity() != null) {
                adsAdapter = new RelatedAccidentAdsAdapter(getActivity(), adsList);
                recyclerView_related_cars.setAdapter(adsAdapter);
                Toast.makeText(getActivity(), "Showing Accident Ads", Toast.LENGTH_LONG).show();
            }
        }*/

        JSONObject jObj = null;

        try{
            jObj= shopDetail.getJSONObject("allshopdetailsdata");
            int id= jObj.getInt("id"); //dealer id
            String fname= jObj.getString("fname");
            String cname= jObj.getString("cname");
            String arabicName= jObj.getString("arabic_name");
            String email= jObj.getString("email");
            String phone = jObj.getString("phone");
            globalPhone= phone;
            String whatsapp_no = jObj.getString("whatsapp_no");
            String address = jObj.getString("address");
            globalAddress=address;
            String description = jObj.getString("description");

            JSONArray images = jObj.getJSONArray("images");

            Log.d("Images Array", images.toString());
            //Log.d("JSON Arr list", images.getString(0)+ images.get(1).toString() + images.get(0).toString());
            Log.d("Image length", String.valueOf(images.length()));

            txt_dealer_name.setText(fname);
            txt_name_english.setText(cname);
            txt_name_arabic.setText(arabicName);
            txt_phone.setText(phone);

          if(images.length()>1)
            {
                Picasso.get().load(root+images.get(0)).into(imageView);
            }
            else
          {
              Picasso.get().load(R.drawable.no_image_found).into(imageView);
          }


            Log.d("Session data", dealerId+" and "+fname+" and "+ cname +" and"+arabicName + " and " + email + " and " + phone
                    + " and " + whatsapp_no + " and " + address + " and " + description );

            //Toast.makeText(getActivity(),"Showing Shop details",Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        if(getActivity() != null)
        {
            /*adsAdapter= new HomeAdsAdapter(getActivity(),adsList);
            recyclerView_ads.setAdapter(adsAdapter);*/
            Toast.makeText(getActivity(),"Showing Shop Details", Toast.LENGTH_LONG).show();
        }

        try_again_layout.setVisibility(View.GONE);
        content_layout.setVisibility(View.VISIBLE);

        dismissProgressDialog();
    }

    @Override
    public void onShopDetailError(String error) {
        content_layout.setVisibility(View.GONE);
        try_again_layout.setVisibility(View.VISIBLE);

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
        content_layout.setVisibility(View.GONE);
        try_again_layout.setVisibility(View.VISIBLE);

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
