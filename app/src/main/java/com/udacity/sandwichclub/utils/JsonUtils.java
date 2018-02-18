package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json){

        try {

        ArrayList<String> sandwichAlsoKnown = new ArrayList<>();

        ArrayList<String> sandwichIngredients = new ArrayList<>();

        JSONObject sandwichJson = new JSONObject(json);

        JSONObject name = sandwichJson.getJSONObject("name");

        String sandwichName = name.optString("mainName");

        JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs") ;

        for (int i = 0; i < alsoKnownAs.length(); i++)
        {
            sandwichAlsoKnown.add(alsoKnownAs.optString(i));
        }

        String sandwichOrigin = sandwichJson.optString("placeOfOrigin");

        String sandwichDescription = sandwichJson.optString("description");

        String sandwichImage = sandwichJson.optString("image");

        JSONArray ingredients = sandwichJson.getJSONArray("ingredients") ;

        for (int i = 0; i < ingredients.length(); i++)
        {
            sandwichIngredients.add(ingredients.optString(i));
        }

        Sandwich objSandwich = new Sandwich(sandwichName, sandwichAlsoKnown, sandwichOrigin, sandwichDescription, sandwichImage, sandwichIngredients);

        return objSandwich;

        } catch (JSONException je) {
            je.printStackTrace();
        }

        return null;
    }
}
