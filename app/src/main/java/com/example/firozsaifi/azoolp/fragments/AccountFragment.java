package com.example.firozsaifi.azoolp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.LoginActivity;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.activity.RegisterActivity;


public class AccountFragment extends Fragment implements View.OnClickListener {

    TextView txt_signin, txt_signup;

    //three cards
    LinearLayout accident_car_layout, spare_parts_layout, shop_layout, share_us_layout;
    //ends here

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        txt_signin= view.findViewById(R.id.txt_signin);
        txt_signup= view.findViewById(R.id.txt_signup);
        accident_car_layout= view.findViewById(R.id.card_accident_car);
        spare_parts_layout= view.findViewById(R.id.card_spare_parts);
        shop_layout= view.findViewById(R.id.card_shop);
        share_us_layout= view.findViewById(R.id.card_share);

        txt_signin.setOnClickListener(this);
        txt_signup.setOnClickListener(this);
        accident_car_layout.setOnClickListener(this);
        spare_parts_layout.setOnClickListener(this);
        shop_layout.setOnClickListener(this);
        share_us_layout.setOnClickListener(this);
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
       /* if (context instanceof OnFragmentInteractionListener) {
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
        if(v==txt_signin)
        {
            Intent i= new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
        }

        if(v==txt_signup)
        {
            Intent i= new Intent(getActivity(), RegisterActivity.class);
            startActivity(i);
        }

        if(v==accident_car_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.accidentCar();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==spare_parts_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.spareParts();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==shop_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.shops();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==share_us_layout)
        {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Azool Pvt. Ltd.");
                String sAux = "\nCheckout Our Application. We have great deals for cars and spare parts.\n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.a2solutions.azool \n\n";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Choose One"));
            } catch(Exception e) {
                //e.toString();
            }
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
