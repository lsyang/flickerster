package com.codepath.flickerster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lsyang on 3/12/17.
 */

public class Trailer {

    public String getKey() {
        return key;
    }

    String key;

    public Trailer(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
    }

    public static ArrayList<Trailer> fromJSONArray(JSONArray array) {
        ArrayList<Trailer> results = new ArrayList<>();
        for (int x=0; x < array.length(); x++) {
            try {
                results.add(new Trailer(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
