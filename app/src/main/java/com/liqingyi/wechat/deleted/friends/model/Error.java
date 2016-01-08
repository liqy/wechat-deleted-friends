package com.liqingyi.wechat.deleted.friends.model;

import me.tatarka.parsnip.annotations.SerializedName;
import me.tatarka.parsnip.annotations.Tag;

/**
 * Created by liqin on 2016/1/6.
 */
@SerializedName("error")
public class Error {

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

    public Error() {
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
}
