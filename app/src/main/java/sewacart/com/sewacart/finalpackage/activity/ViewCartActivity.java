package sewacart.com.sewacart.finalpackage.activity;

import android.app.ProgressDialog;
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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

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
import sewacart.com.sewacart.finalpackage.controller.ControllerPixels;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.AddToCartModel;
import sewacart.com.sewacart.finalpackage.model.ViewCartModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ViewCartActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_main_activity)
    Toolbar toolbar;
    @BindView(R.id.table)
    TableLayout table;
    @BindView(R.id.checkout)
    MaterialRippleLayout checkOut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        table = findViewById(R.id.table);
        setTitle("Cart List");

        toolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCartActivity.super.onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewCart();


    }

    private void viewCart() {

        final ProgressDialog progressBar = new ProgressDialog(ViewCartActivity.this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();

        table.removeAllViews();
        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", SharedPreferenceController.getUserDetails(ViewCartActivity.this).getId());
        Call<ViewCartModel> call = userInterface.getViewCart(params);
        call.enqueue(new Callback<ViewCartModel>() {
            @Override
            public void onResponse(@NonNull Call<ViewCartModel> call, @NonNull Response<ViewCartModel> response) {
                progressBar.dismiss();

                if (response.body() == null) {
                    checkOut.setVisibility(View.GONE);

                    new SweetAlertDialog(ViewCartActivity.this)
                            .setTitleText("Basket is Empty !")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent intent = new Intent(ViewCartActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .show();
                } else {
                    checkOut.setVisibility(View.VISIBLE);
                    TableRow topTableRow = new TableRow(ViewCartActivity.this);
                    int dp = (int) ControllerPixels.convertDpToPixel(16, ViewCartActivity.this);
                    int smallmargin = (int) ControllerPixels.convertDpToPixel(5, ViewCartActivity.this);


                    TextView topproduct = new TextView(ViewCartActivity.this);
                    topproduct.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                    topproduct.setText("Product");
                    topproduct.setPadding(smallmargin, dp, smallmargin, dp);
                    topproduct.setTextSize(15f);
                    topproduct.setGravity(Gravity.CENTER);
                    topproduct.setTypeface(null, Typeface.BOLD);
                    topproduct.setMaxLines(1);
                    topproduct.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, ViewCartActivity.this));
                    topproduct.setBackgroundResource(R.drawable.border);


                    TextView topqty = new TextView(ViewCartActivity.this);
                    topqty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topqty.setText("Price");
                    topqty.setGravity(Gravity.CENTER);
                    topqty.setTypeface(null, Typeface.BOLD);
                    topqty.setTextSize(15f);
                    topqty.setPadding(smallmargin, dp, smallmargin, dp);
                    topqty.setBackgroundResource(R.drawable.border);


                    TextView topempty = new TextView(ViewCartActivity.this);
                    topempty.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    topempty.setText("Action");
                    topempty.setTextSize(15f);
                    topempty.setGravity(Gravity.CENTER);
                    topempty.setTypeface(null, Typeface.BOLD);
                    topempty.setPadding(smallmargin, dp, smallmargin, dp);
                    topempty.setBackgroundResource(R.drawable.border);


                    topTableRow.addView(topproduct);
                    topTableRow.addView(topqty);
                    topTableRow.addView(topempty);

                    if (topTableRow.getParent() != null)
                        ((ViewGroup) topTableRow.getParent()).removeView(topTableRow);
                    table.addView(topTableRow);

                    for (int i = 0; i < response.body().getCart().size(); i++) {
                        final ViewCartModel.Cart cart = response.body().getCart().get(i);
                        final TableRow tableRow = new TableRow(ViewCartActivity.this);

                        TextView product = new TextView(ViewCartActivity.this);
                        product.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                        product.setText(cart.getTitle());
                        product.setPadding(dp, 0, 0, 0);
                        //   product.setWidth((int) ControllerPixels.convertDpToPixel(150, ViewCartActivity.this));
                        product.setMaxLines(1);
                        product.setHorizontallyScrolling(true);
                        product.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                        product.setMarqueeRepeatLimit(-1);
                        product.setSingleLine(true);
                        product.setSelected(true);
                        product.setTextSize(12f);
                        product.setGravity(Gravity.CENTER);
                        product.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, ViewCartActivity.this));
                        product.setBackgroundResource(R.drawable.border);

                        //TODO: Make 2 digit floating price for 1. cart list, 2. order details

                        TextView price = new TextView(ViewCartActivity.this);
                        price.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        price.setText("Rs. " + String.format("%.2f",  Float.parseFloat(cart.getPrice())));
                        price.setGravity(Gravity.CENTER);
                        price.setPadding(smallmargin, dp, smallmargin, dp);
                        price.setBackgroundResource(R.drawable.border);



                        TextView delete = new TextView(ViewCartActivity.this);
                        delete.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                        delete.setText("X");
                        delete.setTypeface(null, Typeface.BOLD);
                        delete.setTextColor(Color.RED);
                        delete.setGravity(Gravity.CENTER);
                        delete.setPadding(smallmargin, dp, smallmargin, dp);
                        delete.setBackgroundResource(R.drawable.border);


                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new SweetAlertDialog(ViewCartActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Are you sure?")
                                        .setContentText("You Won't be able to undo!")
                                        .setConfirmText("Yes,delete it!")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();

                                                final SweetAlertDialog pDialog = new SweetAlertDialog(ViewCartActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                                pDialog.setTitleText("Loading ...");
                                                pDialog.setContentText("Deleting product from cart");
                                                pDialog.setCancelable(false);
                                                pDialog.show();

                                                UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
                                                Call<AddToCartModel> call = userInterface.removeFromCart("remove_list", cart.getProductId(), SharedPreferenceController.getUserDetails(ViewCartActivity.this).getId());
                                                call.enqueue(new Callback<AddToCartModel>() {
                                                    @Override
                                                    public void onResponse(@NonNull Call<AddToCartModel> call, @NonNull Response<AddToCartModel> response) {
                                                        pDialog.dismiss();
                                                        Intent intent = new Intent(ViewCartActivity.this, ViewCartActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onFailure(@NonNull Call<AddToCartModel> call, @NonNull Throwable t) {
                                                        pDialog.dismiss();
                                                        fallback();
                                                    }
                                                });
                                            }
                                        }).show();


                            }
                        });

                        tableRow.addView(product);
                        tableRow.addView(price);
                        tableRow.addView(delete);

                        table.addView(tableRow);
                    }
                    TableRow tableRowTotal = new TableRow(ViewCartActivity.this);

                    TextView totalSum = new TextView(ViewCartActivity.this);
                    totalSum.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                    totalSum.setText("Total");
                    totalSum.setTypeface(null, Typeface.BOLD);
                    totalSum.setPadding(dp, dp, dp, dp);
                    totalSum.setGravity(Gravity.CENTER);
                    totalSum.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, ViewCartActivity.this));
                    totalSum.setBackgroundResource(R.drawable.border);
                    TableRow.LayoutParams params = (TableRow.LayoutParams) totalSum.getLayoutParams();
                    params.span = 1;
                    totalSum.setLayoutParams(params);

                    TextView totalCharge = new TextView(ViewCartActivity.this);
                    totalCharge.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    totalCharge.setText("Rs. " + String.format("%.2f",response.body().getTotal()));
                    totalCharge.setTypeface(null, Typeface.BOLD);
                    totalCharge.setPadding(dp, dp, dp, dp);
                    totalCharge.setGravity(Gravity.CENTER);
                    totalCharge.setBackgroundResource(R.drawable.border);

                    tableRowTotal.addView(totalSum);
                    tableRowTotal.addView(totalCharge);
                    table.addView(tableRowTotal);

                    TableRow vatRow = new TableRow(ViewCartActivity.this);

                    TextView vat = new TextView(ViewCartActivity.this);
                    vat.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                    vat.setText("Vat");
                    vat.setTypeface(null, Typeface.BOLD);
                    vat.setPadding(dp, dp, dp, dp);
                    vat.setGravity(Gravity.CENTER);
                    vat.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, ViewCartActivity.this));
                    vat.setBackgroundResource(R.drawable.border);
                    TableRow.LayoutParams params1 = (TableRow.LayoutParams) totalSum.getLayoutParams();
                    params1.span = 1;
                    totalSum.setLayoutParams(params1);

                    TextView vatCount = new TextView(ViewCartActivity.this);
                    vatCount.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    vatCount.setText("Rs. " + String.format("%.2f",response.body().getVat()));
                    vatCount.setTypeface(null, Typeface.BOLD);
                    vatCount.setPadding(dp, dp, dp, dp);
                    vatCount.setGravity(Gravity.CENTER);
                    vatCount.setBackgroundResource(R.drawable.border);


                    vatRow.addView(vat);
                    vatRow.addView(vatCount);
                    table.addView(vatRow);


                    TableRow grantRow = new TableRow(ViewCartActivity.this);

                    TextView grand = new TextView(ViewCartActivity.this);
                    grand.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 4.0f));
                    grand.setText("Grand Total");
                    grand.setTypeface(null, Typeface.BOLD);
                    grand.setPadding(dp, dp, dp, dp);
                    grand.setGravity(Gravity.CENTER);
                    grand.setMaxWidth((int) ControllerPixels.convertDpToPixel(200, ViewCartActivity.this));
                    grand.setBackgroundResource(R.drawable.border);
                    TableRow.LayoutParams params2 = (TableRow.LayoutParams) totalSum.getLayoutParams();
                    params2.span = 1;
                    totalSum.setLayoutParams(params2);

                    TextView GrandTotal = new TextView(ViewCartActivity.this);
                    GrandTotal.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
                    vatCount.setText("Rs. " + String.format("%.2f",response.body().getGrandTotal()));
                    GrandTotal.setTypeface(null, Typeface.BOLD);
                    GrandTotal.setPadding(dp, dp, dp, dp);
                    GrandTotal.setGravity(Gravity.CENTER);
                    GrandTotal.setBackgroundResource(R.drawable.border);


                    grantRow.addView(grand);
                    grantRow.addView(GrandTotal);
                    table.addView(grantRow);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ViewCartModel> call, @NonNull Throwable t) {
                progressBar.dismiss();
                fallback();
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();
        table.removeAllViews();
        checkOut.setVisibility(View.GONE);
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ViewCartActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(ViewCartActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @OnClick(R.id.checkout)
    public void onViewClicked() {
        startActivity(new Intent(ViewCartActivity.this, CheckoutActivity.class));
    }
}
