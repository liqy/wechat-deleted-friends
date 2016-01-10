package com.liqingyi.wechat.deleted.friends.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.liqingyi.wechat.deleted.friends.ui.adapter.MemberAdapter;
import com.liqingyi.wechat.deleted.friends.R;
import com.liqingyi.wechat.deleted.friends.model.BaseError;
import com.liqingyi.wechat.deleted.friends.model.BaseParam;
import com.liqingyi.wechat.deleted.friends.model.ResponseData;
import com.liqingyi.wechat.deleted.friends.model.User;
import com.liqingyi.wechat.deleted.friends.util.JsonStringRequest;
import com.liqingyi.wechat.deleted.friends.util.RecyclerUtils;
import com.liqingyi.wechat.deleted.friends.util.VolleyClient;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

public class MemberListActivity extends BaseActivity {

    private SuperRecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    BaseError error;
    BaseParam param;
    String base_uri;
    Button createRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);
        initBar();

        error = getIntent().getParcelableExtra("Error");
        param = getIntent().getParcelableExtra("BaseParam");
        base_uri = getIntent().getStringExtra("base_uri");

        createRoom = (Button) findViewById(R.id.createRoom);
        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoom();
            }
        });

        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        memberAdapter = new MemberAdapter(this, base_uri);
        memberAdapter.setHasStableIds(true);
        recyclerView.setAdapter(memberAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerUtils.RecyclerItemClickListener(this, new RecyclerUtils.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                memberAdapter.selectMember(position);
            }
        }));

        webWXInit();
    }

    @Override
    public void initBar() {
        super.initBar();
        bar.setDisplayHomeAsUpEnabled(true);
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

    public void createChatRoom(ArrayList<User> list) {
        String url = base_uri + "/webwxcreatechatroom?pass_ticket=%1$s&r=%2$s";
        param.MemberList = list;
        String body = new Gson().toJson(param);
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket, System.currentTimeMillis()),
                body,
                createChatRoomReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void addMember(ArrayList<User> list) {
        String userNames = "";
        for (User user : list) {
            userNames += user.UserName + ",";
        }
        param.AddMemberList = userNames.substring(0, userNames.length() - 1);
        String url = base_uri + "/webwxupdatechatroom?fun=addmember&pass_ticket=%1$s";
        String body = new Gson().toJson(param);
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket),
                body,
                createAddReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void deleteMember(ArrayList<User> list) {
        String userNames = "";
        for (User user : list) {
            userNames += user.UserName + ",";
        }
        param.DelMemberList = userNames.substring(0, userNames.length() - 1);
        String url = base_uri + "/webwxupdatechatroom?fun=delmember&pass_ticket=%1$s";
        String body = new Gson().toJson(param);
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket),
                body,
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
            }
        };
    }

    public void createRoom() {
        ArrayList<User> list = memberAdapter.getSelectMembers();
        //已选择的群组成员
        param.MemberCount = list.size();
        if (param.MemberCount > 1) {
            if (TextUtils.isEmpty(param.ChatRoomName)) {
                createChatRoom(list);
            } else {
                addMember(list);
            }
        } else {
            Toast.makeText(MemberListActivity.this, "点击选择好友之后再点，不要这么着急...", Toast.LENGTH_SHORT).show();
        }
    }

    private Response.Listener<String> createChatRoomReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                Gson gson = new Gson();
                ResponseData data = gson.fromJson(response, ResponseData.class);
                param.ChatRoomName = data.ChatRoomName;
                deleteMember(data.MemberList);
            }
        };
    }

    private Response.Listener<String> createAddReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                Gson gson = new Gson();
                ResponseData data = gson.fromJson(response, ResponseData.class);
                deleteMember(data.MemberList);
            }
        };
    }


    private Response.Listener<String> createDeleteReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                memberAdapter.resetMembers();
            }
        };
    }

}
