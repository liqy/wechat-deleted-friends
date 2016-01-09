package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

import me.tatarka.parsnip.annotations.SerializedName;
import me.tatarka.parsnip.annotations.Tag;

/**
 * Created by liqin on 2016/1/6.
 */
@SerializedName("error")
public class BaseError implements Parcelable {

    @Tag
    public int ret;

    @Tag
    public String message;

    @Tag
    public String skey;

    @Tag
    public String wxsid;

    @Tag
    public String wxuin;

    @Tag
    public String pass_ticket;

    @Tag
    public int isgrayscale;

    public BaseError() {
    }

    @Override
    public String toString() {
        return "Error{" +
                "ret=" + ret +
                ", message='" + message + '\'' +
                ", skey='" + skey + '\'' +
                ", wxsid='" + wxsid + '\'' +
                ", wxuin='" + wxuin + '\'' +
                ", pass_ticket='" + pass_ticket + '\'' +
                ", isgrayscale=" + isgrayscale +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ret);
        dest.writeString(this.message);
        dest.writeString(this.skey);
        dest.writeString(this.wxsid);
        dest.writeString(this.wxuin);
        dest.writeString(this.pass_ticket);
        dest.writeInt(this.isgrayscale);
    }

    protected BaseError(Parcel in) {
        this.ret = in.readInt();
        this.message = in.readString();
        this.skey = in.readString();
        this.wxsid = in.readString();
        this.wxuin = in.readString();
        this.pass_ticket = in.readString();
        this.isgrayscale = in.readInt();
    }

    public static final Parcelable.Creator<BaseError> CREATOR = new Parcelable.Creator<BaseError>() {
        public BaseError createFromParcel(Parcel source) {
            return new BaseError(source);
        }

        public BaseError[] newArray(int size) {
            return new BaseError[size];
        }
    };
}
