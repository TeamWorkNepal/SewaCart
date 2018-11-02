package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.DashboardModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ProviderDashboard extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.ready_job_title)
    TextView readyJobTitle;
    @BindView(R.id.ready_job_count)
    TextView readyJobCount;
    @BindView(R.id.wrapper_ready_job)
    CardView wrapperReadyJob;
    @BindView(R.id.pending_job_title)
    TextView pendingJobTitle;
    @BindView(R.id.pending_job_count)
    TextView pendingJobCount;
    @BindView(R.id.wrapper_pending_job)
    CardView wrapperPendingJob;
    @BindView(R.id.completed_job_title)
    TextView completedJobTitle;
    @BindView(R.id.completed_job_count)
    TextView completedJobCount;
    @BindView(R.id.wapper_completed_job)
    CardView wapperCompletedJob;
    @BindView(R.id.rejected_job_title)
    TextView rejectedJobTitle;
    @BindView(R.id.rejected_job_count)
    TextView rejectedJobCount;
    @BindView(R.id.rejected_job_wrapper)
    CardView rejectedJobWrapper;
    @BindView(R.id.service_total_listing)
    TextView serviceTotalListing;
    @BindView(R.id.service_total_listing_count)
    TextView serviceTotalListingCount;
    @BindView(R.id.service_total_listing_wrapper)
    CardView serviceTotalListingWrapper;
    @BindView(R.id.service_published_title)
    TextView servicePublishedTitle;
    @BindView(R.id.service_published_title_count)
    TextView servicePublishedTitleCount;
    @BindView(R.id.service_published)
    CardView servicePublished;
    @BindView(R.id.service_pending)
    TextView servicePending;
    @BindView(R.id.service_pending_count)
    TextView servicePendingCount;
    @BindView(R.id.pending_Service)
    CardView pendingService;
    @BindView(R.id.balance_title)
    TextView balanceTitle;
    @BindView(R.id.balance_title_count)
    TextView balanceTitleCount;
    @BindView(R.id.balance_wrapper)
    CardView balanceWrapper;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_dashboard);
        ButterKnife.bind(this);
        setTitle("Dashboard");

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProviderDashboard.super.onBackPressed();
            }
        });
        scrollView.setVisibility(View.GONE);
        getDashboardData();
    }

    private void getDashboardData() {

        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", SharedPreferenceController.getUserDetails(ProviderDashboard.this).getId());
        Call<DashboardModel> call = userInterface.getDashboard(params);
        call.enqueue(new Callback<DashboardModel>() {
            @Override
            public void onResponse(@NonNull Call<DashboardModel> call, @NonNull Response<DashboardModel> response) {
                scrollView.setVisibility(View.VISIBLE);
                pDialog.dismiss();

                readyJobCount.setText(response.body().getJobs().getReady());
                pendingJobCount.setText(response.body().getJobs().getPending());
                completedJobCount.setText(response.body().getJobs().getCompleted());
                rejectedJobCount.setText(response.body().getJobs().getRejected());

                serviceTotalListingCount.setText(response.body().getServiceCount().getTotalService());
                servicePublishedTitleCount.setText(response.body().getServiceCount().getPublishService());
                servicePendingCount.setText(response.body().getServiceCount().getPendingService());
                balanceTitleCount.setText("Rs. " + response.body().getCreditBalance().get(0));

            }

            @Override
            public void onFailure(@NonNull Call<DashboardModel> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });

    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ProviderDashboard.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(ProviderDashboard.this, MainActivity.class));
                finish();
            }
        });
    }

    @OnClick({R.id.wrapper_ready_job, R.id.wrapper_pending_job, R.id.wapper_completed_job, R.id.rejected_job_wrapper, R.id.pending_Service, R.id.service_published, R.id.service_total_listing_wrapper, R.id.balance_wrapper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wrapper_ready_job:
                intent = new Intent(ProviderDashboard.this, OrdersActivity.class);
                intent.putExtra("key", "Ready Job");
                intent.putExtra("key1", "Ready");
                startActivity(intent);
                break;
            case R.id.wrapper_pending_job:
                intent = new Intent(ProviderDashboard.this, OrdersActivity.class);
                intent.putExtra("key", "Pending Job");
                intent.putExtra("key1", "Pending");
                startActivity(intent);
                break;
            case R.id.wapper_completed_job:
                intent = new Intent(ProviderDashboard.this, OrdersActivity.class);
                intent.putExtra("key", "Completed Job");
                intent.putExtra("key1", "Completed");
                startActivity(intent);
                break;
            case R.id.rejected_job_wrapper:
                intent = new Intent(ProviderDashboard.this, OrdersActivity.class);
                intent.putExtra("key", "Rejected Job");
                intent.putExtra("key1", "Rejected");
                startActivity(intent);
                break;
            case R.id.pending_Service:
                loadService();
                break;
            case R.id.service_published:
                loadService();
                break;
            case R.id.service_total_listing_wrapper:
                loadService();
                break;
            case R.id.balance_wrapper:
                break;
        }
    }

    private void loadService() {
        Intent intent = new Intent(ProviderDashboard.this, ServiceListingActivity.class);
        intent.putExtra("own", true);
        startActivity(intent);
    }

}
