package com.liqingyi.wechat.deleted.friends.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.liqingyi.wechat.deleted.friends.MemberAdapter;
import com.liqingyi.wechat.deleted.friends.R;
import com.liqingyi.wechat.deleted.friends.model.BaseError;
import com.liqingyi.wechat.deleted.friends.model.BaseParam;
import com.liqingyi.wechat.deleted.friends.model.ResponseData;
import com.liqingyi.wechat.deleted.friends.model.User;
import com.liqingyi.wechat.deleted.friends.util.JsonStringRequest;
import com.liqingyi.wechat.deleted.friends.util.VolleyClient;

import java.util.ArrayList;

public class MemberListActivity extends BaseActivity {

    private XRecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    BaseError error;
    BaseParam param;
    String base_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        error = getIntent().getParcelableExtra("Error");
        param = getIntent().getParcelableExtra("BaseParam");
        base_uri = getIntent().getStringExtra("base_uri");

        recyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        memberAdapter = new MemberAdapter();
        recyclerView.setAdapter(memberAdapter);

        webWXInit();
    }

    public void webWXInit() {
        String body = new Gson().toJson(param);
        String url = base_uri + "/webwxinit?pass_ticket=%1$s&skey=%2$s&r=%3$s";
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket, error.skey, System.currentTimeMillis()),
                body,
                createInitReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void webWXGetContact() {
        String body = new Gson().toJson(param);
        String url = base_uri + "/webwxgetcontact?pass_ticket=%1$s&skey=%2$s&r=%3$s";
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket, error.skey, System.currentTimeMillis()),
                body,
                createGetContactReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void createChatRoom() {
        String url = base_uri + "/webwxcreatechatroom?pass_ticket=%1$s&skey=%2$s&r=%3$s";
        String body = new Gson().toJson(param);
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket, error.skey, System.currentTimeMillis()),
                body,
                createChatRoomReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void addMember() {
        String url = base_uri + "/webwxupdatechatroom?fun=addmember&pass_ticket=%1$s&skey=%2$s&r=%3$s";

        StringRequest request = new StringRequest(Request.Method.GET,
                base_uri,
                createAddReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void deleteMember() {
        StringRequest request = new StringRequest(Request.Method.GET,
                base_uri,
                createDeleteReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    private Response.Listener<String> createInitReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                webWXGetContact();
            }
        };
    }

    private Response.Listener<String> createGetContactReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                Gson gson = new Gson();
                ResponseData data = gson.fromJson(response, ResponseData.class);
                memberAdapter.addMember(data.MemberList);
                ArrayList<User> list=new ArrayList<>();
                for (User user : data.MemberList) {
                    if (user.Sex==2){
                        list.add(user);
                    }
                }
                param.MemberCount = list.size();
                param.MemberList=list;
                createChatRoom();
            }
        };
    }

    private Response.Listener<String> createChatRoomReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
            }
        };
    }

    private Response.Listener<String> createDeleteReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
            }
        };
    }

    private Response.Listener<String> createAddReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
            }
        };
    }

}
