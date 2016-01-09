package com.liqingyi.wechat.deleted.friends.util;

import java.io.UnsupportedEncodingException;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

/**
 * Created by Giacomo on 30/11/2014.
 */
public class JsonStringRequest extends JsonRequest<String> {

    /**
     * 36.     * Creates a new request.
     * 37.     * @param url URL to fetch the JSON from
     * 38.     * @param listener Listener to receive the JSON response
     * 39.     * @param errorListener Error listener, or null to ignore errors.
     * 40.
     */
    public JsonStringRequest(int method, String url, String bodyData, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, bodyData, listener, errorListener);
    }

//    /**
//     * Passing some request headers
//     * */
//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> headers = new HashMap<String, String>();
//        headers.put("Content-Type", "application/json");
//        return headers;
//    }


    /**
     * Ricotruisce la risposta e restituisce un oggetto json
     * @param response
     * @return
     */
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "UTF-8");
            Response<String> responseString = Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
            return responseString;
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    //In your extended request class
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        Log.e("", this.getBodyContentType());
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }

        return volleyError;
    }
}
