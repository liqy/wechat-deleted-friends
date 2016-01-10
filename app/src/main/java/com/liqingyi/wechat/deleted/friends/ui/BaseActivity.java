package com.liqingyi.wechat.deleted.friends.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.liqingyi.wechat.deleted.friends.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liqin on 2016/1/6.
 */
public class BaseActivity extends AppCompatActivity {
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected Response.ErrorListener createReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        };
    }

    protected String finStr(String find, String response) {
        Pattern pattern = Pattern.compile(find);
        Matcher matcher = pattern.matcher(response);
        matcher.find();
        return matcher.group(1);
    }

    public void initBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bar = getSupportActionBar();
    }

}
