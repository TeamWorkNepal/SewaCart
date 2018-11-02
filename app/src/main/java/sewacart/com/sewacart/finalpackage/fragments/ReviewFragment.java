package sewacart.com.sewacart.finalpackage.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import sewacart.com.sewacart.finalpackage.activity.ServiceProviderDetailsActivity;
import sewacart.com.sewacart.finalpackage.adapter.ReviewAdapter;
import sewacart.com.sewacart.finalpackage.controller.VerticalNewsPaddingController;
import sewacart.com.sewacart.finalpackage.model.ReviewModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ReviewFragment extends Fragment {
    Context context;
    List<ReviewModel> reviewModels = new ArrayList<>();
    @BindView(R.id.review_rcy)
    RecyclerView reviewRcy;
    ReviewAdapter reviewAdapter;
    LinearLayoutManager mLayoutManager;
    @BindView(R.id.loading_msg)
    TextView loadingMsg;
    @BindView(R.id.nested)
    NestedScrollView nested;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        ButterKnife.bind(this, view);
        reviewRcy.addItemDecoration(new VerticalNewsPaddingController(0));
        mLayoutManager = new LinearLayoutManager(context);
        reviewRcy.setLayoutManager(mLayoutManager);
        reviewAdapter = new ReviewAdapter(reviewModels, context);
    reviewRcy.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadingMsg.setVisibility(View.VISIBLE);
        loadReview();
    }

    private void loadReview() {

        UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", ServiceProviderDetailsActivity.providerModel.getServiceProvider() + "");
        Call<List<ReviewModel>> call = userInterface.getReviews(params);
        call.enqueue(new Callback<List<ReviewModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ReviewModel>> call, @NonNull Response<List<ReviewModel>> response) {


                if (response.body().size() < 1) {
                    loadingMsg.setText("No Reviews !\nBe the First to review...");
                } else {
                    loadingMsg.setVisibility(View.GONE);
                }
                reviewModels.addAll(response.body());
                reviewRcy.setAdapter(reviewAdapter);


            }

            @Override
            public void onFailure(@NonNull Call<List<ReviewModel>> call, @NonNull Throwable t) {
                loadingMsg.setText("Something went wrong !");
                fallback();
            }
        });
    }

    private void fallback() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText("Oops...");
        pDialog.setContentText("Something went wrong!");
        pDialog.show();

    }


    @Override
    public void onStop() {
        super.onStop();
        loadingMsg.setVisibility(View.GONE);
        reviewModels.clear();
        if (reviewAdapter != null)
            reviewAdapter.notifyDataSetChanged();
    }


}
