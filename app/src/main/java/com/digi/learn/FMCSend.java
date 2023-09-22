package com.digi.learn;

import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class FMCSend {
    public static final String BASE_URL = "https://fcm.googleapis.com/fcm/send";
    public static final String SEVER_KEY = "key=AAAAHvIX1Fs:APA91bFx9m8s43md3uJMLbvGSDt-0QNQHc30t_Z1UI1hT2uI_wUw7CyB0_FDdODUFJy72QmlXC-1Wu7Ok4H52vzjsdUTnT7fJrGKSVRMfQGpZpFAGZF4mS_WYlqRjwh6FQpJIOwAvy0q";


    public static void pushNotification(Context context, String token, String title, String message){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        RequestQueue queue = Volley.newRequestQueue(context);


        try {
            JSONObject json = new JSONObject();
            json.put("to", token);
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", message);
            json.put("notification", notification);

            //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest()


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL,  json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, "Bingo Success", Toast.LENGTH_SHORT).show();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Oops error", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type","application/json");
                    params.put("Authorization", SEVER_KEY);
                    return params;
                }};


            queue.add(jsonObjectRequest);

            } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


}
