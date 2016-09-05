package com.techbuzz.anindya.companyprofile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParsingHelper {

   //To pass the context in Non-activity class from the Activity Class where I have call this method
    static Context context; // context

    //Create Constructor to access the context
    public JsonParsingHelper(Context context){
        this.context = context; //here pass .this from Activity Class
    }

    private static final String TAG = JsonParsingHelper.class.getSimpleName();

    public static ArrayList<Category> getBollywood(String bollywoodJson) throws JSONException {
        ArrayList<Category> bollywood = new ArrayList<Category>();

        JSONArray names = null;

        // getting JSON string from asset
        JSONObject json = new JSONObject(bollywoodJson);

        try {
            // Getting Array of Contacts
            names = json.getJSONArray("model");

            // looping through All Contacts
            for(int i = 0; i < names.length(); i++){
                JSONObject c = names.getJSONObject(i);

                // Storing each json item in variable
                Category cat = new Category();
                String name = c.getString("name");
                String desc1 = c.getString("desc1");
                String born = c.getString("born");
                String id = c.getString("id");
                String desc2 = c.getString("desc2");

                //Convert The String Picture to Resource Res existed in the drawable folder
                String picture = c.getString("picture").substring(0, c.getString("picture").lastIndexOf('.'));
                int res = context.getResources().getIdentifier(picture, "drawable" , context.getPackageName());

                cat.setName(name);
                cat.setDesc1(desc1);
                cat.setBorn(born);
                cat.setId(id);
                cat.setDesc2(desc2);
                cat.setRes(res);
                cat.setPicture(picture);

                bollywood.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bollywood;
    }
}

