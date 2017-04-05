package test.megogo.megotest.mvp.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JSJEM on 04.04.2017.
 */

public class Genre {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
