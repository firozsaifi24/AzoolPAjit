package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.adapter.HomeAdsAdapter;
import com.example.firozsaifi.azoolp.adapter.ProductViewAdapter;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnHomeCallback;
import com.example.firozsaifi.azoolp.interfaces.OnProductDeleteCallback;
import com.example.firozsaifi.azoolp.model.Ads;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.CustomProgressDialog;
import com.example.firozsaifi.azoolp.other.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewProductFragment extends Fragment implements View.OnClickListener, OnHomeCallback, OnProductDeleteCallback {

    //my objects

    private String dealerId;

    private SessionManager session;

    ImageView menu_btn;

    //recyclerview
    RecyclerView recyclerView_ads;

    //arraylist
    private ArrayList<Ads> adsList = new ArrayList<>();

    //adapters
    private ProductViewAdapter adsAdapter;

    CustomRequest customRequest;
    private ProgressDialog progressDialog; //loading icon
    private ProgressDialog pDialog; //loading icon with text

    //

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ViewProductFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ViewProductFragment newInstance(String param1, String param2) {
        ViewProductFragment fragment = new ViewProductFragment();
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
        View view= inflater.inflate(R.layout.fragment_view_product, container, false);
        menu_btn= view.findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(this);


        recyclerView_ads=view.findViewById(R.id.recycler_view_products);
        customRequest= new CustomRequest();
        // Session manager
        session = new SessionManager(getActivity());

        if(session.isLoggedIn())
        {
            dealerId= String.valueOf(session.getDealerId());
        }

        GridLayoutManager adsGridLayoutManager= new GridLayoutManager(getActivity(),1);
        recyclerView_ads.setLayoutManager(adsGridLayoutManager);
        recyclerView_ads.setScrollContainer(false);
        recyclerView_ads.setNestedScrollingEnabled(false);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        //pDialog.setMessage("Loading...");
        //showDialog();

        showProgressDialog();

        ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.viewProduct(Constants.URL_ADS_ALL_VIEW_BY_DEALER,this, dealerId));

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
        /*if (context instanceof OnFragmentInteractionListener) {
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
    public void onHomeSuccess(ArrayList<Ads> adsArrayList) {
        dismissProgressDialog();
        Log.e("onHomeSuccess","Reached here");
        Log.e("ads array list",adsArrayList.toString());

        adsList=adsArrayList;

        if(getActivity() != null)
        {
            adsAdapter= new ProductViewAdapter(getActivity(),adsList, this);
            recyclerView_ads.setAdapter(adsAdapter);
            Toast.makeText(getActivity(),"Showing Home Ads", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onHomeError(String error) {
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

    @Override
    public void onProductDelete(final int cid, final int adapterId) {
        Log.d("onProdDelete"," Deletable id: "+String.valueOf(cid));

        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are You Sure Want To Delete!");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                // Tag used to cancel the request
                String tag_string_req = "req_deleteList";

                pDialog.setMessage("Deleting...");
                showDialog();

                StringRequest strReq= new StringRequest(Request.Method.POST, Constants.URL_ADS_DELETE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Product Delete success", "Deletion Response: " + response);

                        adsList.remove(adapterId);

                        afterCompDelete();




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Complain Delete err", "Deletion Error: " + error.getMessage());
                        hideDialog();
                        if (error instanceof NetworkError) {
                            if(getActivity() !=null)
                            {
                                Toast.makeText(getActivity(),"Nn Internet Connection!",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (error != null && error.getMessage() != null) {
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("pid", String.valueOf(cid));

                        return params;
                    }
                };
                strReq.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                // Adding request to request queue
                ServiceRequest.getInstance(getActivity()).addToRequestQueue(strReq);


            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

    public void afterCompDelete()
    {

        if(adsAdapter != null)
        {
            adsAdapter.notifyDataSetChanged();
        }
        else
        {
            adsAdapter= new ProductViewAdapter(getActivity(), adsList,this);
            recyclerView_ads.setAdapter(adsAdapter);
            adsAdapter.notifyDataSetChanged();
        }
        hideDialog();
        Toast.makeText(getActivity(),"Product Deleted Successfully!",Toast.LENGTH_LONG).show();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
