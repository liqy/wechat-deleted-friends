package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by liqy on 16/1/8.
 */
public class ResponseData implements Parcelable {
    public BaseResponse BaseResponse;
    public int Count;
    public int MemberCount;
    public ArrayList<User> ContactList;
    public ArrayList<User> MemberList;
    public User User;
    public String ChatSet;
    public String SKey;
    public long ClientVersion;
    public long SystemTime;
    public int GrayScale;
    public int InviteStartCount;
    public int MPSubscribeMsgCount;
    public int ClickReportInterval;
    public int Seq;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.BaseResponse, 0);
        dest.writeInt(this.Count);
        dest.writeInt(this.MemberCount);
        dest.writeTypedList(ContactList);
        dest.writeTypedList(MemberList);
        dest.writeParcelable(this.User, 0);
        dest.writeString(this.ChatSet);
        dest.writeString(this.SKey);
        dest.writeLong(this.ClientVersion);
        dest.writeLong(this.SystemTime);
        dest.writeInt(this.GrayScale);
        dest.writeInt(this.InviteStartCount);
        dest.writeInt(this.MPSubscribeMsgCount);
        dest.writeInt(this.ClickReportInterval);
        dest.writeInt(this.Seq);
    }

    public ResponseData() {
    }

    protected ResponseData(Parcel in) {
        this.BaseResponse = in.readParcelable(com.liqingyi.wechat.deleted.friends.model.BaseResponse.class.getClassLoader());
        this.Count = in.readInt();
        this.MemberCount = in.readInt();
        this.ContactList = in.createTypedArrayList(com.liqingyi.wechat.deleted.friends.model.User.CREATOR);
        this.MemberList = in.createTypedArrayList(com.liqingyi.wechat.deleted.friends.model.User.CREATOR);
        this.User = in.readParcelable(com.liqingyi.wechat.deleted.friends.model.User.class.getClassLoader());
        this.ChatSet = in.readString();
        this.SKey = in.readString();
        this.ClientVersion = in.readLong();
        this.SystemTime = in.readLong();
        this.GrayScale = in.readInt();
        this.InviteStartCount = in.readInt();
        this.MPSubscribeMsgCount = in.readInt();
        this.ClickReportInterval = in.readInt();
        this.Seq = in.readInt();
    }

    public static final Parcelable.Creator<ResponseData> CREATOR = new Parcelable.Creator<ResponseData>() {
        public ResponseData createFromParcel(Parcel source) {
            return new ResponseData(source);
        }

        public ResponseData[] newArray(int size) {
            return new ResponseData[size];
        }
    };
}
