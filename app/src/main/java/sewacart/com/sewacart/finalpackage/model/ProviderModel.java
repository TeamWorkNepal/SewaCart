package sewacart.com.sewacart.finalpackage.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("organization")
    @Expose
    private Integer organization;
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("service_provider")
    @Expose
    private Integer serviceProvider;
    @SerializedName("full_address_secondary")
    @Expose
    private String fullAddressSecondary;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_1")
    @Expose
    private String mobile1;
    @SerializedName("mobile_2")
    @Expose
    private String mobile2;
    @SerializedName("service_cost")
    @Expose
    private String serviceCost;
    @SerializedName("cost_interval")
    @Expose
    private String costInterval;
    @SerializedName("min_service_hour")
    @Expose
    private String minServiceHour;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("cropImage")
    @Expose
    private String cropImage;
    @SerializedName("fullImage")
    @Expose
    private String fullImage;
    @SerializedName("categories")
    @Expose
    private String categories;
    @SerializedName("region")
    @Expose
    private String region;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Integer getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(Integer serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getFullAddressSecondary() {
        return fullAddressSecondary;
    }

    public void setFullAddressSecondary(String fullAddressSecondary) {
        this.fullAddressSecondary = fullAddressSecondary;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getCostInterval() {
        return costInterval;
    }

    public void setCostInterval(String costInterval) {
        this.costInterval = costInterval;
    }

    public String getMinServiceHour() {
        return minServiceHour;
    }

    public void setMinServiceHour(String minServiceHour) {
        this.minServiceHour = minServiceHour;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public String getFullImage() {
        return fullImage;
    }

    public void setFullImage(String fullImage) {
        this.fullImage = fullImage;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}