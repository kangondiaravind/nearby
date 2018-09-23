package nearby.aravind.nearby.Model;

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
