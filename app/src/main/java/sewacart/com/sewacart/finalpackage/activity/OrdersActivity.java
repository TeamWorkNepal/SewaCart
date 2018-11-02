package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import sewacart.com.sewacart.finalpackage.model.DashboardModel;
import sewacart.com.sewacart.finalpackage.model.OrderModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class OrdersActivity extends AppCompatActivity {
    Toolbar toolbar;
    @BindView(R.id.table)
    TableLayout table;
    String status = "Completed";
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);


        toolbar = findViewById(R.id.toolbar2);
        status = getIntent().getStringExtra("key1");
        toolbar.setTitle(getIntent().getStringExtra("key"));


        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrdersActivity.super.onBackPressed();
            }
        });

        //toolbar.setTitle(status);
        scrollView.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewOrders();

    }

    private void viewOrders() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        table.removeAllViews();
        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        //TODO: add from sharedpreferencecontroll
        //params.put("user_id", SharedPreferenceController.getUserDetails(OrdersActivity.this).getId());
        params.put("user_id", "1");
        params.put("status", status);
        params.put("limit", 30 + "");
        Call<List<OrderModel>> call = userInterface.getOrderlist(params);
        call.enqueue(new Callback<List<OrderModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrderModel>> call, @NonNull Response<List<OrderModel>> response) {
                pDialog.dismiss();

                if (response.body().size() == 0) {

                    new SweetAlertDialog(OrdersActivity.this)
                            .setTitleText("No Jobs Found !")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(OrdersActivity.this, MainActivity.class);
                                 /*   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .show();
                } else {
                    scrollView.setVisibility(View.VISIBLE);

                    TableRow topTableRow = new TableRow(OrdersActivity.this);
                    int dp = (int) ControllerPixels.convertDpToPixel(16, OrdersActivity.this);
                    int smallmargin = (int) ControllerPixels.convertDpToPixel(5, OrdersActivity.this);


                    TextView topInvoice = new TextView(OrdersActivity.this);
                    topInvoice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topInvoice.setText("S.N");
                    topInvoice.setGravity(Gravity.CENTER);
                    topInvoice.setTextSize(15f);
                    topInvoice.setTypeface(null, Typeface.BOLD);
                    topInvoice.setPadding(smallmargin, dp, smallmargin, dp);
                    topInvoice.setBackgroundResource(R.drawable.border);

/*
                    TextView topService = new TextView(OrdersActivity.this);
                    topService.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topService.setText("Service");
                    topService.setGravity(Gravity.CENTER);
                    topService.setTextSize(15f);
                    topService.setTypeface(null, Typeface.BOLD);
                    topService.setPadding(smallmargin, dp, smallmargin, dp);
                    topService.setBackgroundResource(R.drawable.border);*/


                    TextView topPrice = new TextView(OrdersActivity.this);
                    topPrice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topPrice.setText("C. Name");
                    topPrice.setGravity(Gravity.CENTER);
                    topPrice.setTextSize(15f);
                    topPrice.setTypeface(null, Typeface.BOLD);
                    topPrice.setPadding(smallmargin, dp, smallmargin, dp);
                    topPrice.setBackgroundResource(R.drawable.border);

                    TextView topAddress = new TextView(OrdersActivity.this);
                    topAddress.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topAddress.setText("Address");
                    topAddress.setGravity(Gravity.CENTER);
                    topAddress.setTextSize(15f);
                    topAddress.setTypeface(null, Typeface.BOLD);
                    topAddress.setPadding(smallmargin, dp, smallmargin, dp);
                    topAddress.setBackgroundResource(R.drawable.border);

                    TextView topStatus = new TextView(OrdersActivity.this);
                    topStatus.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topStatus.setText("Contact");
                    topStatus.setGravity(Gravity.CENTER);
                    topStatus.setTextSize(15f);
                    topStatus.setTypeface(null, Typeface.BOLD);
                    topStatus.setPadding(smallmargin, dp, smallmargin, dp);
                    topStatus.setBackgroundResource(R.drawable.border);


                    topTableRow.addView(topInvoice);
                    topTableRow.addView(topPrice);
                    topTableRow.addView(topAddress);
                    topTableRow.addView(topStatus);

                    if (topTableRow.getParent() != null)
                        ((ViewGroup) topTableRow.getParent()).removeView(topTableRow);
                    table.addView(topTableRow);

                    for (int i = 0; i < response.body().size(); i++) {

                        final OrderModel orderModel = response.body().get(i);
                        final TableRow tableRow = new TableRow(OrdersActivity.this);

                        TextView invoice = new TextView(OrdersActivity.this);
                        invoice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        invoice.setText((i + 1) + ".");
                        invoice.setGravity(Gravity.CENTER);
                        invoice.setPadding(smallmargin, dp, smallmargin, dp);
                        invoice.setBackgroundResource(R.drawable.border);
                        invoice.setTextSize(12f);


                        TextView name = new TextView(OrdersActivity.this);
                        name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        name.setText(orderModel.getCustomerName());
                        name.setPadding(dp, 0, 0, 0);
                        //    product.setWidth((int) ControllerPixels.convertDpToPixel(150, ViewCartActivity.this));
                        name.setMaxLines(1);
                        name.setHorizontallyScrolling(true);
                        name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        name.setMarqueeRepeatLimit(-1);
                        name.setSingleLine(true);
                        name.setSelected(true);
                        name.setTextSize(12f);
                        name.setGravity(Gravity.CENTER);
                        name.setMaxWidth((int) ControllerPixels.convertDpToPixel(100, OrdersActivity.this));
                        name.setBackgroundResource(R.drawable.border);


                        TextView address = new TextView(OrdersActivity.this);
                        address.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        address.setText(orderModel.getDeliveryAddress());
                        address.setPadding(dp, 0, 0, 0);
                        //    product.setWidth((int) ControllerPixels.convertDpToPixel(150, ViewCartActivity.this));
                        address.setMaxLines(1);
                        address.setHorizontallyScrolling(true);
                        address.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        address.setMarqueeRepeatLimit(-1);
                        address.setSingleLine(true);
                        address.setSelected(true);
                        address.setTextSize(12f);
                        address.setGravity(Gravity.CENTER);
                        address.setMaxWidth((int) ControllerPixels.convertDpToPixel(100, OrdersActivity.this));
                        address.setBackgroundResource(R.drawable.border);


/*

                        TextView price = new TextView(OrdersActivity.this);
                        price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        price.setText(orderModel.getContact());
                        price.setGravity(Gravity.CENTER);
                        price.setPadding(smallmargin, dp, smallmargin, dp);
                        price.setBackgroundResource(R.drawable.border);
*/

                        TextView phone = new TextView(OrdersActivity.this);
                        phone.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        phone.setText(orderModel.getContact());
                        phone.setPadding(dp, 0, 0, 0);
                        //    product.setWidth((int) ControllerPixels.convertDpToPixel(150, ViewCartActivity.this));
                        phone.setMaxLines(1);
                        phone.setHorizontallyScrolling(true);
                        phone.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        phone.setMarqueeRepeatLimit(-1);
                        phone.setSingleLine(true);
                        phone.setSelected(true);
                        phone.setTextSize(12f);
                        phone.setGravity(Gravity.CENTER);
                        phone.setMaxWidth((int) ControllerPixels.convertDpToPixel(100, OrdersActivity.this));
                        phone.setBackgroundResource(R.drawable.border);


/*
                        TextView status = new TextView(OrdersActivity.this);
                        status.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        status.setText(OrdersActivityModel.getStatus());
                        status.setGravity(Gravity.CENTER);
                        status.setPadding(smallmargin, dp, smallmargin, dp);
                        status.setBackgroundResource(R.drawable.border);*/


                        tableRow.addView(invoice);
                        tableRow.addView(name);
                        tableRow.addView(address);
                        tableRow.addView(phone);
                        // tableRow.addView(status);

                        table.addView(tableRow);


                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<OrderModel>> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        table.removeAllViews();
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(OrdersActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(OrdersActivity.this, ProviderDashboard.class));
                finish();
            }
        });
    }
}
