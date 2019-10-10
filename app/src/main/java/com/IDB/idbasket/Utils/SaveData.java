package com.IDB.idbasket.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.IDB.idbasket.model.RegDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class SaveData {
    SharedPreferences preferences;
    Context context;
   public SaveData(Context ctx){
        context = ctx;
    }
    public void saveTotalData(RegDataModel model){
        preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(model);
        prefsEditor.putString("MainData", json);
        prefsEditor.commit();

    }
    public RegDataModel getMainData(){
        RegDataModel tmodel = new RegDataModel();

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MainData", "");
        RegDataModel totalData;
        if(!json.equalsIgnoreCase("")) {
            Type type = new TypeToken<RegDataModel>() {
            }.getType();
            totalData = gson.fromJson(json, type);
        }else{
            totalData = null;
        }

        return totalData;
    }
}
