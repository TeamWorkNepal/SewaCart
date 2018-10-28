package sewacart.com.sewacart.finalpackage.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("post_status")
    @Expose
    private String postStatus;
    @SerializedName("written_by")
    @Expose
    private String writtenBy;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("show_in_home_page")
    @Expose
    private Integer showInHomePage;
    @SerializedName("image")
    @Expose
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getShowInHomePage() {
        return showInHomePage;
    }

    public void setShowInHomePage(Integer showInHomePage) {
        this.showInHomePage = showInHomePage;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}