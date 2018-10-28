package sewacart.com.sewacart.finalpackage.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class HomeModel {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("service_by_category")
    @Expose
    private List<ServiceByCategory> serviceByCategory = null;

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<ServiceByCategory> getServiceByCategory() {
        return serviceByCategory;
    }

    public void setServiceByCategory(List<ServiceByCategory> serviceByCategory) {
        this.serviceByCategory = serviceByCategory;
    }

    public class Service {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("cropImage")
        @Expose
        private String cropImage;

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

        public String getCropImage() {
            return cropImage;
        }

        public void setCropImage(String cropImage) {
            this.cropImage = cropImage;
        }

    }

    public class ServiceByCategory {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("service")
        @Expose
        private List<Service> service = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Service> getService() {
            return service;
        }

        public void setService(List<Service> service) {
            this.service = service;
        }

    }

    public class Category {

        @SerializedName("category_id")
        @Expose
        private Integer categoryId;
        @SerializedName("category_title")
        @Expose
        private String categoryTitle;
        @SerializedName("category_image")
        @Expose
        private String categoryImage;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("link")
        @Expose
        private String link;

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryTitle() {
            return categoryTitle;
        }

        public void setCategoryTitle(String categoryTitle) {
            this.categoryTitle = categoryTitle;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

    }
}



