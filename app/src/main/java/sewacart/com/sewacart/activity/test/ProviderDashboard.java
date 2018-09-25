package sewacart.com.sewacart.activity.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sewacart.com.sewacart.R;

public class ProviderDashboard extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.wrapper_new_job)
    CardView wrapperNewJob;
    @BindView(R.id.wrapper_pending_job)
    CardView wrapperPendingJob;
    @BindView(R.id.wapper_completed_job)
    CardView wapperCompletedJob;
    @BindView(R.id.wrapper_balance)
    CardView wrapperBalance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_dashboard);
        ButterKnife.bind(this);
        setTitle("Dashboard");

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProviderDashboard.super.onBackPressed();
            }
        });
    }

    @OnClick({R.id.wrapper_new_job, R.id.wrapper_pending_job, R.id.wapper_completed_job, R.id.wrapper_balance})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {

            case R.id.wrapper_new_job:
               intent = new Intent(ProviderDashboard.this,ServiceProdiverDashboardServiceListing.class);
                intent.putExtra("key","New Job");
                startActivity(intent);
                break;
            case R.id.wrapper_pending_job:
                intent = new Intent(ProviderDashboard.this,ServiceProdiverDashboardServiceListing.class);
                intent.putExtra("key","Pending Job");
                startActivity(intent);
                break;
            case R.id.wapper_completed_job:
                intent = new Intent(ProviderDashboard.this,ServiceProdiverDashboardServiceListing.class);
                intent.putExtra("key","Completed Job");
                startActivity(intent);
                break;
            case R.id.wrapper_balance:
                intent = new Intent(ProviderDashboard.this,ProfileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
