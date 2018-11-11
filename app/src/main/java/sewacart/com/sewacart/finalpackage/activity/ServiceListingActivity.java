package sewacart.com.sewacart.finalpackage.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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
import sewacart.com.sewacart.finalpackage.adapter.ServiceProviderListingAdapter;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.controller.VerticalNewsPaddingController;
import sewacart.com.sewacart.finalpackage.model.ProviderModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ServiceListingActivity extends AppCompatActivity {


    Toolbar toolbar;
    @BindView(R.id.service_rcy)
    RecyclerView serviceRcy;
    LinearLayoutManager mLayoutManager;
    List<ProviderModel> providerModels = new ArrayList<>();
    ServiceProviderListingAdapter serviceProviderAdapter;
    String catId = "1";
    boolean own = false;
    @BindView(R.id.editIconImage)
    ImageView editIconImage;
    @BindView(R.id.default_msg)
    TextView defaultMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_listing);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);


        catId = String.valueOf(getIntent().getExtras().getInt("category_id", 0));
        own = getIntent().getExtras().getBoolean("own", false);

        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceListingActivity.super.onBackPressed();
            }
        });

        if (own) {
            setTitle("My Services");
            serviceProviderAdapter = new ServiceProviderListingAdapter(ServiceListingActivity.this, providerModels, true);
            editIconImage.setVisibility(View.GONE);
        } else {
            setTitle("Services");
            serviceProviderAdapter = new ServiceProviderListingAdapter(ServiceListingActivity.this, providerModels, false);
        }

        serviceRcy.addItemDecoration(new VerticalNewsPaddingController(0));
        mLayoutManager = new LinearLayoutManager(ServiceListingActivity.this);
        serviceRcy.setLayoutManager(mLayoutManager);
        loadServiceProviders();
    }

    private void loadServiceProviders() {


        final ProgressDialog progressBar = new ProgressDialog(ServiceListingActivity.this);
        progressBar.setCancelable(false);//you can cancel it by pressing back button
        progressBar.setMessage("Loading...");
        progressBar.show();

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();

        if (own) {
            params.put("user_id", SharedPreferenceController.getUserDetails(ServiceListingActivity.this).getId());
        } else {
            params.put("cat_id", catId);
        }

        Call<List<ProviderModel>> call = userInterface.getProviders(params);
        call.enqueue(new Callback<List<ProviderModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProviderModel>> call, @NonNull Response<List<ProviderModel>> response) {
                progressBar.dismiss();
                providerModels.addAll(response.body());
                serviceRcy.setAdapter(serviceProviderAdapter);
                if(response.body().size()==0){
                    defaultMsg.setVisibility(View.VISIBLE);
                }else{
                    defaultMsg.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProviderModel>> call, @NonNull Throwable t) {
                progressBar.dismiss();
                fallback();
            }
        });
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ServiceListingActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();
        pDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                startActivity(new Intent(ServiceListingActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        defaultMsg.setVisibility(View.GONE);
    }
}
