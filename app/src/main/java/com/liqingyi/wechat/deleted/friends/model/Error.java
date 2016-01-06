package com.liqingyi.wechat.deleted.friends.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by liqin on 2016/1/6.
 */
@Root
public class Error {

    @Element
    public int ret;

    @Element
    public String message;

    @Element
    public String skey;

    @Element
    public String wxsid;

    @Element
    public String wxuin;

    @Element
    public String pass_ticket;

    @Element
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
