package com.zybooks.twister;

import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataFetcher {

    private Context mContext;

    public DataFetcher(Context context) {
        mContext = context;
    }

    public interface OnTwistsReceivedListener {
        void onTwistsReceived(ArrayList<Twist> twist);
        void onErrorResponse(VolleyError error);
    }

    public void getData(String HttpExtension, final OnTwistsReceivedListener listener) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "http://jsonstub.com" + HttpExtension;

        // Request a string response from the provided URL
        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonTwistArray = response.getJSONArray("twists");
                            ArrayList<Twist> twists = new ArrayList<>();
                            for (int i = 0; i < jsonTwistArray.length(); i++){
                                Twist twist = new Twist();
                                JSONObject jsonTwist = jsonTwistArray.getJSONObject(i);
                                twist.setId(jsonTwist.getInt("id"));
                                twist.setName(jsonTwist.getString("username"));
                                twist.setDescription(jsonTwist.getString("message"));
                                twist.setmTimeAgo(jsonTwist.getString("timestamp"));
                                twists.add(twist);
                            }
                            listener.onTwistsReceived(twists);
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
                params.put("JsonStub-Project-Key", "40e26003-fc1b-40f3-9ae4-bfab71e6d186");
                return params;
            }
        };

        // Add the request to the RequestQueue
        queue.add(jsonRequest);
    }
}
