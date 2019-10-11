package com.IDB.idbasket.fragmentClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.IDB.idbasket.MainActivity;
import com.IDB.idbasket.R;
import com.IDB.idbasket.RegistrationActivity;
import com.IDB.idbasket.Utils.SaveData;
import com.IDB.idbasket.model.RegDataModel;

public class ProfileSettings extends Fragment {
    ImageButton menu_icon;
    TextView app_title;
    EditText firstName,lastName,phoneNumber,emailID,companyName,designation,whatsappNumnber,website,linkedinId,facebookId,twitterId,address,titleOnBussinessCard,confirmPassword,password;
    Spinner profession;
    Button submit;
    RegDataModel dm = null;
    String[] professionsList = { "Select Profession","Accountant", "Commercial Banker", "Research Analyst", "Investment Banker"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.profile_settings,container,false);
        dm = new RegDataModel();
        menu_icon = getActivity().findViewById(R.id.menu_icon);
        app_title = getActivity().findViewById(R.id.app_title);
//        menu_icon.setVisibility(View.GONE);
        app_title.setText("Profile Setting");
        firstName = layout.findViewById(R.id.firstName);
        lastName = layout.findViewById(R.id.lastName);
        phoneNumber = layout.findViewById(R.id.phoneNumber);
        emailID = layout.findViewById(R.id.emailID);
        companyName = layout.findViewById(R.id.companyName);
        designation = layout.findViewById(R.id.designation);
        whatsappNumnber = layout.findViewById(R.id.whatsappNumnber);
        website = layout.findViewById(R.id.website);
        linkedinId = layout.findViewById(R.id.linkedinId);
        facebookId = layout.findViewById(R.id.facebookId);
        twitterId = layout.findViewById(R.id.twitterId);
        titleOnBussinessCard = layout.findViewById(R.id.titleOnBussinessCard);
        address = layout.findViewById(R.id.address);
        profession = layout.findViewById(R.id.profession);
        submit = layout.findViewById(R.id.submit);
        confirmPassword = layout.findViewById(R.id.confirmPassword);
        password = layout.findViewById(R.id.password);
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,professionsList);
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
                    Toast.makeText(getActivity(),"Please Enter FirstName",Toast.LENGTH_SHORT).show();
                }else if(phoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter PhoneNumber",Toast.LENGTH_SHORT).show();
                }else if(phoneNumber.getText().toString().trim().length() < 10){
                    Toast.makeText(getActivity(),"Please Enter Valid PhoneNumber",Toast.LENGTH_SHORT).show();
                }else if(emailID.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter EmailId",Toast.LENGTH_SHORT).show();
                }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailID.getText().toString()).matches()){
                    Toast.makeText(getActivity(),"Please Enter Valid EmailId",Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter Password",Toast.LENGTH_SHORT).show();
                }else if(password.getText().toString().trim().length() < 6){
                    Toast.makeText(getActivity(),"Password must contain at least six characters",Toast.LENGTH_SHORT).show();
                }else if(confirmPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter ConfirmPassword",Toast.LENGTH_SHORT).show();
                }else if(!password.getText().toString().matches(confirmPassword.getText().toString().trim())){
                    Toast.makeText(getActivity(),"Please check that you've entered and confirmed your password.",Toast.LENGTH_SHORT).show();
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
                    new SaveData(getActivity()).saveTotalData(dm);

                    HomeScreen hm = new HomeScreen();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.container_layout,hm);
                    ft.commit();
//                    startActivity(new Intent(getActivity(), MainActivity.class));
                }

            }
        });
        RegDataModel model = new RegDataModel();
        model = new SaveData(getActivity()).getMainData();
        if(model != null) {
            firstName.setText(model.getFirstName());
            lastName.setText(model.getLastName());
            phoneNumber.setText(model.getPhoneNumber());
            emailID.setText(model.getEmailId());
            companyName.setText(model.getCompanyName());
            designation.setText(model.getDesignation());
            whatsappNumnber.setText(model.getWhatsappNumber());
            website.setText(model.getWebsit());
            linkedinId.setText(model.getLinkedinID());
            facebookId.setText(model.getFacebookId());
            twitterId.setText(model.getTwitterId());
            address.setText(model.getAddress());
            password.setText(model.getPassword());
            confirmPassword.setText(model.getConfirmPassword());
            titleOnBussinessCard.setText(model.getTitleOnBusinessCard());

//            if (profession.getSelectedItemPosition() != 0)
//                dm.setProfession(profession.getSelectedItem().toString());
        }
        return layout;
    }
}
