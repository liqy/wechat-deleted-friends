package com.liqingyi.wechat.deleted.friends.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        memberAdapter = new MemberAdapter();
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
        String url = base_uri + "/webwxcreatechatroom?pass_ticket=%1$s&r=%2$s";
        String body = new Gson().toJson(param);
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket, System.currentTimeMillis()),
                body,
                createChatRoomReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void addMember(String chatRoomName, String userNames) {
        param.ChatRoomName = chatRoomName;
        param.AddMemberList = userNames;
        String url = base_uri + "/webwxupdatechatroom?fun=addmember&pass_ticket=%1$s";
        String body = new Gson().toJson(param);
        JsonStringRequest request = new JsonStringRequest(Request.Method.POST,
                String.format(url, error.pass_ticket),
                body,
                createAddReqSuccessListener(),
                createReqErrorListener());
        VolleyClient.getRequestQueue().add(request);
    }

    public void deleteMember(String chatRoomName, String userNames) {
        param.ChatRoomName = chatRoomName;
        param.DelMemberList = userNames;
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
        ArrayList<User> list = memberAdapter.getMembers();

        param.MemberCount = list.size();
        param.MemberList = list;
        if (param.MemberCount > 1) {
            createChatRoom();
        }

    }

    private Response.Listener<String> createChatRoomReqSuccessListener() {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getLocalClassName(), response);
                Gson gson = new Gson();
                ResponseData data = gson.fromJson(response, ResponseData.class);
                String userNames = "";
                for (User user : data.MemberList) {
                    userNames += user.UserName + ",";
                }

                deleteMember(data.ChatRoomName, userNames.substring(0,userNames.length()-1));
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
