package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.ControllerPixels;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.CustomerDashboardModel;
import sewacart.com.sewacart.finalpackage.model.DashboardModel;
import sewacart.com.sewacart.finalpackage.model.ViewCartModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class CustomerDashboard extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.table)
    TableLayout table;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        ButterKnife.bind(this);

        setTitle("Dashboard");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerDashboard.super.onBackPressed();
            }
        });
        scrollView.setVisibility(View.GONE);


    }

    @Override
    protected void onStart() {
        super.onStart();
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
        params.put("user_id", SharedPreferenceController.getUserDetails(CustomerDashboard.this).getId());
        params.put("limit", 30 + "");
        Call<List<CustomerDashboardModel>> call = userInterface.getCustomerDashboard(params);
        call.enqueue(new Callback<List<CustomerDashboardModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CustomerDashboardModel>> call, @NonNull Response<List<CustomerDashboardModel>> response) {

                pDialog.dismiss();

                if (response.body().size() == 0) {

                    new SweetAlertDialog(CustomerDashboard.this)
                            .setTitleText("No Order History Found !")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(CustomerDashboard.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else {
                    scrollView.setVisibility(View.VISIBLE);

                    TableRow topTableRow = new TableRow(CustomerDashboard.this);
                    int dp = (int) ControllerPixels.convertDpToPixel(16, CustomerDashboard.this);
                    int smallmargin = (int) ControllerPixels.convertDpToPixel(5, CustomerDashboard.this);


                    TextView topInvoice = new TextView(CustomerDashboard.this);
                    topInvoice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topInvoice.setText("Invoice");
                    topInvoice.setGravity(Gravity.CENTER);
                    topInvoice.setTextSize(15f);
                    topInvoice.setTypeface(null, Typeface.BOLD);
                    topInvoice.setPadding(smallmargin, dp, smallmargin, dp);
                    topInvoice.setBackgroundResource(R.drawable.border);


                    TextView topService = new TextView(CustomerDashboard.this);
                    topService.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topService.setText("Service");
                    topService.setGravity(Gravity.CENTER);
                    topService.setTextSize(15f);
                    topService.setTypeface(null, Typeface.BOLD);
                    topService.setPadding(smallmargin, dp, smallmargin, dp);
                    topService.setBackgroundResource(R.drawable.border);


                    TextView topPrice = new TextView(CustomerDashboard.this);
                    topPrice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topPrice.setText("Price");
                    topPrice.setGravity(Gravity.CENTER);
                    topPrice.setTextSize(15f);
                    topPrice.setTypeface(null, Typeface.BOLD);
                    topPrice.setPadding(smallmargin, dp, smallmargin, dp);
                    topPrice.setBackgroundResource(R.drawable.border);

                    TextView topStatus = new TextView(CustomerDashboard.this);
                    topStatus.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topStatus.setText("Status");
                    topStatus.setGravity(Gravity.CENTER);
                    topStatus.setTextSize(15f);
                    topStatus.setTypeface(null, Typeface.BOLD);
                    topStatus.setPadding(smallmargin, dp, smallmargin, dp);
                    topStatus.setBackgroundResource(R.drawable.border);

                    topTableRow.addView(topInvoice);
                    topTableRow.addView(topService);
                    topTableRow.addView(topPrice);
                    topTableRow.addView(topStatus);

                    if (topTableRow.getParent() != null)
                        ((ViewGroup) topTableRow.getParent()).removeView(topTableRow);
                    table.addView(topTableRow);

                    for (int i = 0; i < response.body().size(); i++) {

                        final CustomerDashboardModel customerDashboardModel = response.body().get(i);
                        final TableRow tableRow = new TableRow(CustomerDashboard.this);

                        TextView invoice = new TextView(CustomerDashboard.this);
                        invoice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        invoice.setText("#" + customerDashboardModel.getInvoiceId());
                        invoice.setGravity(Gravity.CENTER);
                        invoice.setPadding(smallmargin, dp, smallmargin, dp);
                        invoice.setBackgroundResource(R.drawable.border);

                        TextView service = new TextView(CustomerDashboard.this);
                        service.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        service.setText(customerDashboardModel.getServicesRequest());
                        service.setPadding(dp, 0, 0, 0);
                        //    product.setWidth((int) ControllerPixels.convertDpToPixel(150, ViewCartActivity.this));
                        service.setMaxLines(1);
                        service.setHorizontallyScrolling(true);
                        service.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        service.setMarqueeRepeatLimit(-1);
                        service.setSingleLine(true);
                        service.setSelected(true);
                        service.setTextSize(12f);
                        service.setGravity(Gravity.CENTER);
                        service.setMaxWidth((int) ControllerPixels.convertDpToPixel(100, CustomerDashboard.this));
                        service.setBackgroundResource(R.drawable.border);


                        TextView price = new TextView(CustomerDashboard.this);
                        price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        price.setText("Rs. " + customerDashboardModel.getPrice());
                        price.setGravity(Gravity.CENTER);
                        price.setPadding(smallmargin, dp, smallmargin, dp);
                        price.setBackgroundResource(R.drawable.border);


                        TextView status = new TextView(CustomerDashboard.this);
                        status.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        status.setText(customerDashboardModel.getStatus());
                        status.setGravity(Gravity.CENTER);
                        status.setPadding(smallmargin, dp, smallmargin, dp);
                        status.setBackgroundResource(R.drawable.border);


                        tableRow.addView(invoice);
                        tableRow.addView(service);
                        tableRow.addView(price);
                        tableRow.addView(status);

                        table.addView(tableRow);


                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<List<CustomerDashboardModel>> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });

    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(CustomerDashboard.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(CustomerDashboard.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        table.removeAllViews();
    }
}
