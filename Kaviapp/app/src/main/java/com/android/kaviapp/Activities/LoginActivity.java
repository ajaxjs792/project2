package com.android.kaviapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kaviapp.API_tasks.APITask_Login;
import com.android.kaviapp.Models.User;
import com.android.kaviapp.R;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, APITask_Login.Listener_Login {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int REQUEST_SIGNUP = 0;

    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USERNAME = "name";

    private EditText editTextMobile;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView Signup;
    private String name;
    private String mobile;
    private String password;
    private Activity mActivity;
    private ProgressDialog pd;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //if SharedPreferences contains username and password then directly redirect to Home activity
        if(sharedpreferences.contains("mobile") && sharedpreferences.contains("password")){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();   //finish current activity
        }



    }


    private void initUI() {
        try {
            mActivity = LoginActivity.this;
            editTextMobile = (EditText) findViewById(R.id.input_mobile);
            editTextPassword = (EditText) findViewById(R.id.input_password);

            buttonLogin = (Button) findViewById(R.id.btn_login);
            Signup = (TextView) findViewById(R.id.link_signup);
            buttonLogin.setOnClickListener(this);
            Signup.setOnClickListener(this);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void userLogin() {
        mobile = editTextMobile.getText().toString();
        password = editTextPassword.getText().toString();
        JSONObject params = new JSONObject();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KEY_MOBILE, mobile);
        editor.commit();
        try {

            params.put(KEY_MOBILE, mobile);
            params.put(KEY_PASSWORD, password);
            Log.d("userLogin","->"+params);
            APITask_Login apiTaskLogin = new APITask_Login(this,mActivity);
            apiTaskLogin.login(params);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }


    }


    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            userLogin();
            pd= ProgressDialog.show(LoginActivity.this,"","Please Wait",false);

        }
        if (v == Signup) {
            startActivity(new Intent(this, SignupActivity.class));
        }

    }

    @Override
    public void onLoginSuccess(User user) {


        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra(KEY_USERNAME, user.name);
        Log.e("USER_NAME","->"+ user.name);
        startActivity(intent);
        finish();
        pd.dismiss();

    }

    @Override
    public void onLoginFailure(String mes) {

        Toast.makeText(LoginActivity.this,"Login Failed", Toast.LENGTH_LONG).show();
        pd.dismiss();

    }
}