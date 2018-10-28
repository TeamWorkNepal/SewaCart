package sewacart.com.sewacart.finalpackage.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.AddReviewModel;
import sewacart.com.sewacart.finalpackage.model.ProviderModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ReviewActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.review_title)
    EditText reviewTitle;
    @BindView(R.id.review_designation)
    EditText reviewDesignation;
    @BindView(R.id.review_service)
    EditText reviewService;
    @BindView(R.id.review_rating)
    Spinner reviewRating;
    @BindView(R.id.review_msg)
    EditText reviewMsg;
    @BindView(R.id.review_btn)
    MaterialRippleLayout reviewBtn;
    String service_id;
    ProviderModel providerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewActivity.super.onBackPressed();
            }
        });
        service_id = String.valueOf(getIntent().getExtras().getInt("service_id", 0));
        providerModel = Parcels.unwrap(getIntent().getParcelableExtra("data"));
    }

    @OnClick(R.id.review_btn)
    public void onViewClicked() {

        String title = reviewTitle.getText().toString();
        String content = reviewMsg.getText().toString();
        String author = SharedPreferenceController.getUserDetails(ReviewActivity.this).getId();
        String testimonial_written_by = SharedPreferenceController.getUserDetails(ReviewActivity.this).getName();
        String rating = reviewRating.getSelectedItem().toString();
        String designation = reviewDesignation.getText().toString();
        // String service = reviewService.getText().toString();

        if (title.length() != 0 && content.length() != 0 && rating.length() != 0 && designation.length() != 0) {


            final SweetAlertDialog pDialog = new SweetAlertDialog(ReviewActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setContentText("Adding your review !");
            pDialog.setCancelable(false);
            pDialog.show();

            final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
            Call<AddReviewModel> call = userInterface.addReview("add_review", title, content, author, testimonial_written_by, rating, designation, service_id);
            call.enqueue(new Callback<AddReviewModel>() {
                @Override
                public void onResponse(@NonNull Call<AddReviewModel> call, @NonNull Response<AddReviewModel> response) {
                    pDialog.dismiss();
              /*  Intent intent = new Intent(ReviewActivity.this, ServiceProviderDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", Parcels.wrap(providerModel));
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);*/
                    finish();
                }

                @Override
                public void onFailure(@NonNull Call<AddReviewModel> call, @NonNull Throwable t) {
                    pDialog.dismiss();
                    fallback();
                }
            });
        } else {
            Toast.makeText(ReviewActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(ReviewActivity.this, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();

    }


}
