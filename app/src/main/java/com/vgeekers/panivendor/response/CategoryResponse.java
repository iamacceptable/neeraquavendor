
package com.vgeekers.panivendor.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {

    @SerializedName("category")
    @Expose
    private List<String> category = new ArrayList<>();

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

}
