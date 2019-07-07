package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.adapter.BrandBaseAdapter;
import com.example.firozsaifi.azoolp.adapter.HomeAdsAdapter;
import com.example.firozsaifi.azoolp.adapter.ModelBaseAdapter;
import com.example.firozsaifi.azoolp.adapter.ShopAdapter;
import com.example.firozsaifi.azoolp.adapter.YearBaseAdapter;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnBrandCallback;
import com.example.firozsaifi.azoolp.interfaces.OnModelCallback;
import com.example.firozsaifi.azoolp.interfaces.OnShopCallback;
import com.example.firozsaifi.azoolp.interfaces.OnYearCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.model.Brand;
import com.example.firozsaifi.azoolp.model.Model;
import com.example.firozsaifi.azoolp.model.Shop;
import com.example.firozsaifi.azoolp.model.Year;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.CustomProgressDialog;

import java.util.ArrayList;


public class ShopFragment extends Fragment implements View.OnClickListener, OnBrandCallback, OnModelCallback, OnYearCallback, OnShopCallback {



    LinearLayout brand, model, year;
    TextView txt_brand, txt_model, txt_year;

    ListPopupWindow listPopupWindow;

    //recyclerview
    RecyclerView recyclerView_ads;

    //arraylist
    private ArrayList<Shop> adsList = new ArrayList<>();

    //adapters
    private ShopAdapter shopAdapter;


    CustomRequest customRequest;
    private ProgressDialog progressDialog;

    private String selectedBrandId;
    private String selectedModelId;
    private String selectedYearId;
    private String selectedCatId;
    private String selectedSubCatId;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shop, container, false);
        brand=view.findViewById(R.id.brand_layout);
        model=view.findViewById(R.id.model_layout);
        year=view.findViewById(R.id.year_layout);

        txt_brand=view.findViewById(R.id.txt_brand);
        txt_model=view.findViewById(R.id.txt_model);
        txt_year=view.findViewById(R.id.txt_year);

        brand.setOnClickListener(this);
        model.setOnClickListener(this);
        year.setOnClickListener(this);

        recyclerView_ads=view.findViewById(R.id.recycler_shop_ads);
        customRequest= new CustomRequest();

        GridLayoutManager adsGridLayoutManager= new GridLayoutManager(getActivity(),2);
        recyclerView_ads.setLayoutManager(adsGridLayoutManager);
        recyclerView_ads.setScrollContainer(false);
        recyclerView_ads.setNestedScrollingEnabled(false);

        showProgressDialog();

        ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.shop(Constants.URL_SHOP_VIEW,this));

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
        if(v==brand)
        {

            showProgressDialog();
            ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.brandView(Constants.URL_BRAND, this));
        }

        if(v==model)
        {
            if(selectedBrandId!=null)
            {
                showProgressDialog();
                ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.modelView(Constants.URL_MODEL, this, selectedBrandId));

            }
            else
            {
                Toast.makeText(getActivity(),"Please select a Brand first", Toast.LENGTH_LONG).show();
            }

        }

        if(v==year)
        {

            if(selectedModelId!=null)
            {
                showProgressDialog();
                ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.yearView(Constants.URL_YEAR, this));
            }
            else
            {
                Toast.makeText(getActivity(),"Please select a model first", Toast.LENGTH_LONG).show();
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
    public void onBrandSuccess(final ArrayList<Brand> brandList) {
        dismissProgressDialog();
            if (brandList.size() > 0) {
            listPopupWindow = new ListPopupWindow(getActivity());
            //listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,brandLi));
            BrandBaseAdapter brandBaseAdapter = new BrandBaseAdapter(brandList);
            listPopupWindow.setAdapter(brandBaseAdapter);
            listPopupWindow.setAnchorView(brand);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (brandList.size() > position) {
                        Brand selection = brandList.get(position);
                        txt_brand.setText(selection.getTitle());
                        selectedBrandId = String.valueOf(selection.getId());
                        Log.d("selected brand id", selectedBrandId);
                        txt_model.setText("Model");
                        selectedModelId= null;
                        txt_year.setText("Year");
                        selectedYearId = null;
                        listPopupWindow.dismiss();
                    }
                }
            });

            listPopupWindow.show();
            //Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getActivity(),"Sorry, No Brand Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBrandError(String error) {
        dismissProgressDialog();
        if (error != null && !error.isEmpty()){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onModelSuccess(final ArrayList<Model> modelList) {
        dismissProgressDialog();

        if (modelList.size() > 0) {
            listPopupWindow = new ListPopupWindow(getActivity());
            //listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,brandLi));
            ModelBaseAdapter modelBaseAdapter = new ModelBaseAdapter(modelList);
            listPopupWindow.setAdapter(modelBaseAdapter);
            listPopupWindow.setAnchorView(model);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (modelList.size() > position) {
                        Model selection = modelList.get(position);
                        txt_model.setText(selection.getTitle());
                        selectedModelId = String.valueOf(selection.getId());
                        Log.d("selected model id", selectedModelId);
                        txt_year.setText("Year");
                        selectedYearId = null;
                        listPopupWindow.dismiss();
                    }
                }
            });

            listPopupWindow.show();
            //Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getActivity(),"Sorry, No Model Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onModelError(String error) {
        dismissProgressDialog();
        if (error != null && !error.isEmpty()){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onYearSuccess(final ArrayList<Year> yearList) {
        dismissProgressDialog();
        if (yearList.size() > 0) {
            listPopupWindow = new ListPopupWindow(getActivity());
            //listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,brandLi));
            YearBaseAdapter yearBaseAdapter = new YearBaseAdapter(yearList);
            listPopupWindow.setAdapter(yearBaseAdapter);
            listPopupWindow.setAnchorView(year);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (yearList.size() > position) {
                        Year selection = yearList.get(position);
                        txt_year.setText(selection.getTitle());
                        selectedYearId = String.valueOf(selection.getId());
                        Log.d("selected year id", selectedYearId);
                        listPopupWindow.dismiss();
                    }
                }
            });

            listPopupWindow.show();
            //Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getActivity(),"Sorry, No Year Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onYearError(String error) {
        dismissProgressDialog();
        if (error != null && !error.isEmpty()){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onShopSuccess(ArrayList<Shop> adsArrayList) {
        dismissProgressDialog();
        Log.e("onShopSuccess","Reached here");
        Log.e("ads array list",adsArrayList.toString());

        adsList=adsArrayList;

        if(getActivity() != null)
        {
            shopAdapter= new ShopAdapter(getActivity(),adsList);
            recyclerView_ads.setAdapter(shopAdapter);
            Toast.makeText(getActivity(),"Showing Shop", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onShopError(String error) {
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
