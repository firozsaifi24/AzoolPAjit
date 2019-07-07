package com.example.firozsaifi.azoolp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnRegisterCallback;
import com.example.firozsaifi.azoolp.other.Constants;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, OnRegisterCallback {

    ImageView menu_back_btn;
    EditText et_name, et_email, et_pass, et_confirm_pass;
    Button btn_register;
    CustomRequest customRequest;

    //list pop window and data
    ListPopupWindow listPopupWindow;
    String[] phoneCode={"971", "1", "44","66"};

    //

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        menu_back_btn= findViewById(R.id.menu_btn);
        et_name= findViewById(R.id.etxt_name_register);
        et_email= findViewById(R.id.etxt_email_register);
        et_pass= findViewById(R.id.etxt_pass_register);
        et_confirm_pass= findViewById(R.id.etxt_confirm_pass_register);
        btn_register= findViewById(R.id.btn_register);

        mProgressDialog= new ProgressDialog(this);
        mProgressDialog.setCancelable(false);

        menu_back_btn.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        customRequest= new CustomRequest();

    }

    @Override
    public void onClick(View v) {
        if(v==menu_back_btn)
        {
            super.onBackPressed();
        }

/*        if(v==txt_country_code)
        {
            listPopupWindow= new ListPopupWindow(this);
            listPopupWindow.setAdapter(new ArrayAdapter<String>(this,R.layout.list_item_popupwindow,phoneCode));
            listPopupWindow.setAnchorView(txt_country_code);
            listPopupWindow.setWidth(500);
            //listPopupWindow.setHeight(400);
            listPopupWindow.setModal(true);
            listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    txt_country_code.setText(phoneCode[position]);
                    listPopupWindow.dismiss();
                }
            });

            listPopupWindow.show();
        }*/

        if(v==btn_register) {

            String name = et_name.getText().toString().trim();
            String email = et_email.getText().toString().trim();
            String pass = et_pass.getText().toString().trim();
            String confirmpass = et_confirm_pass.getText().toString().trim();

            if (!name.isEmpty())
            {

                if(!email.isEmpty())
                {
                    if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    {

                        if(!pass.isEmpty())
                        {
                            if(!confirmpass.isEmpty())
                            {

                                           if(pass.equals(confirmpass))
                                           {
                                               //mProgressDialog.setMessage("Registering.. Please Wait...");
                                               //mProgressDialog.show();

                                               //ServiceRequest.getInstance(this).addToRequestQueue(customRequest.register(Constants.URL_REGISTER,this, name, cname, email, phone, pass));
                                               //Toast.makeText(this,"Registration success",Toast.LENGTH_LONG).show();

                                               Intent i= new Intent(this,RegisterAdditonalActivity.class);
                                               Bundle bundle= new Bundle();
                                               bundle.putString("name", name);
                                               bundle.putString("email", email);
                                               bundle.putString("pass", pass);
                                               i.putExtras(bundle);
                                               startActivity(i);
                                               overridePendingTransition(R.anim.enter, R.anim.exit);

                                           }
                                           else
                                           {
                                               Toast.makeText(this,"Password do not match!", Toast.LENGTH_LONG).show();
                                           }

                            }
                            else
                            {
                                Toast.makeText(this,"Pls confirm your password", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(this,"Pls enter your password", Toast.LENGTH_LONG).show();
                        }


                    }
                    else
                    {
                        Toast.makeText(this,"Pls enter a valid email address!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(this,"Pls enter your email", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(this,"Pls enter your name", Toast.LENGTH_LONG).show();
            }



        }

    }

    @Override
    public void onRegisterSuccess(JSONObject response) {
        mProgressDialog.dismiss();
        Toast.makeText(getApplicationContext(),"Registration successfull! Try Login Now!", Toast.LENGTH_LONG).show();
        Intent i= new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onRegisterError(String error) {
        mProgressDialog.dismiss();
        if (error != null && !error.isEmpty()){
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Some error has been occurred!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNetworkError() {
        mProgressDialog.dismiss();
        Toast.makeText(this,"No Internet Connection!", Toast.LENGTH_LONG).show();
    }
}
