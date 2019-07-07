package com.example.firozsaifi.azoolp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firozsaifi.azoolp.R;
import com.example.firozsaifi.azoolp.data.CustomRequest;
import com.example.firozsaifi.azoolp.data.ServiceRequest;
import com.example.firozsaifi.azoolp.interfaces.OnLoginCallback;
import com.example.firozsaifi.azoolp.other.Constants;
import com.example.firozsaifi.azoolp.other.SessionManager;

import org.json.JSONObject;

import static com.example.firozsaifi.azoolp.R.id.etxt_upass_login;
import static com.example.firozsaifi.azoolp.R.id.txt_forgotpass_login;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnLoginCallback {

    ImageView menu_back_btn;
    TextView txt_forgoetPass;
    EditText et_username, et_userpass;
    Button btn_login;

    CustomRequest customRequest;
    private ProgressDialog mProgressDialog;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        menu_back_btn= findViewById(R.id.menu_btn);
        txt_forgoetPass= findViewById(R.id.txt_forgotpass_login);
        et_username= findViewById(R.id.etxt_uname_login);
        et_userpass= findViewById(R.id.etxt_upass_login);
        btn_login= findViewById(R.id.btn_login);

        menu_back_btn.setOnClickListener(this);
        txt_forgoetPass.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        customRequest= new CustomRequest();
        // Session manager
        session = new SessionManager(this);

        mProgressDialog= new ProgressDialog(this);
        mProgressDialog.setCancelable(false);

    }


    @Override
    public void onClick(View v) {
        if(v==menu_back_btn)
        {
            super.onBackPressed();
        }
        if(v==txt_forgoetPass)
        {
            Intent i= new Intent(this,ForgotPasswordActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
        }
        if(v==btn_login)
        {
            String email= et_username.getText().toString().trim();
            String pass= et_userpass.getText().toString().trim();

            if(!email.isEmpty())
            {
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    if(!pass.isEmpty())
                    {
                        mProgressDialog.setMessage("Logging In.. Please Wait...");
                        mProgressDialog.show();

                        ServiceRequest.getInstance(this).addToRequestQueue(customRequest.login(Constants.URL_LOGIN,this, email, pass));

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
    }

    @Override
    public void onLoginSuccess(JSONObject credentials) {
        mProgressDialog.dismiss();

        session.setLogin(true);

        JSONObject dealer = null;

        try{
            dealer= credentials.getJSONObject("dealer");
            int dealerid= dealer.getInt("id");
            String name= dealer.getString("name");
            String email= dealer.getString("email");
            String shopEnglish= dealer.getString("shopEnglish");
            String shopArabic = dealer.getString("shopArabic");
            String phone = dealer.getString("phone");
            String whatsapp = dealer.getString("whatsapp");
            String address = dealer.getString("address");
            String description = dealer.getString("description");
            String firebase_notification = dealer.getString("firebase_notification");

            session.setDealerId(dealerid);
            session.setName(name);
            session.setEmail(email);
            session.setCompanyEnglishName(shopEnglish);
            session.setCompanyArabicName(shopArabic);
            session.setPhone(phone);
            session.setWhatsapp(whatsapp);
            session.setAddress(address);
            session.setDescription(description);
            session.setFirebaseNotification(firebase_notification);

            Log.d("Session data", dealerid+" and "+name+" and "+" and"+phone + " and " + email + " and " + whatsapp
            + " and " + shopEnglish + " and " + shopArabic + " and " + address + " and "+ description + " and "
            + " and " + firebase_notification);

            Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_LONG).show();

            Intent i= new Intent(this,MainActivity.class);
            startActivity(i);
            finish();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public void onLoginError(String error) {
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
