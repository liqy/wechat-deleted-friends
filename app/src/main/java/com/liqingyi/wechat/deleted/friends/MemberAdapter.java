package com.liqingyi.wechat.deleted.friends;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liqingyi.wechat.deleted.friends.model.User;

import java.util.ArrayList;

/**
 * Created by liqy on 16/1/9.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    ArrayList<User> users;

    public MemberAdapter() {
        this.users = new ArrayList<>();
    }

    public void addMember(ArrayList<User> list) {
        this.users.addAll(list);
        notifyDataSetChanged();
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
        holder.nickName.setText(user.NickName);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nickName;

        public ViewHolder(View view) {
            super(view);
            nickName = (TextView) view.findViewById(R.id.nickName);
        }
    }
}
