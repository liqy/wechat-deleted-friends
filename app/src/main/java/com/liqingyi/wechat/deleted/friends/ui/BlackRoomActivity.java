package com.liqingyi.wechat.deleted.friends.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.liqingyi.wechat.deleted.friends.R;
import com.liqingyi.wechat.deleted.friends.model.User;
import com.liqingyi.wechat.deleted.friends.ui.adapter.MemberAdapter;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

public class BlackRoomActivity extends BaseActivity {
    private SuperRecyclerView recyclerView;
    private MemberAdapter memberAdapter;
    String base_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_room);
        initBar();

        base_uri = getIntent().getStringExtra("base_uri");
        ArrayList<User> users = getIntent().getParcelableArrayListExtra("blackUsers");

        recyclerView = (SuperRecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        memberAdapter = new MemberAdapter(this, base_uri);
        memberAdapter.setHasStableIds(true);
        recyclerView.setAdapter(memberAdapter);
        memberAdapter.addMember(users);
    }

    @Override
    public void initBar() {
        super.initBar();
        bar.setTitle("小黑屋里的坏人");
        bar.setDisplayHomeAsUpEnabled(true);
    }
}
