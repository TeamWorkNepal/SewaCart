package sewacart.com.sewacart.finalpackage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ViewCartModel {

    @SerializedName("cart")
    @Expose
    private List<Cart> cart = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("vat")
    @Expose
    private Integer vat;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public class Cart {

        @SerializedName("images")
        @Expose
        private String images;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("product_id")
        @Expose
        private String productId;
        @SerializedName("cart_id")
        @Expose
        private String cartId;

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getCartId() {
            return cartId;
        }

        public void setCartId(String cartId) {
            this.cartId = cartId;
        }

    }

}