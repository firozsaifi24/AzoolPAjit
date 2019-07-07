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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.interfaces.OnRequestPartAddCallback;
import com.example.firozsaifi.azoolp.other.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import static android.app.Activity.RESULT_OK;


public class RequestPartFragment extends Fragment implements View.OnClickListener, OnRequestPartAddCallback {

    //my objects
    ImageView menu_btn;

    EditText et_brand, et_model, et_year, et_customer_name, et_phone, et_description;
    ImageView image;
    Button btn_req_send;
    CustomRequest customRequest;
    ProgressDialog progressDialog;
    private ProgressDialog mProgressDialog;  //for image uploading service

    //Image request code
    private int PICK_IMAGE_REQUEST_CODE = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    //image onject ends here

    //ends here

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RequestPartFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RequestPartFragment newInstance(String param1, String param2) {
        RequestPartFragment fragment = new RequestPartFragment();
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
        View view= inflater.inflate(R.layout.fragment_request_part, container, false);
        menu_btn= view.findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(this);
        et_brand= view.findViewById(R.id.etxt_req_brand);
        et_model= view.findViewById(R.id.etxt_req_model);
        et_year= view.findViewById(R.id.etxt_req_year);
        et_customer_name= view.findViewById(R.id.etxt_req_customer_name);
        et_phone= view.findViewById(R.id.etxt_req_phone);
        et_description= view.findViewById(R.id.etxt_req_description);
        btn_req_send= view.findViewById(R.id.btn_req_send);
        image= view.findViewById(R.id.img_req_image);

        image.setOnClickListener(this);
        btn_req_send.setOnClickListener(this);

        //initProgressDialog();
        mProgressDialog= new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);

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
     /*   if (context instanceof OnFragmentInteractionListener) {
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

        if(v==image)
        {
            showFileChooser();
        }

        if(v==btn_req_send)
        {
            String brand= et_brand.getText().toString().trim();
            String model= et_model.getText().toString().trim();
            String year= et_year.getText().toString().trim();
            String customer_name= et_customer_name.getText().toString().trim();
            String phone= et_phone.getText().toString().trim();
            String description= et_description.getText().toString().trim();

            if(!brand.isEmpty())
            {
                if(!model.isEmpty())
                {
                    if(!year.isEmpty())
                    {
                        if(!customer_name.isEmpty())
                        {
                            if(!phone.isEmpty())
                            {
                                if(!description.isEmpty())
                                {
                                    if(filePath!=null)
                                    {
                                        //progressDialog.show();
                                        mProgressDialog.setMessage("Sending Request.. Please Wait...");
                                        mProgressDialog.show();

                                        String path= getPath(filePath);  //get the absolute path from the getPath function
                                        Log.d("Absolute path", path);

                                        customRequest.requestPartAdd(Constants.URL_REQUEST_PART_ADD,getActivity(), this, path, brand, model, year, customer_name, phone, description);

                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                    }

                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Please enter description", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(getActivity(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please enter year", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(), "Please enter model", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getActivity(), "Please enter brand", Toast.LENGTH_SHORT).show();
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
                //Picasso.get().load(filePath).into(image);
                Glide.with(getActivity()).load(bitmap).into(image);
                //image.setImageBitmap(bitmap);
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

    @Override
    public void onRequestPartAddSuccess(JSONObject credentials) {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(),"Part Request Sent Successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPartAddError(String error) {
        mProgressDialog.dismiss();
        if (error != null && !error.isEmpty()){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNetworkError() {
        mProgressDialog.dismiss();
        Toast.makeText(getActivity(),"No Internet Connection!", Toast.LENGTH_LONG).show();
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
