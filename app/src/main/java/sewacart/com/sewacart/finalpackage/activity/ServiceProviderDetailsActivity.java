package sewacart.com.sewacart.finalpackage.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;
import sewacart.com.sewacart.R;
import sewacart.com.sewacart.finalpackage.adapter.ProfileViewPagerAdapter;
import sewacart.com.sewacart.finalpackage.controller.SharedPreferenceController;
import sewacart.com.sewacart.finalpackage.model.AddToCartModel;
import sewacart.com.sewacart.finalpackage.model.ProviderModel;
import sewacart.com.sewacart.finalpackage.rest.ApiClient;
import sewacart.com.sewacart.finalpackage.rest.services.UserInterface;

public class ServiceProviderDetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    @BindView(R.id.add_to_cart)
    Button addToCart;
    @BindView(R.id.service_provider_book_now)
    Button serviceProviderBookNow;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.provider_title)
    TextView providerTitle;
    @BindView(R.id.provider_service)
    TextView providerService;
    @BindView(R.id.provider_cost)
    TextView providerCost;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    private Toolbar toolbar;

    CollapsingToolbarLayout collapsingToolbarLayout = null;
    ViewPager mViewPager;
    ProfileViewPagerAdapter profileViewPagerAdapter;
    private TabLayout mTabLayout;
    public static ProviderModel providerModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_details);
        ButterKnife.bind(this);
        floatingActionButton.hide();
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        providerModel = Parcels.unwrap(getIntent().getParcelableExtra("data"));
        toolbar.setNavigationIcon(R.drawable.arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceProviderDetailsActivity.super.onBackPressed();
            }
        });

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
        collapsingToolbarLayout.setTitle(providerModel.getTitle());
        providerTitle.setText(providerModel.getTitle());
        providerCost.setText("Rs. " + providerModel.getServiceCost() + " / " + providerModel.getCostInterval());


        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout.setupWithViewPager(mViewPager);

        //collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.profileExpandedAppBar);
        //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        profileViewPagerAdapter = new ProfileViewPagerAdapter(getSupportFragmentManager(), 1, this);
        mViewPager.setAdapter(profileViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        if (!providerModel.getFullImage().isEmpty()) {
            Picasso.with(ServiceProviderDetailsActivity.this).load(providerModel.getFullImage()).placeholder(R.drawable.default_user).networkPolicy(NetworkPolicy.OFFLINE).into(profileImage, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ServiceProviderDetailsActivity.this).load(providerModel.getFullImage()).placeholder(R.drawable.default_user).into(profileImage);
                }


            });
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceProviderDetailsActivity.this, ReviewActivity.class);
                intent.putExtra("service_id", providerModel.getId());
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", Parcels.wrap(providerModel));
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0:

                floatingActionButton.hide();

                break;
            case 1:

                floatingActionButton.hide();


                break;

            case 2:
                floatingActionButton.show();


                break;

            default:

                floatingActionButton.hide();

        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    @OnClick({R.id.add_to_cart, R.id.service_provider_book_now})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_to_cart:

                final ProgressDialog progressBar = new ProgressDialog(ServiceProviderDetailsActivity.this);
                progressBar.setCancelable(false);//you can cancel it by pressing back button
                progressBar.setMessage("Loading...");
                progressBar.show();

                final UserInterface userInterface = ApiClient.getApiClient().create(UserInterface.class);
                Call<AddToCartModel> call = userInterface.addToCart("add_to_cart", providerModel.getId() + "", SharedPreferenceController.getUserDetails(ServiceProviderDetailsActivity.this).getId());
                call.enqueue(new retrofit2.Callback<AddToCartModel>() {
                    @Override
                    public void onResponse(@NonNull Call<AddToCartModel> call, @NonNull Response<AddToCartModel> response) {
                        progressBar.dismiss();
                        new SweetAlertDialog(ServiceProviderDetailsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Successful !")
                                .setContentText("Click ok to proceed ")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                        Intent intent = new Intent(ServiceProviderDetailsActivity.this, ViewCartActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<AddToCartModel> call, @NonNull Throwable t) {
                        progressBar.dismiss();
                        Toast.makeText(ServiceProviderDetailsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case R.id.service_provider_book_now:
                Intent intent = new Intent(ServiceProviderDetailsActivity.this, MainActivity.class);
                intent.putExtra("goToContact", true);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.profile_image)
    public void onViewClicked() {
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(profileImage, "fullImage");

        Intent chatIntent = new Intent(ServiceProviderDetailsActivity.this, FullImageActivity.class);
        ActivityOptions activityOptions = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(ServiceProviderDetailsActivity.this, pairs);
        }

        chatIntent.putExtra("imageUrl", providerModel.getFullImage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(chatIntent, activityOptions.toBundle());
        } else {
            startActivity(chatIntent);
        }

    }
}
