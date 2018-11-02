package sewacart.com.sewacart.finalpackage.model;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardModel {

    @SerializedName("service_count")
    @Expose
    private ServiceCount serviceCount;
    @SerializedName("jobs")
    @Expose
    private Jobs jobs;
    @SerializedName("credit_balance")
    @Expose
    private List<String> creditBalance = null;

    public ServiceCount getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(ServiceCount serviceCount) {
        this.serviceCount = serviceCount;
    }

    public Jobs getJobs() {
        return jobs;
    }

    public void setJobs(Jobs jobs) {
        this.jobs = jobs;
    }

    public List<String> getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(List<String> creditBalance) {
        this.creditBalance = creditBalance;
    }
    public class Jobs {

        @SerializedName("pending")
        @Expose
        private String pending;
        @SerializedName("ready")
        @Expose
        private String ready;
        @SerializedName("completed")
        @Expose
        private String completed;
        @SerializedName("rejected")
        @Expose
        private String rejected;

        public String getPending() {
            return pending;
        }

        public void setPending(String pending) {
            this.pending = pending;
        }

        public String getReady() {
            return ready;
        }

        public void setReady(String ready) {
            this.ready = ready;
        }

        public String getCompleted() {
            return completed;
        }

        public void setCompleted(String completed) {
            this.completed = completed;
        }

        public String getRejected() {
            return rejected;
        }

        public void setRejected(String rejected) {
            this.rejected = rejected;
        }

    }


    public class ServiceCount {

        @SerializedName("pending_service")
        @Expose
        private String pendingService;
        @SerializedName("publish_service")
        @Expose
        private String publishService;
        @SerializedName("total_service")
        @Expose
        private String totalService;

        public String getPendingService() {
            return pendingService;
        }

        public void setPendingService(String pendingService) {
            this.pendingService = pendingService;
        }

        public String getPublishService() {
            return publishService;
        }

        public void setPublishService(String publishService) {
            this.publishService = publishService;
        }

        public String getTotalService() {
            return totalService;
        }

        public void setTotalService(String totalService) {
            this.totalService = totalService;
        }

    }
}

