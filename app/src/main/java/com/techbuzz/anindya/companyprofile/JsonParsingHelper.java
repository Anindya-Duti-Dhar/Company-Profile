package com.techbuzz.anindya.companyprofile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParsingHelper {

    private static final String TAG = JsonParsingHelper.class.getSimpleName();

    public static ArrayList<Category> getCities(String countryJson) throws JSONException {
        ArrayList<Category> cities = new ArrayList<Category>();

        JSONArray names = null;

        // getting JSON string from asset
        JSONObject json = new JSONObject(countryJson);

        try {
            // Getting Array of Contacts
            names = json.getJSONArray("model");
/*            Context context = null; // context
            Resources resources = context.getResources();*/
            // looping through All Contacts
            for(int i = 0; i < names.length(); i++){
                JSONObject c = names.getJSONObject(i);

                // Storing each json item in variable
                Category cat = new Category();
                String name = c.getString("name");
                String desc1 = c.getString("desc1");
                /*String born = c.getString("born");*/
                String id = c.getString("id");
                String desc2 = c.getString("desc2");

                /*String picture = c.getString("picture");
                // get resource id by image name
                final int resourceId = resources.getIdentifier(picture, "drawable", context.getPackageName());
                // get drawable by resource id
                Drawable drawable = resources.getDrawable(resourceId);*/

                cat.setName(name);
                cat.setDesc1(desc1);
                /*cat.setBorn(born);*/
                cat.setId(id);
                cat.setDesc2(desc2);

                /*cat.setPicture(picture);*/

                cities.add(cat);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cities;
    }
}

