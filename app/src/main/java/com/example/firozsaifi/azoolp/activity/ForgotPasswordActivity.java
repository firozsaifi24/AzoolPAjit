package com.example.firozsaifi.azoolp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firozsaifi.azoolp.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView menu_back_btn;
    Button btn_reset;
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        menu_back_btn= findViewById(R.id.menu_btn);
        et_email= findViewById(R.id.etxt_email_reset);
        btn_reset= findViewById(R.id.btn_reset);

        menu_back_btn.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==menu_back_btn)
        {
            super.onBackPressed();
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }

        if(v==btn_reset)
        {
            String email= et_email.getText().toString().trim();
            if(!email.isEmpty())
            {
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(this,"Password reset successfully!", Toast.LENGTH_LONG).show();
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

}
