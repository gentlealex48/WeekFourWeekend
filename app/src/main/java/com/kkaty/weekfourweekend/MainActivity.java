package com.kkaty.weekfourweekend;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String TOKEN = "d454e8485ed50688a6ea1e662a9271a0";
    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    VolleyService.IResult resultCallback = null;
    VolleyService volleyService;

    Context context;
    EditText etZip;
    TextView tvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();

        etZip = findViewById(R.id.etZip);
        tvMain = findViewById(R.id.tvMain);
    }

    public void onClick(View view) {

        Log.d(TAG, "onClick: STARTED");
        //get the zip
        String zip = etZip.getText().toString();

        //call api to get data
        String result = "Rain";
        //WORKING:
        //RemoteData remoteData = new RemoteData(getApplicationContext());
        //result = remoteData.getResults();
        //END WORKING:

        String url = "api.openweathermap.org/data/2.5/weather?q=Phoenix&appid=d454e8485ed50688a6ea1e662a9271a0";

        // BROKEN getResponse(url, null, RemoteData.VolleyCallback){        };

        //Per: https://stackoverflow.com/questions/35628142/how-to-make-separate-class-for-volley-library-and-call-all-method-of-volley-from
        initVolleyCallback();
        Log.d(TAG, "onClick: initVolleyCallback() COMPLETED");

        volleyService = new VolleyService(resultCallback,context);
        Log.d(TAG, "onClick: volleyService instantiated");

        //volleyService.getDataVolley("GETCALL", "http://192.168.1.150/datatest/get/data");
        volleyService.getDataVolley("GETCALL", url);
        Log.d(TAG, "onClick: volleyService.getDataVolley COMPLETED");
        Log.d(TAG, "onClick: response: " + resultCallback.toString());
//        JSONObject sendObj = null;
//        try {
//            sendObj = new JSONObject("{'Test':'Test'}");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        volleyService.postDataVolley("POSTCALL", "http://192.168.1.150/datatest/post/data", sendObj);


        //present data on screen
        tvMain.setText(result);

    }

    void initVolleyCallback(){
        resultCallback = new VolleyService.IResult() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post " + response);
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post: " + "That didn't work!");
            }
        };
    }


}
