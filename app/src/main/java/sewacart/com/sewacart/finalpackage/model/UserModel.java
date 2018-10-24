package sewacart.com.sewacart.finalpackage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;


public class UserModel {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("user_details")
    @Expose
    private UserDetails userDetails;
    @SerializedName("data")
    @Expose
    private String data = "";

    @SerializedName("data_mob")
    @Expose
    private String data_mob = "";

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData_mob() {
        return data_mob;
    }

    public void setData_mob(String data_mob) {
        this.data_mob = data_mob;
    }

    @Parcel
    public static class UserDetails {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("extra_address")
        @Expose
        private String extraAddress;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("user_photo")
        @Expose
        private String userPhoto;
        @SerializedName("zip")
        @Expose
        private String zip;
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getExtraAddress() {
            return extraAddress;
        }

        public void setExtraAddress(String extraAddress) {
            this.extraAddress = extraAddress;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public UserDetails(String id, String name, String email, String role, String phone, String mobile, String state, String city, String street, String extraAddress, String description, String userPhoto, String zip) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.role = role;
            this.phone = phone;
            this.mobile = mobile;
            this.state = state;
            this.city = city;
            this.street = street;
            this.extraAddress = extraAddress;
            this.description = description;
            this.userPhoto = userPhoto;
            this.zip = zip;
        }
        public UserDetails() {
        }
    }
}