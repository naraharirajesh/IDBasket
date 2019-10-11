package com.IDB.idbasket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.IDB.idbasket.Utils.SaveData;
import com.IDB.idbasket.model.RegDataModel;
import com.google.gson.Gson;

public class RegistrationActivity extends AppCompatActivity {
    ImageButton menu_icon;
    TextView app_title;
    EditText firstName,lastName,phoneNumber,emailID,companyName,designation,whatsappNumnber,website,linkedinId,facebookId,twitterId,address,titleOnBussinessCard,confirmPassword,password;
    Spinner profession;
    Button submit;
    RegDataModel dm = null;
    String[] professionsList = { "Select Profession","Accountant", "Commercial Banker", "Research Analyst", "Investment Banker"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration_activity);
        dm = new RegDataModel();
        menu_icon = findViewById(R.id.menu_icon);
        app_title = findViewById(R.id.app_title);
        menu_icon.setVisibility(View.GONE);
        app_title.setText("Profile Registration");
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        phoneNumber = findViewById(R.id.phoneNumber);
        emailID = findViewById(R.id.emailID);
        companyName = findViewById(R.id.companyName);
        designation = findViewById(R.id.designation);
        whatsappNumnber = findViewById(R.id.whatsappNumnber);
        website = findViewById(R.id.website);
        linkedinId = findViewById(R.id.linkedinId);
        facebookId = findViewById(R.id.facebookId);
        twitterId = findViewById(R.id.twitterId);
        titleOnBussinessCard = findViewById(R.id.titleOnBussinessCard);
        address = findViewById(R.id.address);
        profession = findViewById(R.id.profession);
        submit = findViewById(R.id.submit);
        confirmPassword = findViewById(R.id.confirmPassword);
        password = findViewById(R.id.password);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,professionsList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        profession.setAdapter(aa);
        profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) profession.getSelectedView()).setTextColor(getResources().getColor(R.color.colorAccent));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
                submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"Please Enter FirstName",Toast.LENGTH_SHORT).show();
                }else if(phoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"Please Enter PhoneNumber",Toast.LENGTH_SHORT).show();
                }else if(phoneNumber.getText().toString().trim().length() < 10){
                    Toast.makeText(RegistrationActivity.this,"Please Enter Valid PhoneNumber",Toast.LENGTH_SHORT).show();
                }else if(emailID.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"Please Enter EmailId",Toast.LENGTH_SHORT).show();
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailID.getText().toString()).matches()){
                    Toast.makeText(RegistrationActivity.this,"Please Enter Valid EmailId",Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().trim().length() < 6){
                    Toast.makeText(RegistrationActivity.this,"Password must contain at least six characters",Toast.LENGTH_SHORT).show();
                }else if(confirmPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegistrationActivity.this,"Please Enter ConfirmPassword",Toast.LENGTH_SHORT).show();
                }else if(!password.getText().toString().matches(confirmPassword.getText().toString().trim())){
                    Toast.makeText(RegistrationActivity.this,"Please check that you've entered and confirmed your password.",Toast.LENGTH_SHORT).show();
                }else{
                    dm.setFirstName(firstName.getText().toString().trim());
                    dm.setLastName(lastName.getText().toString().trim());
                    dm.setPhoneNumber(phoneNumber.getText().toString().trim());
                    dm.setEmailId(emailID.getText().toString().trim());
                    dm.setCompanyName(companyName.getText().toString().trim());
                    dm.setDesignation(designation.getText().toString().trim());
                    dm.setWhatsappNumber(whatsappNumnber.getText().toString().trim());
                    dm.setWebsit(website.getText().toString().trim());
                    dm.setLinkedinID(linkedinId.getText().toString().trim());
                    dm.setFacebookId(facebookId.getText().toString().trim());
                    dm.setTwitterId(twitterId.getText().toString().trim());
                    dm.setAddress(address.getText().toString().trim());
                    dm.setPassword(password.getText().toString().trim());
                    dm.setConfirmPassword(confirmPassword.getText().toString().trim());
                    dm.setTitleOnBusinessCard(titleOnBussinessCard.getText().toString().trim());
                    if(profession.getSelectedItemPosition() != 0)
                    dm.setProfession(profession.getSelectedItem().toString());
                    new SaveData(RegistrationActivity.this).saveTotalData(dm);
                    SharedPreferences preferences = PreferenceManager
                            .getDefaultSharedPreferences(RegistrationActivity.this);
                    SharedPreferences.Editor prefsEditor = preferences.edit();
                    prefsEditor.putBoolean("isMobileLoggedIn", true);
                    prefsEditor.commit();
                    finish();
                    startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
