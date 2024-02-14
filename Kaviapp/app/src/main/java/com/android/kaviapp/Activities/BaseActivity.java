package com.android.kaviapp.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.kaviapp.Utils.Utils;

import butterknife.ButterKnife;



public class BaseActivity extends AppCompatActivity {
    public Activity mActivity;
    public Utils utils;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=BaseActivity.this;
        utils=new Utils(mActivity);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
