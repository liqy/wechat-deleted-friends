package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by liqy on 16/1/8.
 */
public class User implements Parcelable {

    public int Uin;//
    public String UserName;//
    public String NickName;//
    public String HeadImgUrl;//
    public int ContactFlag;//用户类型
    public int MemberCount;
    public String RemarkName;//
    public int HideInputBarFlag;//
    public int Sex;//
    public String Signature;//
    public int VerifyFlag;//
    public int OwnerUin;
    public String PYInitial;//
    public String PYQuanPin;//
    public String RemarkPYInitial;//
    public String RemarkPYQuanPin;//
    public int StarFriend;//
    public int AppAccountFlag;//
    public int Statues;
    public int AttrStatus;
    public String Province;
    public String City;
    public String Alias;
    /**
     * 17
     */
    public int SnsFlag;//
    public int WebWxPluginSwitch;//
    public int HeadImgFlag;//
    public int UniFriend;
    public String DisplayName;
    public int ChatRoomId;
    public String KeyWord;
    public String EncryChatRoomId;

    @Override
    public String toString() {
        return "User{" +
                "NickName=" + NickName +
                "ContactFlag=" + ContactFlag +
                ", VerifyFlag=" + VerifyFlag +
                ", AppAccountFlag=" + AppAccountFlag +
                ", SnsFlag=" + SnsFlag +
                ", UniFriend=" + UniFriend +
                ", PYQuanPin='" + PYQuanPin + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Uin);
        dest.writeString(this.UserName);
        dest.writeString(this.NickName);
        dest.writeString(this.HeadImgUrl);
        dest.writeInt(this.ContactFlag);
        dest.writeInt(this.MemberCount);
        dest.writeString(this.RemarkName);
        dest.writeInt(this.HideInputBarFlag);
        dest.writeInt(this.Sex);
        dest.writeString(this.Signature);
        dest.writeInt(this.VerifyFlag);
        dest.writeInt(this.OwnerUin);
        dest.writeString(this.PYInitial);
        dest.writeString(this.PYQuanPin);
        dest.writeString(this.RemarkPYInitial);
        dest.writeString(this.RemarkPYQuanPin);
        dest.writeInt(this.StarFriend);
        dest.writeInt(this.AppAccountFlag);
        dest.writeInt(this.Statues);
        dest.writeInt(this.AttrStatus);
        dest.writeString(this.Province);
        dest.writeString(this.City);
        dest.writeString(this.Alias);
        dest.writeInt(this.SnsFlag);
        dest.writeInt(this.WebWxPluginSwitch);
        dest.writeInt(this.HeadImgFlag);
        dest.writeInt(this.UniFriend);
        dest.writeString(this.DisplayName);
        dest.writeInt(this.ChatRoomId);
        dest.writeString(this.KeyWord);
        dest.writeString(this.EncryChatRoomId);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.Uin = in.readInt();
        this.UserName = in.readString();
        this.NickName = in.readString();
        this.HeadImgUrl = in.readString();
        this.ContactFlag = in.readInt();
        this.MemberCount = in.readInt();
        this.RemarkName = in.readString();
        this.HideInputBarFlag = in.readInt();
        this.Sex = in.readInt();
        this.Signature = in.readString();
        this.VerifyFlag = in.readInt();
        this.OwnerUin = in.readInt();
        this.PYInitial = in.readString();
        this.PYQuanPin = in.readString();
        this.RemarkPYInitial = in.readString();
        this.RemarkPYQuanPin = in.readString();
        this.StarFriend = in.readInt();
        this.AppAccountFlag = in.readInt();
        this.Statues = in.readInt();
        this.AttrStatus = in.readInt();
        this.Province = in.readString();
        this.City = in.readString();
        this.Alias = in.readString();
        this.SnsFlag = in.readInt();
        this.WebWxPluginSwitch = in.readInt();
        this.HeadImgFlag = in.readInt();
        this.UniFriend = in.readInt();
        this.DisplayName = in.readString();
        this.ChatRoomId = in.readInt();
        this.KeyWord = in.readString();
        this.EncryChatRoomId = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
