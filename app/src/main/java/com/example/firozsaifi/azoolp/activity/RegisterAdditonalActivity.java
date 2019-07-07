package com.example.firozsaifi.azoolp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnRegisterCallback;
import com.example.firozsaifi.azoolp.other.Constants;
import com.squareup.picasso.Picasso;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONObject;

import java.util.UUID;

public class RegisterAdditonalActivity extends AppCompatActivity implements View.OnClickListener, OnRegisterCallback {

    ImageView menu_back_btn;
    Button btn_register;
    EditText shop_title_english, shop_title_arabic, shop_phone, shop_whatsapp, shop_address, shop_description;

    private ProgressDialog mProgressDialog;
    CustomRequest customRequest;

    ProgressDialog progressDialog;  //for image uploading service

    //image related objects
    Button btnChoose;
    ImageView img1, img2;

    //Image request code
    private int PICK_IMAGE_REQUEST_ONE = 1;
    private int PICK_IMAGE_REQUEST_TWO = 2;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath1;
    private Uri filePath2;

    //image onject ends here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_additonal);
        //Requesting storage permission
        requestStoragePermission();

        menu_back_btn= findViewById(R.id.menu_btn);
        menu_back_btn.setOnClickListener(this);

        btn_register= findViewById(R.id.btn_register);
        shop_title_english= findViewById(R.id.etxt_shop_title_english);
        shop_title_arabic= findViewById(R.id.etxt_shop_title_arabic);
        shop_phone= findViewById(R.id.etxt_shop_phnne_number);
        shop_whatsapp= findViewById(R.id.etxt_shop_whatsapp_number);
        shop_address= findViewById(R.id.etxt_shop_address);
        shop_description= findViewById(R.id.etxt_shop_description);

        img1= findViewById(R.id.img_one);
        img2= findViewById(R.id.img_two);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);

        btn_register.setOnClickListener(this);

        mProgressDialog= new ProgressDialog(this);
        mProgressDialog.setCancelable(false);

        //initProgressDialog();

        customRequest= new CustomRequest();

    }

    @Override
    public void onClick(View v) {

        if(v==menu_back_btn)
        {
            super.onBackPressed();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }

        if(v==btn_register)
        {
            String shopNameEnglish= shop_title_english.getText().toString().trim();
            String shopNameArabic= shop_title_arabic.getText().toString().trim();
            String shopPhone= shop_phone.getText().toString().trim();
            String shopWhatsapp= shop_whatsapp.getText().toString().trim();
            String shopAddress= shop_address.getText().toString().trim();
            String shopDescription= shop_description.getText().toString().trim();

            Bundle bundle = getIntent().getExtras();
            String name = bundle.getString("name");
            String email = bundle.getString("email");
            String pass = bundle.getString("pass");

            Log.d("Received Data: ", name+email+pass);


            if(!shopNameEnglish.isEmpty())
            {
                if(!shopNameArabic.isEmpty())
                {
                    if(!shopPhone.isEmpty())
                    {
                        if(!shopWhatsapp.isEmpty())
                        {
                            if(!shopAddress.isEmpty())
                            {
                                if(!shopDescription.isEmpty())
                                {

                                   /* mProgressDialog.setMessage("Registering.. Please Wait...");
                                    mProgressDialog.show();  */




                                    if(filePath1!=null && filePath2 !=null)
                                    {
                                        //progressDialog.show();
                                        mProgressDialog.setMessage("Registering.. Please Wait...");
                                        mProgressDialog.show();
                                        String path= getPath(filePath1);  //get the absolute path from the getPath function
                                        String path2= getPath(filePath2);  //get the absolute path from the getPath function
                                        Log.d("Absolute path", path);
                                        Log.d("Absolute path", path2);
                                        //customRequest.register(Constants.URL_REGISTER_WITH_IMAGE, RegisterAdditonalActivity.this, this, progressDialog, path, path2, name, email, pass, shopNameEnglish, shopNameArabic, shopPhone, shopWhatsapp, shopAddress, shopDescription);
                                        customRequest.register(Constants.URL_REGISTER_WITH_IMAGE, RegisterAdditonalActivity.this, this, path, path2, name, email, pass, shopNameEnglish, shopNameArabic, shopPhone, shopWhatsapp, shopAddress, shopDescription);
                                        //customRequest.register(Constants.URL_REGISTER, RegisterAdditonalActivity.this ,this, progressDoalog, path, path2, name, email, pass, shopNameEnglish, shopNameArabic, shopPhone, shopWhatsapp, shopAddress, shopDescription);
                                        //uploadImage(Constants.URL_REGISTER_WITH_IMAGE, name, email, pass, shopNameEnglish, shopNameArabic, shopPhone, shopWhatsapp, shopAddress, shopDescription);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),
                                                "Please choose images!", Toast.LENGTH_LONG)
                                                .show();
                                    }


                                    //Toast.makeText(getApplicationContext(),"Registration Successfull", Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Pls enter your shop description", Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Pls enter your shop address", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Pls enter your shop whatsapp number", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Pls enter your shop phone number", Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Pls enter your shop arabic name", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(),"Pls enter your shop english name", Toast.LENGTH_LONG).show();
            }

        }

        if(v==img1)
        {
            showFileChooser(PICK_IMAGE_REQUEST_ONE);
        }

        if(v==img2)
        {
            showFileChooser(PICK_IMAGE_REQUEST_TWO);
        }

    }

    @Override
    public void onRegisterSuccess(JSONObject response) {
        //progressDialog.dismiss();
        mProgressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Registration successfull! Try Login Now!", Toast.LENGTH_LONG).show();
        Intent i= new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onRegisterError(String error) {
        //progressDialog.dismiss();
        mProgressDialog.dismiss();
        if (error != null && !error.isEmpty()){
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    public void onNetworkError() {
        //progressDialog.dismiss();
        mProgressDialog.dismiss();
        Toast.makeText(this,"No Internet Connection!", Toast.LENGTH_LONG).show();
    }

    private void requestStoragePermission()
    {
        //check if the read external storage permission is already granted
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return;  //to stop the execution of this method, if granted
            //break is used in switch and for loop
        }
        //if nnot granted, ask the user to grant the permission
        //it will call the onRequestPermissionsResult overridden method to handle the grant or deny permission.
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);

    }

    //handling the permission request (grant or denied) given by user through this override method
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,"Permission not Granted",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showFileChooser(int pick_image_request_code)
    {
        Intent intent= new Intent();
        intent.setType("image/*");  //it will show only images in the picker
        intent.setAction(Intent.ACTION_GET_CONTENT);  //to get the contents
        //it will call the onActivityResult overriden method to handle the file chooser
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),pick_image_request_code);

    }

    ///handling the request after user pick the image
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE_REQUEST_ONE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //image path from the storage
            filePath1= data.getData();
            try
            {
                //storing the image into bitmap object
                bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),filePath1);
                Log.d("Uri path", filePath1.toString());
                bitmap= Bitmap.createScaledBitmap(bitmap,500, 500, false);
                Glide.with(this).load(bitmap).into(img1);
                //Picasso.get().load(filePath1).into(img1);
                //imgShow.setImageBitmap(bitmap);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }



        }

        if(requestCode == PICK_IMAGE_REQUEST_TWO && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            //image path from the storage
            filePath2= data.getData();
            try
            {
                //storing the image into bitmap object
                bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),filePath2);
                Log.d("Uri path", filePath2.toString());
                bitmap= Bitmap.createScaledBitmap(bitmap,500, 500, false);
                Glide.with(this).load(bitmap).into(img2);
                //Picasso.get().load(filePath2).into(img2);
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

        Cursor cursor= getApplicationContext().getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String document_id= cursor.getString(0);
        document_id= document_id.substring(document_id.lastIndexOf(":")+1);

        cursor= getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Images.Media._ID + " = ? ",new String[]{document_id},null);
        cursor.moveToFirst();
        String path= cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    // this method is used to upload the image
/*    public void uploadImage(String url, final String name, final String email, final String password, final String shopNameEnglish, final String shopNameArabic, final String shopPhone, final String shopWhatsapp, final String shopAddress, final String shopDescription)
    {
        progressDialog.show();
        String path1= getPath(filePath1);  //get the absolute path from the getPath function
        String path2= getPath(filePath2);

        Log.d("Absolute path1", path1);
        Log.d("Absolute path1", path2);

        try
        {
            String uploadid= UUID.randomUUID().toString();
            //uploading file to server
            new MultipartUploadRequest(getApplicationContext(), uploadid, url)
                    .addFileToUpload(path1,"image1")
                    .addFileToUpload(path2,"image2")
                    .addParameter("name", name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .addParameter("shopNameEnglish", shopNameEnglish)
                    .addParameter("shopNameArabic", shopNameArabic)
                    .addParameter("shopPhone", shopPhone)
                    .addParameter("shopWhatsapp", shopWhatsapp)
                    .addParameter("shopAddress", shopAddress)
                    .addParameter("shopDescription", shopDescription)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setDelegate(new net.gotev.uploadservice.UploadStatusDelegate(){

                        @Override
                        public void onProgress(Context context, final UploadInfo uploadInfo) {
                            *//*Handler handler= new Handler();
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressDoalog.setProgress(uploadInfo.getProgressPercent());
                                }
                            });*//*
                            //progressDoalog.setProgress((int)(uploadInfo.getUploadedBytes()/uploadInfo.getTotalBytes())*100);
                            progressDialog.setProgress(uploadInfo.getProgressPercent());
                            Log.e("gotev","onProgress eltstr="+uploadInfo.getElapsedTimeString()+" elt="+uploadInfo.getElapsedTime()+" ratestr="+
                                    uploadInfo.getUploadRateString()+" prc="+uploadInfo.getProgressPercent()+" tb="+uploadInfo.getTotalBytes()+" ub="+uploadInfo.getUploadedBytes());
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {
                            Log.e("gotev","onError "+exception.getMessage() + exception.getStackTrace());
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Some error occurred. Pls Try Again!",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {


                            progressDialog.dismiss();
                            initAlertDialog();
                            Log.e("Server Response: ", serverResponse.getHttpCode()+ " "+serverResponse.getBodyAsString());
                            //Toast.makeText(getApplicationContext(),"Image uploaded Successfully",Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            Log.e("gotev","onCancelled "+uploadInfo);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"Uploading cancelled",Toast.LENGTH_LONG).show();
                        }
                    })
                    .startUpload();

        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Oops! Something went wrong!",Toast.LENGTH_LONG).show();
        }
    }*/

    public void initProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setTitle("Uploading Image");
        progressDialog.setMessage("Please Wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setProgress(0);
    }

    public void initAlertDialog()
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Success");
        alertDialogBuilder.setMessage("Registration Successfull!");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent= new Intent(RegisterAdditonalActivity.this,LoginActivity.class);
                        //finishAffinity();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}
