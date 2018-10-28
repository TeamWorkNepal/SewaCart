package sewacart.com.sewacart.finalpackage.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddReviewModel {

    @SerializedName("data")
    @Expose
    private Integer data;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}