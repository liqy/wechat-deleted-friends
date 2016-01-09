package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqin on 2016/1/8.
 */
public class BaseRequest implements Parcelable {
    public long Uin;
    public String Sid;
    public String Skey;
    public String DeviceID;

    public BaseRequest() {
    }

    public BaseRequest(BaseError baseError) {
        this.Uin = Long.parseLong(baseError.wxuin);
        this.Sid = baseError.wxsid;
        this.Skey = baseError.skey;
        this.DeviceID = "e000000000000000";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Uin);
        dest.writeString(this.Sid);
        dest.writeString(this.Skey);
        dest.writeString(this.DeviceID);
    }

    protected BaseRequest(Parcel in) {
        this.Uin = in.readLong();
        this.Sid = in.readString();
        this.Skey = in.readString();
        this.DeviceID = in.readString();
    }

    public static final Parcelable.Creator<BaseRequest> CREATOR = new Parcelable.Creator<BaseRequest>() {
        public BaseRequest createFromParcel(Parcel source) {
            return new BaseRequest(source);
        }

        public BaseRequest[] newArray(int size) {
            return new BaseRequest[size];
        }
    };
}
