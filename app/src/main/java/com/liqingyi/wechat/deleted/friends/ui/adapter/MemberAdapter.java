package com.liqingyi.wechat.deleted.friends.ui.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liqingyi.wechat.deleted.friends.R;
import com.liqingyi.wechat.deleted.friends.model.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by liqy on 16/1/9.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    ArrayList<User> users;
    ArrayList<User> selectMembers;
    Activity activity;
    public String base_uri;
    Map<String, User> memberMap;

    public MemberAdapter(Activity activity, String base_uri) {
        this.activity = activity;
        this.base_uri = base_uri;
        this.users = new ArrayList<>();
        this.selectMembers = new ArrayList<>();
        this.memberMap = new Hashtable<>();
    }

    public void addMember(ArrayList<User> list) {
        this.users.addAll(list);

        for (User user : list) {
            memberMap.put(user.UserName, user);
        }

        notifyDataSetChanged();
    }

    public ArrayList<User> getSelectMembers() {
        return selectMembers;
    }

    public void selectMember(int pos) {
        User user = this.users.get(pos);
        if (user.isCheck) {
            user.isCheck = false;
        } else {
            user.isCheck = true;
        }
        this.selectMembers.add(user);
        notifyDataSetChanged();
    }

    public void resetMembers() {
        this.selectMembers.clear();
    }

    public Map<String, User> getMemberMap() {
        return memberMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.nickName.setText(Html.fromHtml(user.NickName));
        if (user.isCheck) {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.io15_blue_grey_100));
        } else {
            holder.layout.setBackgroundColor(activity.getResources().getColor(R.color.io15_white));
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public TextView nickName;

        public ViewHolder(View view) {
            super(view);
            layout = (LinearLayout) view.findViewById(R.id.layout);
            nickName = (TextView) view.findViewById(R.id.nickName);
        }
    }
}
