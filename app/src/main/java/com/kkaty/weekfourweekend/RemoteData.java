package com.kkaty.weekfourweekend;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoteData {

    public static final String TAG = RemoteData.class.getSimpleName() + "_TAG";

    Context context;
    String results;

    public RemoteData(Context context) {
        context = context;
    }

    // START Per https://mobikul.com/how-to-send-json-post-request-using-volley-rest-api/
    public interface VolleyCallback {
        void onSuccess(String result);
    }

    public void getResponse(String url, JSONObject jsonObject, final VolleyCallback callback){

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError{
                Map<String,String> params = new HashMap<String, String>();
                params.put("Authorization Basic", TOKEN);
                return params;
            }
        };

    }
// END Per: https://mobikul.com/how-to-send-json-post-request-using-volley-rest-api/

    public String getResults() {

        // implementation of Volley Simple and Standard
        //final TextView mTextView = (TextView) findViewById(R.id.text);
        // ...

        Log.d(TAG, "getResults: STARTED");

        // /* Instantiate the RequestQueue.
        // SIMPLE
        //RequestQueue queue = Volley.newRequestQueue(context);

//        // STANDARD
//        // Instantiate the cache
//        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//        // Instantiate the RequestQueue with the cache and network.
//        RequestQueue queue = new RequestQueue(cache, network);
//        // Start the queue
//        queue.start();

        // SINGLETON METHOD - Get a RequestQueue
        RequestQueue queue = MySingleton.getInstance(context.getApplicationContext()).
                getRequestQueue();

        // */

        String url = "http://www.google.com";

        //Log.d(TAG, "getResults: Volley.newRequestQueue setup");
        Log.d(TAG, "getResults: RequestQueue setup");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        results = "Response is: " + response.substring(0, 500);
                        Log.d(TAG, "getResults: onResponse: " + response.substring(0,50));
                        Log.d(TAG, "getResults: onResponse: results: " + results);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                results = "That didn't work!";
            }
        });

        Log.d(TAG, "getResults: stringRequest COMPLETED : results: " + results);

        // Add the request to the RequestQueue.
        // queue.add(stringRequest);
        // SINGLETON METHOD
        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

        Log.d(TAG, "getResults: FINISHED");


        Log.d(TAG, "getResults: results: " + results);

        return results;
    }
}
