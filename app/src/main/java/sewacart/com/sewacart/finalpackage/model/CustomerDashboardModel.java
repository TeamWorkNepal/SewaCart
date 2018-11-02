package sewacart.com.sewacart.finalpackage.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDashboardModel {

    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("services_request")
    @Expose
    private String servicesRequest;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private String status;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getServicesRequest() {
        return servicesRequest;
    }

    public void setServicesRequest(String servicesRequest) {
        this.servicesRequest = servicesRequest;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}