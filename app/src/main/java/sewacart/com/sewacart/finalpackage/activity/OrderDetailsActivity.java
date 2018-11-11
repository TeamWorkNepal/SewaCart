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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.ControllerPixels;
import sewacart.com.sewacart.finalpackage.model.AddToCartModel;
import sewacart.com.sewacart.finalpackage.model.OrderDetailsModel;
import sewacart.com.sewacart.finalpackage.model.OrderModel;
import sewacart.com.sewacart.finalpackage.model.UserModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class OrderDetailsActivity extends AppCompatActivity {
    String orderId;
    @BindView(R.id.toolbar2)
    Toolbar toolbar2;
    @BindView(R.id.title_customer_info)
    TextView titleCustomerInfo;
    @BindView(R.id.invoice)
    TextView invoice;
    @BindView(R.id.customer_name)
    TextView customerName;
    @BindView(R.id.customer_email)
    TextView customerEmail;
    @BindView(R.id.delivery_mode)
    TextView deliveryMode;
    @BindView(R.id.delivery_address)
    TextView deliveryAddress;
    @BindView(R.id.delivery_datetime)
    TextView deliveryDatetime;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.order_datetime)
    TextView orderDatetime;
    @BindView(R.id.order_status)
    TextView orderStatus;
    @BindView(R.id.payment_mode)
    TextView paymentMode;
    @BindView(R.id.order_message)
    TextView orderMessage;
    @BindView(R.id.title_product_info)
    TextView titleProductInfo;
    @BindView(R.id.order_details_tablelayout)
    TableLayout orderDetailsTablelayout;
    @BindView(R.id.payment_detail)
    TextView paymentDetail;
    @BindView(R.id.top_wrapper)
    RelativeLayout topWrapper;

    float totalPriceFloat = 0.0f;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        ButterKnife.bind(this);
        orderId = getIntent().getStringExtra("orderId");
        if (orderId == null) {
            orderId = "0";
        }

        toolbar2.setTitle("Details");
        setSupportActionBar(toolbar2);
        toolbar2.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDetailsActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrderDetails();
    }

    private void getOrderDetails() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        final List<Boolean> booleans = new ArrayList<>();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("order_id", orderId);
        Call<OrderDetailsModel> call = userInterface.getOrderDetails(params);
        call.enqueue(new Callback<OrderDetailsModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderDetailsModel> call, @NonNull Response<OrderDetailsModel> response) {
                pDialog.dismiss();
                OrderDetailsModel orderDetailsModel = response.body();


                if (orderDetailsModel != null) {


                    topWrapper.setVisibility(View.VISIBLE);
                    OrderDetailsModel.OrderInformation orderInformation = orderDetailsModel.getOrderInformation();
                    final List<OrderDetailsModel.OrderDetail> orderDetails = orderDetailsModel.getOrderDetail();
                    invoice.setText("#" + orderInformation.getInvoiceId());
                    customerName.setText(orderInformation.getCustomerName());
                    deliveryAddress.setText(orderInformation.getDeliveryAddress());
                    contact.setText(orderInformation.getContact());
                    customerEmail.setText(orderInformation.getEmail());
                    orderMessage.setText(orderInformation.getMessage());
                    orderDatetime.setText(orderInformation.getOrderDate());
                    paymentMode.setText(orderInformation.getPaymentMode());
                    paymentDetail.setText(orderInformation.getPaymentDetail());

                    TableRow topTableRow = new TableRow(OrderDetailsActivity.this);
                    int dp = (int) ControllerPixels.convertDpToPixel(16, OrderDetailsActivity.this);
                    int smallmargin = (int) ControllerPixels.convertDpToPixel(5, OrderDetailsActivity.this);


                    TextView topInvoice = new TextView(OrderDetailsActivity.this);
                    topInvoice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topInvoice.setText("S.N");
                    topInvoice.setGravity(Gravity.CENTER);
                    topInvoice.setTextSize(15f);
                    topInvoice.setTypeface(null, Typeface.BOLD);
                    topInvoice.setPadding(smallmargin, dp, smallmargin, dp);
                    topInvoice.setBackgroundResource(R.drawable.border);


                    TextView topPrice = new TextView(OrderDetailsActivity.this);
                    topPrice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topPrice.setText("Service");
                    topPrice.setGravity(Gravity.CENTER);
                    topPrice.setTextSize(15f);
                    topPrice.setTypeface(null, Typeface.BOLD);
                    topPrice.setPadding(smallmargin, dp, smallmargin, dp);
                    topPrice.setBackgroundResource(R.drawable.border);

                    TextView topAddress = new TextView(OrderDetailsActivity.this);
                    topAddress.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topAddress.setText("Price");
                    topAddress.setGravity(Gravity.CENTER);
                    topAddress.setTextSize(15f);
                    topAddress.setTypeface(null, Typeface.BOLD);
                    topAddress.setPadding(smallmargin, dp, smallmargin, dp);
                    topAddress.setBackgroundResource(R.drawable.border);

                  /*  TextView topStatus = new TextView(OrderDetailsActivity.this);
                    topStatus.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topStatus.setText("Status");
                    topStatus.setGravity(Gravity.CENTER);
                    topStatus.setTextSize(15f);
                    topStatus.setTypeface(null, Typeface.BOLD);
                    topStatus.setPadding(smallmargin, dp, smallmargin, dp);
                    topStatus.setBackgroundResource(R.drawable.border);
*/
                    TextView status = new TextView(OrderDetailsActivity.this);
                    status.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    status.setText("Status");
                    status.setGravity(Gravity.CENTER);
                    status.setTextSize(15f);
                    status.setTypeface(null, Typeface.BOLD);
                    status.setPadding(smallmargin, dp, smallmargin, dp);
                    status.setBackgroundResource(R.drawable.border);

                    topTableRow.addView(topInvoice);
                    topTableRow.addView(topPrice);
                    topTableRow.addView(topAddress);
                    topTableRow.addView(status);

                    if (topTableRow.getParent() != null)
                        ((ViewGroup) topTableRow.getParent()).removeView(topTableRow);
                    orderDetailsTablelayout.addView(topTableRow);
                    totalPriceFloat = 0;
                    for (int i = 0; i < orderDetails.size(); i++) {

                        booleans.add(i, true);

                        final OrderDetailsModel.OrderDetail orderModel = orderDetails.get(i);
                        final TableRow tableRow = new TableRow(OrderDetailsActivity.this);

                        final TextView invoice = new TextView(OrderDetailsActivity.this);
                        invoice.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        invoice.setText((i + 1) + ".");
                        invoice.setGravity(Gravity.CENTER);
                        invoice.setPadding(smallmargin, dp, smallmargin, dp);
                        invoice.setBackgroundResource(R.drawable.border);
                        invoice.setTextSize(12f);


                        final TextView name = new TextView(OrderDetailsActivity.this);
                        name.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        name.setText(orderModel.getServiceName());
                        // name.setPadding(0, 0, 0, 0);
                        //    product.setWidth((int) ControllerPixels.convertDpToPixel(150, OrderDetailsActivity.this));
                        name.setMaxLines(1);
                        name.setHorizontallyScrolling(true);
                        name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        name.setMarqueeRepeatLimit(-1);
                        name.setSingleLine(true);
                        name.setSelected(true);
                        name.setTextSize(12f);
                        name.setGravity(Gravity.CENTER);
                        name.setMaxWidth((int) ControllerPixels.convertDpToPixel(100, OrderDetailsActivity.this));
                        name.setBackgroundResource(R.drawable.border);


                        final TextView address = new TextView(OrderDetailsActivity.this);
                        address.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        address.setText("Rs." + String.format("%.2f", Float.parseFloat(orderModel.getPrice())));
                        // address.setPadding(dp, 0, 0, 0);
                        //    product.setWidth((int) ControllerPixels.convertDpToPixel(150, OrderDetailsActivity.this));
                        address.setMaxLines(1);
                        address.setHorizontallyScrolling(true);
                        address.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        address.setMarqueeRepeatLimit(-1);
                        address.setSingleLine(true);
                        address.setSelected(true);
                        address.setTextSize(12f);
                        address.setGravity(Gravity.CENTER);
                        address.setMaxWidth((int) ControllerPixels.convertDpToPixel(100, OrderDetailsActivity.this));
                        address.setBackgroundResource(R.drawable.border);


                        tableRow.addView(invoice);
                        tableRow.addView(name);
                        tableRow.addView(address);


                        if (!orderModel.getStatus().equalsIgnoreCase("Completed")) {

                            final Spinner spinner = new Spinner(OrderDetailsActivity.this);
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(OrderDetailsActivity.this,
                                    R.array.status, R.layout.spinner_item);

                            spinner.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                            spinner.setAdapter(adapter);
                            spinner.setPadding(20, 0, 0, 0);
                            spinner.setGravity(Gravity.CENTER);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            int defaultSelectPos = 0;
                            if(orderModel.getStatus().equalsIgnoreCase("Rejected")){
                                defaultSelectPos=1;
                            }else if(orderModel.getStatus().equalsIgnoreCase("Ready")){
                                defaultSelectPos=2;
                            }
                            spinner.setSelection(defaultSelectPos);

                            final int finalI = i;
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (booleans.get(finalI)) {
                                        booleans.add(finalI, false);
                                    } else {

                                        updateProductStatus(orderModel.getOrderDetailId(), spinner.getSelectedItem().toString());
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            tableRow.addView(spinner);
                        } else {

                            final TextView statusText = new TextView(OrderDetailsActivity.this);
                            statusText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                            statusText.setText(orderModel.getStatus());
                            statusText.setGravity(Gravity.CENTER);
                            statusText.setPadding(smallmargin, dp, smallmargin, dp);
                            statusText.setBackgroundResource(R.drawable.border);
                            statusText.setTextSize(12f);
                            tableRow.addView(statusText);

                        }

                        // tableRow.addView(status);

                        orderDetailsTablelayout.addView(tableRow);

                        float mPricess = Float.parseFloat(orderDetails.get(i).getPrice());
                        totalPriceFloat += mPricess;


                    }

                    TableRow tableRowTotal = new TableRow(OrderDetailsActivity.this);

                    TextView totalSum = new TextView(OrderDetailsActivity.this);
                    totalSum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                    totalSum.setText("Total");
                    totalSum.setTypeface(null, Typeface.BOLD);
                    totalSum.setPadding(dp, dp, dp, dp);
                    totalSum.setGravity(Gravity.CENTER);
                    totalSum.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, OrderDetailsActivity.this));
                    totalSum.setBackgroundResource(R.drawable.border);
                    TableRow.LayoutParams params = (TableRow.LayoutParams) totalSum.getLayoutParams();
                    params.span = 1;
                    totalSum.setLayoutParams(params);


                    TextView totalCharge = new TextView(OrderDetailsActivity.this);
                    totalCharge.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    totalCharge.setText("Rs. " + String.format("%.2f", totalPriceFloat));
                    totalCharge.setTypeface(null, Typeface.BOLD);
                    totalCharge.setPadding(dp, dp, dp, dp);
                    totalCharge.setGravity(Gravity.CENTER);
                    totalCharge.setBackgroundResource(R.drawable.border);

                    tableRowTotal.addView(totalSum);
                    tableRowTotal.addView(totalCharge);
                    orderDetailsTablelayout.addView(tableRowTotal);


                } else {
                    topWrapper.setVisibility(View.GONE);
                    fallback();
                }

            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailsModel> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });
    }

    private void updateProductStatus(final String orderId1, String s) {

        final SweetAlertDialog pDialog1 = new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog1.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog1.setTitleText("Updating Status ...");
        pDialog1.setCancelable(false);
        pDialog1.show();


        final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<AddToCartModel> call = userInterface.changeOrderStatus("change_order_detail_status", orderId1, s,1+"");
        call.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(@NonNull Call<AddToCartModel> call, @NonNull Response<AddToCartModel> response) {
                if (response.body().getValue() == 1) {
                    Intent intent = new Intent(OrderDetailsActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(OrderDetailsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OrderDetailsActivity.this, OrderDetailsActivity.class);
                    intent.putExtra("orderId", orderId);
                    startActivity(intent);
                    finish();
                    /*final SweetAlertDialog pDialog = new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.ERROR_TYPE);
                    pDialog.setTitleText("Oops...");
                    pDialog.setContentText("Something went wrong!");
                    pDialog.show();
                    pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            Intent intent = new Intent(OrderDetailsActivity.this, OrderDetailsActivity.class);
                            intent.putExtra("orderId", orderId);
                            startActivity(intent);
                            finish();
                        }
                    });*/
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddToCartModel> call, @NonNull Throwable t) {
                pDialog1.dismiss();
                fallback();
            }
        });

    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(OrderDetailsActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Intent intent = new Intent(OrderDetailsActivity.this, OrderDetailsActivity.class);
                intent.putExtra("orderId", orderId);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        topWrapper.setVisibility(View.GONE);
        orderDetailsTablelayout.removeAllViews();

    }

}
