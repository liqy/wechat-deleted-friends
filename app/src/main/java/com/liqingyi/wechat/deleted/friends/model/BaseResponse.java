package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqy on 16/1/8.
 */
public class BaseResponse implements Parcelable {
    public int Ret;
    public String ErrMsg;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Ret);
        dest.writeString(this.ErrMsg);
    }

    public BaseResponse() {
    }

    protected BaseResponse(Parcel in) {
        this.Ret = in.readInt();
        this.ErrMsg = in.readString();
    }

    public static final Parcelable.Creator<BaseResponse> CREATOR = new Parcelable.Creator<BaseResponse>() {
        public BaseResponse createFromParcel(Parcel source) {
            return new BaseResponse(source);
        }

        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };
}
