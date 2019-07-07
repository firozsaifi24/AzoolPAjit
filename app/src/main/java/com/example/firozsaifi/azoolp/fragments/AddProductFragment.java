package com.example.firozsaifi.azoolp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.activity.MainActivity;
import com.example.firozsaifi.azoolp.adapter.BrandBaseAdapter;
import com.example.firozsaifi.azoolp.adapter.CategoryBaseAdapter;
import com.example.firozsaifi.azoolp.adapter.ModelBaseAdapter;
import com.example.firozsaifi.azoolp.adapter.YearBaseAdapter;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnBrandCallback;
import com.example.firozsaifi.azoolp.interfaces.OnCategoryCallback;
import com.example.firozsaifi.azoolp.interfaces.OnModelCallback;
import com.example.firozsaifi.azoolp.interfaces.OnProductAddCallback;
import com.example.firozsaifi.azoolp.interfaces.OnYearCallback;
import com.example.firozsaifi.azoolp.model.Brand;
import com.example.firozsaifi.azoolp.model.Category;
import com.example.firozsaifi.azoolp.model.Model;
import com.example.firozsaifi.azoolp.model.Year;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.CustomProgressDialog;
import com.example.firozsaifi.azoolp.other.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class AddProductFragment extends Fragment implements View.OnClickListener, OnProductAddCallback, OnBrandCallback, OnModelCallback, OnYearCallback, OnCategoryCallback {

    //my objects

    ImageView menu_btn, car_dot_selection, part_dot_selection;
    ImageView imageOne, imageTwo, imageThree;

    TextView txt_add_part, txt_add_car;


    //list pop window and data
    ListPopupWindow listPopupWindow;
    String[] categoryItems={"Body Parts", "Engine Parts", "Interior Parts","Mechanical Parts"};
    String[] brandItems={"Acura", "Alfa romeo", "Aston martin","Audi",
            "BAIC"};
    String[] modelItems={"A1", "A2", "A3","A4",
            "A5"};
    String[] yearItems={"2018", "2017", "2016","2015","2014","2013"};


    //

    //Search by list popup
    RelativeLayout category, brand, model, year;
    TextView txt_category, txt_brand, txt_model, txt_year;
    //list pop up ends here

    EditText et_title, et_description, et_price;

    RelativeLayout add_part_layout, add_car_layout;

    Boolean isPartSelected= true;

    Button btn_add_product;

    ProgressDialog progressDialog;
    CustomRequest customRequest;

    //Image request code
    private int PICK_IMAGE_REQUEST_CODE_ONE = 1;
    //Image request code
    private int PICK_IMAGE_REQUEST_CODE_TWO = 2;
    //Image request code
    private int PICK_IMAGE_REQUEST_CODE_THREE = 3;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath1;
    private Uri filePath2;
    private Uri filePath3;


    private String selectedBrandId;
    private String selectedModelId;
    private String selectedYearId;
    private String selectedCatId;
    private String dealerId;

    private SessionManager session;

    //ends here

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddProductFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddProductFragment newInstance(String param1, String param2) {
        AddProductFragment fragment = new AddProductFragment();
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
        View view= inflater.inflate(R.layout.fragment_add_product, container, false);
        menu_btn= view.findViewById(R.id.menu_btn);
        menu_btn.setOnClickListener(this);
        category= view.findViewById(R.id.category_selector);
        brand= view.findViewById(R.id.brand_selector);
        model= view.findViewById(R.id.model_selector);
        year= view.findViewById(R.id.year_selector);

        txt_add_part= view.findViewById(R.id.txt_add_part);
        txt_add_car= view.findViewById(R.id.txt_add_car);

        et_title= view.findViewById(R.id.etxt_title_english);
        et_description= view.findViewById(R.id.etxt_description);
        et_price= view.findViewById(R.id.etxt_price);

        imageOne= view.findViewById(R.id.imageOne);
        imageTwo= view.findViewById(R.id.imageTwo);
        imageThree= view.findViewById(R.id.imageThree);

        // Session manager
        session = new SessionManager(getActivity());

        btn_add_product= view.findViewById(R.id.btn_add_product);

        txt_category= view.findViewById(R.id.txt_categoy);
        txt_brand= view.findViewById(R.id.txt_brand);
        txt_model= view.findViewById(R.id.txt_model);
        txt_year= view.findViewById(R.id.txt_year);

        add_part_layout= view.findViewById(R.id.add_part_sublayout);
        add_car_layout= view.findViewById(R.id.add_car_sublayout);
        part_dot_selection= view.findViewById(R.id.img_add_part);
        car_dot_selection= view.findViewById(R.id.img_add_car);

        add_part_layout.setOnClickListener(this);
        add_car_layout.setOnClickListener(this);

        category.setOnClickListener(this);
        brand.setOnClickListener(this);
        model.setOnClickListener(this);
        year.setOnClickListener(this);
        btn_add_product.setOnClickListener(this);

        imageOne.setOnClickListener(this);
        imageTwo.setOnClickListener(this);
        imageThree.setOnClickListener(this);

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


        if(v==category)
        {
            showProgressDialog();
            ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.categoryView(Constants.URL_CATEGORY, this));

            /*listPopupWindow= new ListPopupWindow(getActivity());
            listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,categoryItems));
            listPopupWindow.setAnchorView(category);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    txt_category.setText(categoryItems[position]);
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow.show();*/
        }

        if(v==brand)
        {
            showProgressDialog();
            ServiceRequest.getInstance(getActivity()).addToRequestQueue(customRequest.brandView(Constants.URL_BRAND, this));

           /* listPopupWindow= new ListPopupWindow(getActivity());
            listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,brandItems));
            listPopupWindow.setAnchorView(brand);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    txt_brand.setText(brandItems[position]);
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow.show();*/
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

           /* listPopupWindow= new ListPopupWindow(getActivity());
            listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,modelItems));
            listPopupWindow.setAnchorView(model);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    txt_model.setText(modelItems[position]);
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow.show();*/
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

           /* listPopupWindow= new ListPopupWindow(getActivity());
            listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,yearItems));
            listPopupWindow.setAnchorView(year);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    txt_year.setText(yearItems[position]);
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow.show();*/
        }

        if(v==imageOne)
        {
            showFileChooser(PICK_IMAGE_REQUEST_CODE_ONE);
        }

        if(v==imageTwo)
        {
            showFileChooser(PICK_IMAGE_REQUEST_CODE_TWO);
        }

        if(v==imageThree)
        {
            showFileChooser(PICK_IMAGE_REQUEST_CODE_THREE);
        }

        if(v==add_part_layout)
        {
            isPartSelected=true;
            car_dot_selection.setImageResource(0);
            part_dot_selection.setImageResource(R.drawable.ic_dot_black_24dp);
            category.setVisibility(View.VISIBLE);
            et_description.setVisibility(View.GONE);
            et_price.setVisibility(View.GONE);


        }

        if(v==add_car_layout)
        {
            isPartSelected=false;
            part_dot_selection.setImageResource(0);
            car_dot_selection.setImageResource(R.drawable.ic_dot_black_24dp);
            category.setVisibility(View.GONE);
            et_description.setVisibility(View.VISIBLE);
            et_price.setVisibility(View.VISIBLE);
        }

        if(v==btn_add_product)
        {
            String ebrand= txt_brand.getText().toString().trim();
            String emodel= txt_model.getText().toString().trim();
            String eyear= txt_year.getText().toString().trim();
            String ecat= txt_category.getText().toString().trim();
            String etitle= et_title.getText().toString().trim();
            String edescription= et_description.getText().toString().trim();
            String eprice= et_price.getText().toString().trim();

            if(session.isLoggedIn())
            {
                dealerId= String.valueOf(session.getDealerId());
            }

            if(isPartSelected)
            {
                Log.d("Add Part", ebrand + emodel + eyear + ecat + etitle);

                if(!ebrand.equalsIgnoreCase("CAR BRAND"))
                {
                    if(!emodel.equalsIgnoreCase("CAR MODEL"))
                    {
                        if(!eyear.equalsIgnoreCase("YEAR"))
                        {
                            if(!ecat.equalsIgnoreCase("SPARE PARTS CATEGORY"))
                            {
                                    if(!etitle.isEmpty())
                                    {
                                        if(filePath1!=null)
                                        {
                                            if(filePath2!=null)
                                            {
                                                if(filePath3!=null)
                                                {
                                                    //showProgressDialog();
                                                    //Toast.makeText(getActivity(),"Product added successfully!", Toast.LENGTH_LONG).show();
                                                    showProgressDialog();

                                                    String path1= getPath(filePath1);  //get the absolute path from the getPath function
                                                    String path2= getPath(filePath2);  //get the absolute path from the getPath function
                                                    String path3= getPath(filePath3);  //get the absolute path from the getPath function


                                                    Log.d("Absolute path", path1);
                                                    Log.d("Absolute path", path2);
                                                    Log.d("Absolute path", path3);

                                                    customRequest.addProduct(Constants.URL_ADD_PRODUCT, getActivity(), this, path1, path2, path3, selectedBrandId, selectedModelId, selectedYearId, selectedCatId, etitle, "", "", dealerId, "spare");
                                                    //customRequest.addSpareProduct(Constants.URL_ADD_PRODUCT, getActivity(), this, path1, path2, path3, selectedBrandId, selectedModelId, selectedYearId, selectedCatId, etitle, dealerId, "spare");

                                                }
                                                else
                                                {
                                                    Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),"Please enter Title", Toast.LENGTH_LONG).show();
                                    }
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Please select Category", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Please select Year", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please select Model", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),"Please select Brand", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Log.d("Add Car", ebrand + emodel + eyear + etitle + edescription + eprice);

                if(!ebrand.equalsIgnoreCase("CAR BRAND"))
                {
                    if(!emodel.equalsIgnoreCase("CAR MODEL"))
                    {
                        if(!eyear.equalsIgnoreCase("YEAR"))
                        {
                            if(!etitle.isEmpty())
                            {
                                if(!edescription.isEmpty())
                                {
                                    if(!eprice.isEmpty())
                                    {
                                        if(filePath1!=null)
                                        {
                                            if(filePath2!=null)
                                            {
                                                if(filePath3!=null)
                                                {
                                                    //showProgressDialog();
                                                    //Toast.makeText(getActivity(),"Product added successfully!", Toast.LENGTH_LONG).show();
                                                    showProgressDialog();

                                                    String path1= getPath(filePath1);  //get the absolute path from the getPath function
                                                    String path2= getPath(filePath2);  //get the absolute path from the getPath function
                                                    String path3= getPath(filePath3);  //get the absolute path from the getPath function


                                                    Log.d("Absolute path", path1);
                                                    Log.d("Absolute path", path2);
                                                    Log.d("Absolute path", path3);

                                                    customRequest.addProduct(Constants.URL_ADD_PRODUCT, getActivity(), this, path1, path2, path3, selectedBrandId, selectedModelId, selectedYearId, "0", etitle, edescription, eprice, dealerId, "accident_part");
                                                }
                                                else
                                                {
                                                    Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                            else
                                            {
                                                Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(),"Please choose image", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                    else
                                    {
                                        Toast.makeText(getActivity(),"Please enter Price", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"Please enter Description", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getActivity(),"Please enter Title", Toast.LENGTH_LONG).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Please select Year", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please select Model", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getActivity(),"Please select Brand", Toast.LENGTH_LONG).show();
                }

            }

        }

    }

    public void showFileChooser(int imageReqCode)
    {
        Intent intent= new Intent();
        intent.setType("image/*");  //it will show only images in the picker
        intent.setAction(Intent.ACTION_GET_CONTENT);  //to get the contents
        //it will call the onActivityResult overriden method to handle the file chooser
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),imageReqCode);
    }

    ///handling the request after user pick the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST_CODE_ONE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //image path from the storage
            filePath1= data.getData();
            try
            {
                //storing the image into bitmap object
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath1);
                Log.d("Uri path", filePath1.toString());
                bitmap= Bitmap.createScaledBitmap(bitmap,500, 500, false);
                Glide.with(getActivity()).load(bitmap).into(imageOne);
                //Picasso.get().load(filePath1).into(imageOne);
                //imgShow.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        if(requestCode == PICK_IMAGE_REQUEST_CODE_TWO && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //image path from the storage
            filePath2= data.getData();
            try
            {
                //storing the image into bitmap object
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath2);
                Log.d("Uri path", filePath2.toString());
                bitmap= Bitmap.createScaledBitmap(bitmap,500, 500, false);
                Glide.with(getActivity()).load(bitmap).into(imageTwo);
                //Picasso.get().load(filePath2).into(imageTwo);
                //imgShow.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        if(requestCode == PICK_IMAGE_REQUEST_CODE_THREE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //image path from the storage
            filePath3= data.getData();
            try
            {
                //storing the image into bitmap object
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filePath3);
                Log.d("Uri path", filePath3.toString());
                bitmap= Bitmap.createScaledBitmap(bitmap,500, 500, false);
                Glide.with(getActivity()).load(bitmap).into(imageThree);
                //Picasso.get().load(filePath3).into(imageThree);
                //imgShow.setImageBitmap(bitmap);
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

    @Override
    public void onProductAddSuccess(JSONObject credentials) {
        progressDialog.dismiss();
        Toast.makeText(getActivity(),"Product Added Successfully", Toast.LENGTH_LONG).show();
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

    @Override
    public void onProductAddError(String error) {
        progressDialog.dismiss();
        if (error != null && !error.isEmpty()){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBrandSuccess(final ArrayList<Brand> brandList) {
        dismissProgressDialog();
        Toast.makeText(getActivity(), "Showing brand list", Toast.LENGTH_LONG).show();
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
    public void onCategorySuccess(final ArrayList<Category> categoryList) {
        dismissProgressDialog();
        if (categoryList.size() > 0) {
            listPopupWindow = new ListPopupWindow(getActivity());
            //listPopupWindow.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.list_item_popupwindow,brandLi));
            CategoryBaseAdapter categoryBaseAdapter = new CategoryBaseAdapter(categoryList);
            listPopupWindow.setAdapter(categoryBaseAdapter);
            listPopupWindow.setAnchorView(category);
            //listPopupWindow.setWidth(300);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if (categoryList.size() > position) {
                        Category selection = categoryList.get(position);
                        txt_category.setText(selection.getTitle());
                        selectedCatId = String.valueOf(selection.getId());
                        Log.d("selected cat id", selectedCatId);
                        listPopupWindow.dismiss();
                    }
                }
            });

            listPopupWindow.show();
            //Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(getActivity(),"Sorry, No Category Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCategoryError(String error) {
        dismissProgressDialog();
        if (error != null && !error.isEmpty()){
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNetworkError() {
        progressDialog.dismiss();
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
