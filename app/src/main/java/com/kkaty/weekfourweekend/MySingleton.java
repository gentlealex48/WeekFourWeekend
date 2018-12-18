package com.kkaty.weekfourweekend;

// Per https://mobikul.com/how-to-send-json-post-request-using-volley-rest-api/

import android.content.Context;

public class MySingleton {

    private static MySingleton mySingleton;
    private RequestQueue requestQueue;
    private static Context context;

    private MySingleton(Context context){
        context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        if (mySingleton == null) {
            mySingleton = new MySingleton(context);
        }
        return mySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}