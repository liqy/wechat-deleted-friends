package com.liqingyi.wechat.deleted.friends.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.liqingyi.wechat.deleted.friends.R;
import com.liqingyi.wechat.deleted.friends.util.VolleyClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends BaseActivity {

    ImageView codeImage;
    CountDownTimer timer;
    String uuid;
    int tip = 1;
    String redirect_uri;
    String base_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeImage = (ImageView) findViewById(R.id.codeImage);
        timer = new CountDownTimer(60000, 5000) {

            @Override
            public void onTick(long millisUntilFinished) {
                waitLogin();
            }

            @Override
            public void onFinish() {
                waitLogin();
            }
        };
        loginChat();
    }

    public void loginChat() {
        String url = "https://login.weixin.qq.com/jslogin?appid=%1$s&fun=%2$s&lang=%3$s";
        StringRequest request = new StringRequest(Method.GET,
                String.format(url, "wx782c26e4c19acffb", "new", "zh_CN"),
                createLoginReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void qrCode() {
        String url = "https://login.weixin.qq.com/qrcode/%1$s?t=%2$s";
        Glide.with(this).load(String.format(url, uuid, "webwx")).into(codeImage);
        timer.start();
    }

    public void waitLogin() {
        String url = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%1$s&uuid=%2$s";
        StringRequest request = new StringRequest(Method.GET,
                String.format(url, tip, uuid),
                createCodeReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void login(){
        StringRequest request = new StringRequest(Method.GET,
                redirect_uri,
                createReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    private Response.Listener<String> createReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
            }
        };
    }

    private Response.Listener<String> createLoginReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                String find = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\"";
                Pattern pattern = Pattern.compile(find);
                Matcher matcher = pattern.matcher(response);
                matcher.find();

                String code = matcher.group(1);
                uuid = matcher.group(2);
                Log.i(getLocalClassName(), code);
                Log.i(getLocalClassName(), uuid);
                if ("200".equals(code)) {
                    qrCode();
                }
            }
        };
    }

    public String finStr(String find, String response) {
        Pattern pattern = Pattern.compile(find);
        Matcher matcher = pattern.matcher(response);
        matcher.find();
        return matcher.group(1);
    }

    private Response.Listener<String> createCodeReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                String code = finStr("window.code=(\\d+);", response);
                if ("201".equals(code)) {
                    tip = 0;
                    Log.i(getLocalClassName(), code + ":成功扫描,请在手机上点击确认以登录");
                } else if ("200".equals(code)) {
                    Log.i(getLocalClassName(), code + ":正在登录...");
                    timer.cancel();
                    redirect_uri = finStr("window.redirect_uri=\"(\\S+?)\";", response) + "&fun=new";
                    base_uri = redirect_uri.substring(0, redirect_uri.lastIndexOf("/"));

                    Log.i(getLocalClassName(), redirect_uri);
                    Log.i(getLocalClassName(), base_uri);

                    login();
                } else if ("408".equals("code")) {
                    Log.i(getLocalClassName(), code + ":超时");
                } else {
                    Log.i(getLocalClassName(), code + ":其他错误");
                }
            }
        };
    }

}
