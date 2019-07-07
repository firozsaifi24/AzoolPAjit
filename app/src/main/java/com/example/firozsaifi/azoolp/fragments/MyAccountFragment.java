package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.SessionManager;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class MyAccountFragment extends Fragment implements View.OnClickListener {

    //my objects
    ImageView img_more;
    TextView txt_show_more;
    LinearLayout cards_layout, show_more_layout;
    boolean showMore=false;

    LinearLayout shop_layout, add_product_layout, view_product_layout, edit_profile_layout, change_password_layout, logout_layout;

    TextView txt_welcome;
    private SessionManager session;
    ImageView profile_picture_edit, img_profile;


    //Image request code
    private int PICK_IMAGE_REQUEST_CODE = 1;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    //image onject ends here

    CustomRequest customRequest;
    ProgressDialog progressDialog;  //for image uploading service

    //
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);
        txt_welcome= view.findViewById(R.id.tv2);
        profile_picture_edit= view.findViewById(R.id.img_profile_edit);
        img_profile= view.findViewById(R.id.img_profile_photo);
        shop_layout= view.findViewById(R.id.card_shop);
        add_product_layout= view.findViewById(R.id.card_add_product);
        view_product_layout= view.findViewById(R.id.card_view_product);
        edit_profile_layout= view.findViewById(R.id.card_edit_profile);
        change_password_layout= view.findViewById(R.id.card_change_password);
        logout_layout= view.findViewById(R.id.card_logout);

        img_more= view.findViewById(R.id.img_more);
        txt_show_more= view.findViewById(R.id.txt_more);
        cards_layout= view.findViewById(R.id.cards_layout);
        show_more_layout= view.findViewById(R.id.card_more);
        show_more_layout.setOnClickListener(this);

        // Session manager
        session = new SessionManager(getActivity());
        String welcomeName  = session.getName();
        welcomeName = welcomeName.substring(0,1).toUpperCase() + welcomeName.substring(1).toLowerCase();
        txt_welcome.setText("Hi, " + welcomeName);

        shop_layout.setOnClickListener(this);
        add_product_layout.setOnClickListener(this);
        view_product_layout.setOnClickListener(this);
        edit_profile_layout.setOnClickListener(this);
        change_password_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        profile_picture_edit.setOnClickListener(this);

        initProgressDialog();
        customRequest= new CustomRequest();

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
        if(v==shop_layout)
        {
            if(getActivity()!=null)
            {
               /* try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.visitShop(adsArrayList.get(getAdapterPosition()).getId());

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }*/
            }
        }

        if(v==add_product_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.addProduct();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==view_product_layout)
        {

            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.viewProduct();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

        }

        if(v==edit_profile_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.profileEdit();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==change_password_layout)
        {
            if(getActivity()!=null)
            {
                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.changePaswword();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==logout_layout)
        {

            if(getActivity()!=null)
            {

                session.setLogin(false);
                session.clear();

                Toast.makeText(getActivity(),"Logout Successfull!", Toast.LENGTH_LONG).show();

                try
                {
                    MainActivity mainActivity=(MainActivity)getActivity();
                    mainActivity.logout();

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(v==profile_picture_edit)
        {
            showFileChooser();
        }

        if(v==show_more_layout)
        {
            if(showMore)
            {
                showMore=false;
                img_more.setImageResource(R.drawable.ic_show_more_orange_100dp);
                txt_show_more.setText("Show More");
                cards_layout.setVisibility(View.GONE);
            }
            else
            {
                showMore=true;
                img_more.setImageResource(R.drawable.ic_show_less_orange_100dp);
                txt_show_more.setText("Show Less");
                cards_layout.setVisibility(View.VISIBLE);
            }

        }
    }

    private void showFileChooser()
    {
        Intent intent= new Intent();
        intent.setType("image/*");  //it will show only images in the picker
        intent.setAction(Intent.ACTION_GET_CONTENT);  //to get the contents
        //it will call the onActivityResult overriden method to handle the file chooser
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_IMAGE_REQUEST_CODE);

    }

    ///handling the request after user pick the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //image path from the storage
            filePath= data.getData();
            try
            {
                //storing the image into bitmap object
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath);
                Log.d("Uri path", filePath.toString());
                bitmap= Bitmap.createScaledBitmap(bitmap,500, 500, false);
                Glide.with(getActivity()).load(bitmap).into(img_profile);
                //Picasso.get().load(filePath).into(img_profile);
                //imgShow.setImageBitmap(bitmap);

                if(filePath!=null)
                {
                    //progressDialog.show();
                    String path= getPath(filePath);  //get the absolute path from the getPath function
                    Log.d("Absolute path", path);

                    Toast.makeText(getActivity(), "Profile picture changed successfully!", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }


    }

    //this method is used to get the absolute path of the selected image from the Uri path object.
    private String getPath(Uri uri)
    {

        Cursor cursor= getActivity().getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String document_id= cursor.getString(0);
        document_id= document_id.substring(document_id.lastIndexOf(":")+1);

        cursor= getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Images.Media._ID + " = ? ",new String[]{document_id},null);
        cursor.moveToFirst();
        String path= cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public void initProgressDialog()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setTitle("Uploading Image");
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);
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
