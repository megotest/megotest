package test.megogo.megotest.mvp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JSJEM on 04.04.2017.
 */

public class Language {

    @SerializedName("iso_639_1")
    private String code;
    @SerializedName("name")
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
