package com.liqingyi.wechat.deleted.friends.model;

/**
 * Created by liqin on 2016/1/6.
 */
public class Error {

    public int ret;

    public String message;

    public String skey;

    public String wxsid;

    public String wxuin;

    public String pass_ticket;

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
