package sewacart.com.sewacart.activity.test;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.ControllerPixels;

public class ServiceProdiverDashboardServiceListing extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.table)
    TableLayout table;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_dashboard_listing);
        ButterKnife.bind(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle(getIntent().getStringExtra("key"));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceProdiverDashboardServiceListing.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        TableRow topTableRow = new TableRow(ServiceProdiverDashboardServiceListing.this);
        int dp = (int) ControllerPixels.convertDpToPixel(16, ServiceProdiverDashboardServiceListing.this);
        int smallmargin = (int) ControllerPixels.convertDpToPixel(5, ServiceProdiverDashboardServiceListing.this);

        TextView sn = new TextView(ServiceProdiverDashboardServiceListing.this);
        sn.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        sn.setText("S.n");
        sn.setGravity(Gravity.CENTER);
        sn.setTypeface(null, Typeface.BOLD);
        sn.setTextSize(15f);
        sn.setPadding(smallmargin, dp, smallmargin, dp);
        sn.setBackgroundResource(R.drawable.border);


        TextView topproduct = new TextView(ServiceProdiverDashboardServiceListing.this);
        topproduct.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
        topproduct.setText("Service");
        topproduct.setPadding(smallmargin, dp, smallmargin, dp);
        topproduct.setTextSize(15f);
        topproduct.setGravity(Gravity.CENTER);
        topproduct.setTypeface(null, Typeface.BOLD);
        topproduct.setMaxLines(1);
        topproduct.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, ServiceProdiverDashboardServiceListing.this));
        topproduct.setBackgroundResource(R.drawable.border);


        TextView topqty = new TextView(ServiceProdiverDashboardServiceListing.this);
        topqty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        topqty.setText("C. Name");
        topqty.setGravity(Gravity.CENTER);
        topqty.setTypeface(null, Typeface.BOLD);
        topqty.setTextSize(15f);
        topqty.setPadding(smallmargin, dp, smallmargin, dp);
        topqty.setBackgroundResource(R.drawable.border);


        TextView topempty = new TextView(ServiceProdiverDashboardServiceListing.this);
        topempty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        topempty.setText("C. Address");
        topempty.setTextSize(15f);
        topempty.setGravity(Gravity.CENTER);
        topempty.setTypeface(null, Typeface.BOLD);
        topempty.setPadding(smallmargin, dp, smallmargin, dp);
        topempty.setBackgroundResource(R.drawable.border);

       /* TextView phone = new TextView(ServiceProdiverDashboardServiceListing.this);
        phone.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        phone.setText("Phone");
        phone.setTextSize(15f);
        phone.setGravity(Gravity.CENTER);
        phone.setTypeface(null, Typeface.BOLD);
        phone.setPadding(smallmargin, dp, smallmargin, dp);
        phone.setBackgroundResource(R.drawable.border);*/

        topTableRow.addView(sn);
        topTableRow.addView(topproduct);
        topTableRow.addView(topqty);
        topTableRow.addView(topempty);
       // topTableRow.addView(phone);

        if (topTableRow.getParent() != null)
            ((ViewGroup) topTableRow.getParent()).removeView(topTableRow);

        table.addView(topTableRow);
    }

    @Override
    protected void onPause() {
        super.onPause();
        table.removeAllViews();
    }
}
