package sewacart.com.sewacart.finalpackage.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.balysv.materialripple.MaterialRippleLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.AddToCartModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class CheckoutActivity extends AppCompatActivity {

    Toolbar toolbar;
    @BindView(R.id.input_full_address)
    EditText inputFullAddress;
    @BindView(R.id.input_contact_person)
    EditText inputContactPerson;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.input_info)
    EditText inputInfo;
    @BindView(R.id.submit)
    MaterialRippleLayout submit;
    @BindView(R.id.input_email)
    EditText inputEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckoutActivity.super.onBackPressed();
            }
        });
    }

    @OnClick(R.id.submit)
    public void onViewClicked() {


        String user_id = SharedPreferenceController.getUserDetails(CheckoutActivity.this).getId();
        String action = "ordernow";
        String deliver_address = inputFullAddress.getText().toString();
        String deliver_full_name = inputContactPerson.getText().toString();
        String deliver_contact = phone.getText().toString();
        String deliver_email = inputEmail.getText().toString();
        String deliver_information = inputInfo.getText().toString();

        final SweetAlertDialog pDialog = new SweetAlertDialog(CheckoutActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Call<AddToCartModel> call = userInterface.orderNow(action, user_id, deliver_address, deliver_full_name, deliver_contact, deliver_email, deliver_information);
        call.enqueue(new Callback<AddToCartModel>() {
            @Override
            public void onResponse(@NonNull Call<AddToCartModel> call, @NonNull Response<AddToCartModel> response) {
                pDialog.dismiss();
                new SweetAlertDialog(CheckoutActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Successful !")
                        .setContentText("Click ok to proceed ")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
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
        final SweetAlertDialog pDialog = new SweetAlertDialog(CheckoutActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
