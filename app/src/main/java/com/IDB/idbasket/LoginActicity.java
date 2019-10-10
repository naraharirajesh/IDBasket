package com.IDB.idbasket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.IDB.idbasket.Utils.SaveData;
import com.IDB.idbasket.model.RegDataModel;

public class LoginActicity extends AppCompatActivity {
    EditText emailId,password;
    Button submit,signupBtn;
    ImageButton menu_icon;
    TextView app_title;
    RegDataModel dm = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout_activity);
        emailId  = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submitBtn);
        menu_icon = findViewById(R.id.menu_icon);
        menu_icon.setVisibility(View.GONE);
        app_title = findViewById(R.id.app_title);
        app_title.setText("Login");
        signupBtn  = findViewById(R.id.signupBtn);
        dm = new RegDataModel();
        dm = new SaveData(LoginActicity.this).getMainData();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailId.getText().toString().isEmpty()){
                    Toast.makeText(LoginActicity.this,"Enter Your EmailID",Toast.LENGTH_SHORT).show();
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches()){
                    Toast.makeText(LoginActicity.this,"EmailId is not valid",Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActicity.this,"Enter Your Password",Toast.LENGTH_SHORT).show();
                }else if(dm != null && !dm.getEmailId().equalsIgnoreCase(emailId.getText().toString().trim())){
                    Toast.makeText(LoginActicity.this,"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                }else if(dm != null && !dm.getPassword().equalsIgnoreCase(password.getText().toString().trim())){
                    Toast.makeText(LoginActicity.this,"Incorrect Username or Password",Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(LoginActicity.this,MainActivity.class));
                }
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActicity.this,RegistrationActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
