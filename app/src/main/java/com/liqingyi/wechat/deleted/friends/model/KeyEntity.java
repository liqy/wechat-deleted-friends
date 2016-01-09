package com.liqingyi.wechat.deleted.friends.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liqy on 16/1/8.
 */
public class KeyEntity implements Parcelable {
    public int Key;
    public long Val;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Key);
        dest.writeLong(this.Val);
    }

    public KeyEntity() {
    }

    protected KeyEntity(Parcel in) {
        this.Key = in.readInt();
        this.Val = in.readLong();
    }

    public static final Parcelable.Creator<KeyEntity> CREATOR = new Parcelable.Creator<KeyEntity>() {
        public KeyEntity createFromParcel(Parcel source) {
            return new KeyEntity(source);
        }

        public KeyEntity[] newArray(int size) {
            return new KeyEntity[size];
        }
    };
}
