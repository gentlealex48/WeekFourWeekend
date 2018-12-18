package com.kkaty.weekfourweekend;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
public class VolleyService {

    public static final String TAG = VolleyService.class.getSimpleName() + "_TAG";

    IResult resultCallback = null;
    Context context;

    VolleyService(IResult mresultCallback, Context mcontext){
        resultCallback = mresultCallback;
        context = mcontext;
    }

    public interface IResult {
        public void notifySuccess(String requestType,JSONObject response);
        public void notifyError(String requestType, VolleyError error);
    }

    public void postDataVolley(final String requestType, String url, JSONObject sendObj){
        try{
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url, sendObj
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (resultCallback != null) resultCallback.notifySuccess(requestType, response);
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(resultCallback != null)
                        resultCallback.notifyError(requestType,error);
                }
            }
            );
            queue.add(jsonObj);
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "postDataVolley: ERROR: " + e.getMessage());
        }
    }

    public void getDataVolley(final String requestType, String url){
        try{
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    if (resultCallback != null)
                        resultCallback.notifySuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (resultCallback != null) resultCallback.notifyError(requestType,error);
                }
            }
            );
            queue.add(jsonObj);
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "getDataVolley: ERROR: " + e.getMessage());
        }
    }
}
