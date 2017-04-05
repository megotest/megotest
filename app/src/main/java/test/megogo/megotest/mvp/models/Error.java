package test.megogo.megotest.mvp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JSJEM on 05.04.2017.
 */

public class Error {

    @SerializedName("status_code")
    private int statusCode;
    @SerializedName("status_message")
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
