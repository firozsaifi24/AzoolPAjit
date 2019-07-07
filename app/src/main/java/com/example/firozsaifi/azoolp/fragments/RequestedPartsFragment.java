package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.adapter.RequestPartAdsAdapter;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnRequestPartViewCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.model.ReqPartAds;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.CustomProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class RequestedPartsFragment extends Fragment implements View.OnClickListener, OnRequestPartViewCallback {

    //my objects
    ImageView menu_btn;

    private List<ReqPartAds> reqPartAdsList= new ArrayList<>();
    private RecyclerView recyclerView;
    private RequestPartAdsAdapter requestPartAdsAdapter;

    LinearLayout request_part_layout;

    CustomRequest customRequest;
    private ProgressDialog progressDialog;
    //ends here

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RequestedPartsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RequestedPartsFragment newInstance(String param1, String param2) {
        RequestedPartsFragment fragment = new RequestedPartsFragment();
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
        View view= inflater.inflate(R.layout.fragment_requested_parts, container, false);
        menu_btn= view.findViewById(R.id.menu_btn);
        request_part_layout= view.findViewById(R.id.request_parts_layout);
        request_part_layout.setOnClickListener(this);
        recyclerView= view.findViewById(R.id.recycler_requested_parts);
        menu_btn.setOnClickListener(this);

        RecyclerView.LayoutManager mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        customRequest= new CustomRequest();
        showProgressDialog();

        ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.requestPartView(Constants.URL_REQUEST_PART_VIEW,this));
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

        if(v==request_part_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.requestPart();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPartViewSuccess(ArrayList<ReqPartAds> adsArrayList) {
        dismissProgressDialog();
        Log.e("ads array list",adsArrayList.toString());

        reqPartAdsList=adsArrayList;

        if(getActivity()!=null)
        {
            requestPartAdsAdapter= new RequestPartAdsAdapter(getActivity(),reqPartAdsList, getActivity().getSupportFragmentManager());

            recyclerView.setAdapter(requestPartAdsAdapter);
            requestPartAdsAdapter.notifyDataSetChanged();

            Toast.makeText(getActivity(),"Showing Request Part Ads", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPartViewError(String error) {
        dismissProgressDialog();
        Toast.makeText(getActivity(),"Some Error occurred", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNetworkError() {
        dismissProgressDialog();
        Toast.makeText(getActivity(),"No Internet Connection!", Toast.LENGTH_LONG).show();
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
