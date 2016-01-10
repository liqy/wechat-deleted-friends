package com.liqingyi.wechat.deleted.friends.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.liqingyi.wechat.deleted.friends.R;
import com.liqingyi.wechat.deleted.friends.model.*;
import com.liqingyi.wechat.deleted.friends.model.BaseError;
import com.liqingyi.wechat.deleted.friends.util.Utils;
import com.liqingyi.wechat.deleted.friends.util.VolleyClient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.tatarka.parsnip.Xml;
import me.tatarka.parsnip.XmlAdapter;

public class MainActivity extends BaseActivity {

    ImageView codeImage;
    CountDownTimer timer;
    String uuid;
    int tip = 1;
    String redirect_uri;
    String base_uri;
    BaseError baseError;
    BaseParam param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBar();

        codeImage = (ImageView) findViewById(R.id.codeImage);
        codeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveImageToGallery(MainActivity.this, Utils.drawableToBitmap(codeImage.getDrawable()));
                Toast.makeText(MainActivity.this,"刚快按照使用说明操作吧",Toast.LENGTH_SHORT).show();
            }
        });
        timer = new CountDownTimer(60000, 5000) {

            @Override
            public void onTick(long millisUntilFinished) {
                waitForLogin();
            }

            @Override
            public void onFinish() {
                waitForLogin();
            }
        };
        getUUID();
    }

    public void getUUID() {
        String url = "https://login.weixin.qq.com/jslogin?appid=%1$s&fun=%2$s&lang=%3$s&_=%4$s";
        StringRequest request = new StringRequest(Method.GET,
                String.format(url, "wx782c26e4c19acffb", "new", "zh_CN", System.currentTimeMillis()),
                createUUIDReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void showQRImage() {
        String url = "https://login.weixin.qq.com/qrcode/%1$s?t=%2$s";
        Glide.with(this).load(String.format(url, uuid, "webwx")).into(codeImage);
        timer.start();
    }

    public void waitForLogin() {
        String url = "https://login.weixin.qq.com/cgi-bin/mmwebwx-bin/login?tip=%1$s&uuid=%2$s&_=%3$s";
        StringRequest request = new StringRequest(Method.GET,
                String.format(url, tip, uuid, System.currentTimeMillis()),
                createCodeReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void login() {
        StringRequest request = new StringRequest(Method.GET,
                redirect_uri,
                createLoginReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }


    private Response.Listener<String> createUUIDReqSuccessListener() {
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
                    showQRImage();
                }
            }
        };
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

    private Response.Listener<String> createLoginReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                try {
                    Xml xml = new Xml.Builder().build();
                    XmlAdapter<BaseError> xmlAdapter = xml.adapter(BaseError.class);
                    baseError = xmlAdapter.fromXml(response);
                    BaseRequest baseRequest = new BaseRequest(baseError);
                    param = new BaseParam(baseRequest);

                    Intent intent = new Intent(MainActivity.this, MemberListActivity.class);
                    intent.putExtra("Error", baseError);
                    intent.putExtra("BaseParam", param);
                    intent.putExtra("base_uri", base_uri);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
