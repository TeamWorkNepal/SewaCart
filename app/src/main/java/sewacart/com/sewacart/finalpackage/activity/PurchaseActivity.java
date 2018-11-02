package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.AddToCartModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class PurchaseActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.purchase_btn)
    MaterialRippleLayout purchaseBtn;
    @BindView(R.id.input_point)
    EditText input_point;
    @BindView(R.id.radio_cash)
    RadioButton radioCash;
    @BindView(R.id.radio_esewa)
    RadioButton radioEsewa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PurchaseActivity.super.onBackPressed();
            }
        });


    }

    @OnClick(R.id.purchase_btn)
    public void onViewClicked() {
        String point = input_point.getText().toString();
        String option;
        if (radioCash.isChecked()) {
            option = "cash";
        } else {
            option = "esewa";
        }

        final SweetAlertDialog pDialog = new SweetAlertDialog(PurchaseActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<AddToCartModel> call = userInterface.purchasePoint("purchase_credit_points", SharedPreferenceController.getUserDetails(PurchaseActivity.this).getId() + "", point, option);
        call.enqueue(new retrofit2.Callback<AddToCartModel>() {
            @Override
            public void onResponse(@NonNull Call<AddToCartModel> call, @NonNull Response<AddToCartModel> response) {
                pDialog.dismiss();
                new SweetAlertDialog(PurchaseActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Successful !")
                        .setContentText("Click ok to proceed ")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(PurchaseActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }

            @Override
            public void onFailure(@NonNull Call<AddToCartModel> call, @NonNull Throwable t) {
                pDialog.dismiss();
                fallback();
            }
        });
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(PurchaseActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(PurchaseActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
