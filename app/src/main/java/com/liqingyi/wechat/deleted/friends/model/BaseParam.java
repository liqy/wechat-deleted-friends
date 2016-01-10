package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by liqy on 16/1/8.
 */
public class BaseParam implements Parcelable {
    @SerializedName("BaseRequest")
    public BaseRequest baseRequest;

    public ArrayList<User> MemberList;
    public int MemberCount;
    public String Topic;

    public String ChatRoomName;
    public String DelMemberList;
    public String AddMemberList;

    public BaseParam(BaseRequest baseRequest) {
        this.baseRequest = baseRequest;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.baseRequest, 0);
        dest.writeInt(this.MemberCount);
        dest.writeString(this.Topic);
    }

    protected BaseParam(Parcel in) {
        this.baseRequest = in.readParcelable(BaseRequest.class.getClassLoader());
        this.MemberCount = in.readInt();
        this.Topic = in.readString();
    }

    public static final Parcelable.Creator<BaseParam> CREATOR = new Parcelable.Creator<BaseParam>() {
        public BaseParam createFromParcel(Parcel source) {
            return new BaseParam(source);
        }

        public BaseParam[] newArray(int size) {
            return new BaseParam[size];
        }
    };
}
