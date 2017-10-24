package com.zybooks.thebanddatabase;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Josh on 10/24/2017.
 */

public class DataFetcher {
    public void getWeather(String zip, final OnWeatherReceviedListener listener) {

        MyAsyncThing asyncThing = new MyAsyncThing();
        asyncThing.execute();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "http://jsonstub.com/weather/72143";

        // Request a string response from the provided URL
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //mCurrentTemp.setText("Response is: "+ response.toString());

                        Log.d("temp", "response = " + response.toString());
                        try {
                            Weather weather = new Weather();
                            JSONObject main = response.getJSONObject("main");
                            weather.setCurrentTemp(main.getInt("temp"));
                            weather.setMinTemp(main.getInt("temp_min"));
                            weather.setMaxTemp(main.getInt("temp_max"));
                            weather.setCity(response.getString("name"));
                            listener.onWeatherReceived(weather);
                        }
                        catch (Exception ex) {
                            Log.d("temp", "Error: " + ex.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("JsonStub-User-Key", "edbc267a-f880-4dec-8dec-727cccc27e5d");
                params.put("JsonStub-Project-Key", "3988736a-f142-4f20-bd7b-c809bd2241d9");

                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
