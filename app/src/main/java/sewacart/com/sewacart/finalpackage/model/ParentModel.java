package sewacart.com.sewacart.finalpackage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ParentModel {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;
    @SerializedName("region")
    @Expose
    private List<Region> region = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Region> getRegion() {
        return region;
    }

    public void setRegion(List<Region> region) {
        this.region = region;
    }

    public class Region {

        @SerializedName("region_id")
        @Expose
        private Integer regionId;
        @SerializedName("region_title")
        @Expose
        private String regionTitle;

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }

        public String getRegionTitle() {
            return regionTitle;
        }

        public void setRegionTitle(String regionTitle) {
            this.regionTitle = regionTitle;
        }

    }

    public class Category {

        @SerializedName("cat_id")
        @Expose
        private Integer catId;
        @SerializedName("cat_title")
        @Expose
        private String catTitle;

        public Integer getCatId() {
            return catId;
        }

        public void setCatId(Integer catId) {
            this.catId = catId;
        }

        public String getCatTitle() {
            return catTitle;
        }

        public void setCatTitle(String catTitle) {
            this.catTitle = catTitle;
        }

    }

}


