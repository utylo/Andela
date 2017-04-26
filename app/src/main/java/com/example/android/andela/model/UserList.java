package com.example.android.andela.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by UTYLO on 4/26/2017.
 */
public class UserList {
    @SerializedName("items")
    @Expose
    private List<User> items = null;

    public List<User> getItems() {
        return items;
    }

    public void setItems(List<User> items) {
        this.items = items;
    }
}
