package com.liqingyi.wechat.deleted.friends.model;

/**
 * Created by liqin on 2016/1/8.
 */
public class BaseRequest {
    public long Uin;
    public String Sid;
    public String Skey;
    public String DeviceID;

    public BaseRequest() {
    }

    public BaseRequest(Error error) {
        this.Uin = Long.parseLong(error.wxuin);
        this.Sid = error.wxsid;
        this.Skey = error.skey;
        this.DeviceID = "e000000000000000";
    }
}
