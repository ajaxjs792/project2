package com.android.kaviapp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.kaviapp.API_tasks.APITask_Signup;
import com.android.kaviapp.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;






public class SignupActivity extends AppCompatActivity implements View.OnClickListener, APITask_Signup.Listener_Signup {

    public static final String MyPREFERENCES = "MyPrefs" ;
    private static final int REQUEST_SIGNUP = 0;

    public static final String KEY_USERNAME= "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE =  "mobile";
    public static final String KEY_PASSWORD = "password";

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextNumber;
    private EditText editTextPassword;
    private Button buttonRegister;
    private TextView Login;

    private String user_name;
    private String mobile;
    private String email;
    private String password;
    private Activity mActivity;
    private ProgressDialog pd;
    SharedPreferences sharedpreferences;
    private AwesomeValidation Validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Validation = new AwesomeValidation(ValidationStyle.BASIC);
        initUI();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


    }


    private void initUI() {
        try {
            mActivity = SignupActivity.this;
            editTextName = (EditText) findViewById(R.id.input_name);
            editTextEmail = (EditText) findViewById(R.id.input_email);
            editTextPassword = (EditText) findViewById(R.id.input_password);
            editTextNumber = (EditText) findViewById(R.id.input_mobile);


            //adding validation to edittexts
            Validation.addValidation(this, R.id.input_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
            Validation.addValidation(this, R.id.input_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
            Validation.addValidation(this, R.id.input_mobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);



            buttonRegister = (Button) findViewById(R.id.btn_signup);
            Login = (TextView) findViewById(R.id.link_login);
            buttonRegister.setOnClickListener(this);
            Login.setOnClickListener(this);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void userRegister() {
        user_name = editTextName.getText().toString();
        mobile = editTextNumber.getText().toString();
        password = editTextPassword.getText().toString();
        email = editTextEmail.getText().toString();
        JSONObject params = new JSONObject();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(KEY_USERNAME, user_name);
        editor.commit();
        try {
            params.put(KEY_USERNAME, user_name);
            params.put(KEY_MOBILE, mobile);
            params.put(KEY_PASSWORD, password);
            params.put(KEY_EMAIL, email);
            Log.e("userRegister","->"+params);
            APITask_Signup apiTask_signup = new APITask_Signup(this,mActivity);
            apiTask_signup.signup(params);

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }


    }


    @Override
    public void onClick(View v) {
        if (Validation.validate()) {
            if (v == buttonRegister) {
                userRegister();
                pd = ProgressDialog.show(SignupActivity.this, "", "Please Wait", false);

            }
        }
        if (v == Login) {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }


    @Override
    public void onSuccess(String user) {

        Toast.makeText(SignupActivity.this, "Your Registered Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        pd.dismiss();


    }

    @Override
    public void onFailure(String mes) {

        Toast.makeText(SignupActivity.this,"Login Failed", Toast.LENGTH_LONG).show();
        pd.dismiss();


    }
}