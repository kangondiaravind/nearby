package nearby.aravind.nearby.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Places {

    @SerializedName("results")
    private ArrayList<Results> list;

    public ArrayList<Results> getList() {
        return list;
    }

    public void setList(ArrayList<Results> list) {
        this.list = list;
    }
}
