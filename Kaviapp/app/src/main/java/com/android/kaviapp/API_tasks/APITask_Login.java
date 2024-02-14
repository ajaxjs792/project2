package com.android.kaviapp.API_tasks;


import android.content.Context;
import android.util.Log;

import com.android.kaviapp.Constants.Constants_API;
import com.android.kaviapp.Models.User;
import com.android.kaviapp.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;







public class APITask_Login {
    private static final String MODULE = "APITask_Login";
    private static String TAG = "", Str_Msg = "";

    private String Str_Url = Constants_API.login;
    private Context mContext;

    public interface Listener_Login{
        void onLoginSuccess(User user);
        void onLoginFailure(String mes);
    }

    private Listener_Login mCallBack;

    public APITask_Login(Listener_Login mCallBack, Context mContext) {
        this.mContext = mContext;
        this.mCallBack = mCallBack;
    }

    public void login(JSONObject params) {

        TAG = "login";
        Log.d(MODULE, TAG);




        try {

            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                    Str_Url, params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.e("response", "" + response);

                        String id = response.getString("_id");
                        int mobile = response.getInt("mobile");
                        String password = response.getString("password");
                        String name = response.getString("name");
                        User user=new User( id, mobile, password, name);
                        mCallBack.onLoginSuccess(user);


                    } catch (Exception e) {

                        mCallBack.onLoginFailure("oops! Please try again!");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                    VolleyLog.e("Error: ", error.getMessage());
                    Log.e(MODULE, TAG + " UnknownResponse");


                    if (error instanceof NetworkError) {
                        Str_Msg = mContext.getResources().getString(R.string.msg_no_internet);
                    } else if (error instanceof ServerError) {
                        Str_Msg = mContext.getResources().getString(R.string.msg_unexpected_error);
                    } else if (error instanceof AuthFailureError) {
                        Str_Msg = mContext.getResources().getString(R.string.msg_unexpected_error);
                    } else if (error instanceof ParseError) {
                        Str_Msg = mContext.getResources().getString(R.string.msg_unexpected_error);
                    } else if (error instanceof NoConnectionError) {
                        Str_Msg = mContext.getResources().getString(R.string.msg_no_internet);
                    } else if (error instanceof TimeoutError) {
                        Str_Msg = mContext.getResources().getString(R.string.msg_unexpected_error);
                    }

                    mCallBack.onLoginFailure("oops! Please try again!");
                }
            }) {
            };


            int socketTimeout = 60000;// 30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(policy);
            Volley.newRequestQueue(mContext).add(req);

        } catch (Exception e) {
            Log.e(MODULE, TAG + " Exception Occurs - " + e);
            mCallBack.onLoginFailure("oops! Please try again!");
        }

    }


}