package sewacart.com.sewacart.finalpackage.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;




public class OrderDetailsModel {

    @SerializedName("order_information")
    @Expose
    private OrderInformation orderInformation;
    @SerializedName("order_detail")
    @Expose
    private List<OrderDetail> orderDetail = null;

    public OrderInformation getOrderInformation() {
        return orderInformation;
    }

    public void setOrderInformation(OrderInformation orderInformation) {
        this.orderInformation = orderInformation;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }
    public class OrderInformation {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("invoice_id")
        @Expose
        private String invoiceId;
        @SerializedName("customer_id")
        @Expose
        private String customerId;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("delivery_address")
        @Expose
        private String deliveryAddress;
        @SerializedName("contact")
        @Expose
        private String contact;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("payment_mode")
        @Expose
        private String paymentMode;
        @SerializedName("payment_detail")
        @Expose
        private String paymentDetail;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(String invoiceId) {
            this.invoiceId = invoiceId;
        }

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }

        public void setDeliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getPaymentDetail() {
            return paymentDetail;
        }

        public void setPaymentDetail(String paymentDetail) {
            this.paymentDetail = paymentDetail;
        }

    }

    public class OrderDetail {

        @SerializedName("order_id")
        @Expose
        private String orderId;
        @SerializedName("order_detail_id")
        @Expose
        private String orderDetailId;
        @SerializedName("service_provider")
        @Expose
        private String serviceProvider;
        @SerializedName("service_id")
        @Expose
        private String serviceId;
        @SerializedName("service_name")
        @Expose
        private String serviceName;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("status")
        @Expose
        private String status;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderDetailId() {
            return orderDetailId;
        }

        public void setOrderDetailId(String orderDetailId) {
            this.orderDetailId = orderDetailId;
        }

        public String getServiceProvider() {
            return serviceProvider;
        }

        public void setServiceProvider(String serviceProvider) {
            this.serviceProvider = serviceProvider;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
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
}


