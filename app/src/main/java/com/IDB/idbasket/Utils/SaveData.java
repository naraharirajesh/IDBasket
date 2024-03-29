package com.IDB.idbasket.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.IDB.idbasket.fragmentClasses.MyCreations;
import com.IDB.idbasket.model.CreationData;
import com.IDB.idbasket.model.RegDataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SaveData {
    SharedPreferences preferences;
    Context context;

    private static SharedPreferences.Editor editor = null;
   public SaveData(Context ctx){
        context = ctx;
       preferences = PreferenceManager
               .getDefaultSharedPreferences(context);
       editor = preferences.edit();
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
    public <T> void setList( List<CreationData> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set("MyRecordsList", json);
    }

    public static void set(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public List<CreationData> getRecordList(){

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String json = appSharedPrefs.getString("MyRecordsList", "");
        Gson gson = new Gson();
        List<CreationData> totalData;
        if(!json.equalsIgnoreCase("")) {
            Type type = new TypeToken<List<CreationData>>() {
            }.getType();
            totalData = gson.fromJson(json, type);
        }else{
            totalData = null;
        }

        return totalData;
    }
}
